package com.epam.musicbox.controller.command.impl.playlist;

import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.controller.command.CommandType;
import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.FileService;
import com.epam.musicbox.service.PlaylistService;
import com.epam.musicbox.service.impl.AuthServiceImpl;
import com.epam.musicbox.service.impl.FileServiceImpl;
import com.epam.musicbox.service.impl.PlaylistServiceImpl;
import com.epam.musicbox.util.ParamTaker;
import com.epam.musicbox.validator.EntityValidator;
import com.epam.musicbox.validator.FileValidator;
import com.epam.musicbox.validator.impl.EntityValidatorImpl;
import com.epam.musicbox.validator.impl.FileValidatorImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;

public class PlaylistSaveCommand implements Command {

    private static final String PLAYLIST_NOT_FOUND_MSG = "Playlist not found";
    private static final String INVALID_NAME_MSG = "Invalid name";

    private static final String DEFAULT_PLAYLIST_NAME = "Playlist";
    public static final String PLAYLIST_PICTURE = Parameter.PLAYLIST + Parameter.PICTURE;

    private static final String REDIRECT_URL = String.format("controller?command=%s&%s=",
            CommandType.EDIT_PLAYLIST_PAGE.getName(),
            Parameter.PLAYLIST_ID);

    private final PlaylistService playlistService = PlaylistServiceImpl.getInstance();
    private final FileService fileService = FileServiceImpl.getInstance();
    private final FileValidator fileValidator = FileValidatorImpl.getInstance();
    private final EntityValidator validator = EntityValidatorImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        try {
            Jws<Claims> token = AuthServiceImpl.getInstance().getToken(req);
            Claims body = token.getBody();
            long userId = ParamTaker.getLong(body, Parameter.USER_ID);

            Playlist playlist;
            Long id = ParamTaker.getNullableLong(req, Parameter.ALBUM_ID);
            if (id == null) {
                playlist = new Playlist(null, DEFAULT_PLAYLIST_NAME, null, userId);
                id = playlistService.save(playlist);
                playlist.setId(id);
                try {
                    fillAlbum(req, id, playlist);
                } catch (Exception e) {
                    playlistService.deleteById(id);
                    throw new CommandException(e);
                }
            } else {
                playlist = playlistService.findById(id).
                        orElseThrow(() -> new CommandException(PLAYLIST_NOT_FOUND_MSG));
                fillAlbum(req, id, playlist);
            }
            playlistService.save(playlist);
            return CommandResult.redirect(REDIRECT_URL + id);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }

    private void fillAlbum(HttpServletRequest req, Long id, Playlist playlist) throws ServiceException {
        String name = req.getParameter(Parameter.NAME);
        if (name != null) {
            if (!validator.isValidName(name)) {
                throw new ServiceException(INVALID_NAME_MSG);
            }
            playlist.setName(name);
        }
        String key = FileServiceImpl.generateKey(PLAYLIST_PICTURE, id);
        String picture = fileService.put(req, key, Parameter.PICTURE, false,
                Parameter.IMG_DIR, fileValidator::isValidImageFileName);
        if (picture != null) {
            playlist.setPicture(picture);
        }
    }
}

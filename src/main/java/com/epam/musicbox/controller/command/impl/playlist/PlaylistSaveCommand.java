package com.epam.musicbox.controller.command.impl.playlist;

import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.ParameterTaker;
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
import com.epam.musicbox.util.validator.Validator;
import com.epam.musicbox.util.validator.FileValidator;
import com.epam.musicbox.util.validator.impl.ValidatorImpl;
import com.epam.musicbox.util.validator.impl.FileValidatorImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

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
    private final Validator validator = ValidatorImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        try {
            Jws<Claims> token = AuthServiceImpl.getInstance().getToken(req);
            Claims body = token.getBody();
            long userId = ParameterTaker.getLong(body, Parameter.USER_ID);

            Playlist playlist;
            Optional<Long> optionalId = ParameterTaker.getOptionalLong(req, Parameter.ALBUM_ID);
            if (optionalId.isPresent()) {
                long id = optionalId.get();
                playlist = playlistService.findById(id).
                        orElseThrow(() -> new CommandException(PLAYLIST_NOT_FOUND_MSG));
                fillAlbum(req, id, playlist);
            } else {
                playlist = new Playlist(null, DEFAULT_PLAYLIST_NAME, null, userId);
                long id = playlistService.save(playlist);
                playlist.setId(id);
                try {
                    fillAlbum(req, id, playlist);
                } catch (Exception e) {
                    playlistService.deleteById(id);
                    throw new CommandException(e);
                }
            }
            playlistService.save(playlist);
            return CommandResult.redirect(REDIRECT_URL + playlist.getId());
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }

    private void fillAlbum(HttpServletRequest req, Long id, Playlist playlist) throws ServiceException {
        String name = ParameterTaker.getName(req);
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

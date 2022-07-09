package com.epam.musicbox.controller.command.impl.playlist;

import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.ParameterTaker;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandType;
import com.epam.musicbox.controller.command.Router;
import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.FileService;
import com.epam.musicbox.service.PlaylistService;
import com.epam.musicbox.service.impl.AuthServiceImpl;
import com.epam.musicbox.service.impl.FileServiceImpl;
import com.epam.musicbox.service.impl.PlaylistServiceImpl;
import com.epam.musicbox.util.validator.Validator;
import com.epam.musicbox.util.validator.impl.ValidatorImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

import java.util.Optional;

public class PlaylistSaveCommand implements Command {

    private static final String PLAYLIST_NOT_FOUND_MSG = "Playlist not found";
    private static final String INVALID_NAME_MSG = "Invalid name";
    private static final String INVALID_FILE_MSG = "Invalid file";

    private static final String DEFAULT_PLAYLIST_NAME = "Playlist";
    private static final String REDIRECT_URL = String.format("controller?%s=%s&%s=",
            Parameter.COMMAND,
            CommandType.EDIT_PLAYLIST_PAGE.getName(),
            Parameter.PLAYLIST_ID);

    private final PlaylistService playlistService = PlaylistServiceImpl.getInstance();
    private final FileService fileService = FileServiceImpl.getInstance();
    private final Validator validator = ValidatorImpl.getInstance();

    public static String pictureKey(long id) {
        return Parameter.PLAYLIST + Parameter.PICTURE + id;
    }

    @Override
    public Router execute(HttpServletRequest req) throws CommandException {
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
                fill(req, playlist);
            } else {
                playlist = new Playlist(null, DEFAULT_PLAYLIST_NAME, null, userId);
                long id = playlistService.save(playlist);
                playlist.setId(id);
                try {
                    fill(req, playlist);
                } catch (Exception e) {
                    playlistService.deleteById(id);
                    throw new CommandException(e.getMessage(), e);
                }
            }
            playlistService.save(playlist);
            return Router.redirect(REDIRECT_URL + playlist.getId());
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }

    private void fill(HttpServletRequest req, Playlist playlist) throws ServiceException {
        String name = req.getParameter(Parameter.NAME);
        if (!validator.isValidName(name)) {
            throw new ServiceException(INVALID_NAME_MSG);
        }
        playlist.setName(name);

        Part part = ParameterTaker.getPart(req, Parameter.PICTURE);
        if (part != null) {
            String fileName = part.getSubmittedFileName();
            if (fileName != null && !fileName.isEmpty()) {
                if (!validator.isValidImageFileName(fileName)) {
                    throw new ServiceException(INVALID_FILE_MSG);
                }
                String dir = FileService.getUploadDir(req) + FileService.IMAGE_DIR;
                String key = pictureKey(playlist.getId());
                String picture = fileService.save(dir, key, part);
                playlist.setPicture(picture);
            }
        }
    }
}

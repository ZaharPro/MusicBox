package com.epam.musicbox.controller.command.impl.album;

import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.ParameterTaker;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandType;
import com.epam.musicbox.controller.command.Router;
import com.epam.musicbox.entity.Album;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.AlbumService;
import com.epam.musicbox.service.FileService;
import com.epam.musicbox.service.impl.AlbumServiceImpl;
import com.epam.musicbox.service.impl.FileServiceImpl;
import com.epam.musicbox.util.validator.FileValidator;
import com.epam.musicbox.util.validator.Validator;
import com.epam.musicbox.util.validator.impl.FileValidatorImpl;
import com.epam.musicbox.util.validator.impl.ValidatorImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public class AlbumSaveCommand implements Command {

    private static final String ALBUM_NOT_FOUND_MSG = "Album not found";
    private static final String INVALID_NAME_MSG = "Invalid name";

    private static final String DEFAULT_ALBUM_NAME = "Album";
    public static final String ALBUM_PICTURE = Parameter.ALBUM + Parameter.PICTURE;

    private static final String REDIRECT_URL = String.format("controller?%s=%s&%s=",
            Parameter.COMMAND,
            CommandType.EDIT_ALBUM_PAGE.getName(),
            Parameter.ALBUM_ID);

    private final AlbumService albumService = AlbumServiceImpl.getInstance();
    private final FileService fileService = FileServiceImpl.getInstance();
    private final FileValidator fileValidator = FileValidatorImpl.getInstance();
    private final Validator validator = ValidatorImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest req) throws CommandException {
        try {
            Album album;
            Optional<Long> optionalId = ParameterTaker.getOptionalLong(req, Parameter.ALBUM_ID);
            if (optionalId.isPresent()) {
                long id = optionalId.get();
                album = albumService.findById(id).
                        orElseThrow(() -> new CommandException(ALBUM_NOT_FOUND_MSG));
                fillAlbum(req, album);
            } else {
                album = new Album(null, DEFAULT_ALBUM_NAME, null);
                long id = albumService.save(album);
                album.setId(id);
                try {
                    fillAlbum(req, album);
                } catch (Exception e) {
                    albumService.deleteById(id);
                    throw new CommandException(e.getMessage(), e);
                }
            }
            albumService.save(album);
            return Router.redirect(REDIRECT_URL + album.getId());
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }

    private void fillAlbum(HttpServletRequest req, Album album) throws ServiceException {
        String name = req.getParameter(Parameter.NAME);
        if (!validator.isValidName(name)) {
            throw new ServiceException(INVALID_NAME_MSG);
        }
        album.setName(name);
        String key = FileServiceImpl.generateKey(ALBUM_PICTURE, album.getId());
        String picture = fileService.put(req, key, Parameter.PICTURE, false,
                Parameter.IMG_DIR, fileValidator::isValidImageFileName);
        if (picture != null) {
            album.setPicture(picture);
        }
    }
}

package com.epam.musicbox.controller.command.impl.album;

import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.controller.command.CommandType;
import com.epam.musicbox.entity.Album;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.AlbumService;
import com.epam.musicbox.service.FileService;
import com.epam.musicbox.service.impl.AlbumServiceImpl;
import com.epam.musicbox.service.impl.FileServiceImpl;
import com.epam.musicbox.util.ParamTaker;
import com.epam.musicbox.validator.FileValidator;
import com.epam.musicbox.validator.EntityValidator;
import com.epam.musicbox.validator.impl.FileValidatorImpl;
import com.epam.musicbox.validator.impl.EntityValidatorImpl;
import jakarta.servlet.http.HttpServletRequest;

public class AlbumSaveCommand implements Command {

    private static final String ALBUM_NOT_FOUND_MSG = "Album not found";
    private static final String INVALID_NAME_MSG = "Invalid name";

    private static final String DEFAULT_ALBUM_NAME = "Album";
    public static final String ALBUM_PICTURE = Parameter.ALBUM + Parameter.PICTURE;

    private static final String REDIRECT_URL = String.format("controller?command=%s&%s=",
            CommandType.EDIT_ALBUM_PAGE.getName(),
            Parameter.ALBUM_ID);

    private final AlbumService albumService = AlbumServiceImpl.getInstance();
    private final FileService fileService = FileServiceImpl.getInstance();
    private final FileValidator fileValidator = FileValidatorImpl.getInstance();
    private final EntityValidator validator = EntityValidatorImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        try {
            Album album;
            Long id = ParamTaker.getNullableLong(req, Parameter.ALBUM_ID);
            if (id == null) {
                album = new Album(null, DEFAULT_ALBUM_NAME, null);
                id = albumService.save(album);
                album.setId(id);
                try {
                    fillAlbum(req, id, album);
                } catch (Exception e) {
                    albumService.deleteById(id);
                    throw new CommandException(e);
                }
            } else {
                album = albumService.findById(id).
                        orElseThrow(() -> new CommandException(ALBUM_NOT_FOUND_MSG));
                fillAlbum(req, id, album);
            }
            albumService.save(album);
            return CommandResult.redirect(REDIRECT_URL + id);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }

    private void fillAlbum(HttpServletRequest req, Long id, Album album) throws ServiceException {
        String name = req.getParameter(Parameter.NAME);
        if (name != null) {
            if (!validator.isValidName(name)) {
                throw new ServiceException(INVALID_NAME_MSG);
            }
            album.setName(name);
        }
        String key = FileServiceImpl.generateKey(ALBUM_PICTURE, id);
        String picture = fileService.put(req, key, Parameter.PICTURE, false,
                Parameter.IMG_DIR, fileValidator::isValidImageFileName);
        if (picture != null) {
            album.setPicture(picture);
        }
    }
}

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
import com.epam.musicbox.util.validator.Validator;
import com.epam.musicbox.util.validator.impl.ValidatorImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

import java.util.Optional;

public class AlbumSaveCommand implements Command {

    private static final String ALBUM_NOT_FOUND_MSG = "Album not found";
    private static final String INVALID_NAME_MSG = "Invalid name";
    private static final String INVALID_FILE_MSG = "Invalid file";

    private static final String DEFAULT_ALBUM_NAME = "Album";
    private static final String REDIRECT_URL = String.format("controller?%s=%s&%s=",
            Parameter.COMMAND,
            CommandType.EDIT_ALBUM_PAGE.getName(),
            Parameter.ALBUM_ID);

    private final AlbumService albumService = AlbumServiceImpl.getInstance();
    private final FileService fileService = FileServiceImpl.getInstance();
    private final Validator validator = ValidatorImpl.getInstance();

    public static String pictureKey(long id) {
        return Parameter.ALBUM + Parameter.PICTURE + id;
    }

    @Override
    public Router execute(HttpServletRequest req) throws CommandException {
        try {
            Album album;
            Optional<Long> optionalId = ParameterTaker.getOptionalLong(req, Parameter.ALBUM_ID);
            if (optionalId.isPresent()) {
                long id = optionalId.get();
                album = albumService.findById(id).
                        orElseThrow(() -> new CommandException(ALBUM_NOT_FOUND_MSG));
                fill(req, album);
            } else {
                album = new Album(null, DEFAULT_ALBUM_NAME, null);
                long id = albumService.save(album);
                album.setId(id);
                try {
                    fill(req, album);
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


    private void fill(HttpServletRequest req, Album album) throws ServiceException {
        String name = req.getParameter(Parameter.NAME);
        if (!validator.isValidName(name)) {
            throw new ServiceException(INVALID_NAME_MSG);
        }
        album.setName(name);

        Part part = ParameterTaker.getPart(req, Parameter.PICTURE);
        if (part != null) {
            String fileName = part.getSubmittedFileName();
            if (fileName != null && !fileName.isEmpty()) {
                if (!validator.isValidImageFileName(fileName)) {
                    throw new ServiceException(INVALID_FILE_MSG);
                }
                String dir = FileService.getUploadDir(req) + FileService.IMAGE_DIR;
                String key = pictureKey(album.getId());
                String picture = fileService.save(dir, key, part);
                album.setPicture(picture);
            }
        }
    }
}

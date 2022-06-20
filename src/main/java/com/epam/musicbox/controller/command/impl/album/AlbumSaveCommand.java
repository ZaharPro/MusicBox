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
import com.epam.musicbox.validator.Validator;
import com.epam.musicbox.validator.impl.ValidatorImpl;
import jakarta.servlet.http.HttpServletRequest;

public class AlbumSaveCommand implements Command {

    private static final String REDIRECT_URL = String.format("controller?command=%s&%s=",
            CommandType.EDIT_ALBUM_PAGE.getName(),
            Parameter.ALBUM_ID);

    public static final String ALBUM_PICTURE = Parameter.ALBUM + Parameter.PICTURE;

    private final AlbumService albumService = AlbumServiceImpl.getInstance();
    private final FileService fileService = FileServiceImpl.getInstance();
    private final Validator validator = ValidatorImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        try {
            boolean deleteOnFail = false;
            Long albumId = ParamTaker.getNullableLong(req, Parameter.ALBUM_ID);

            String name = req.getParameter(Parameter.NAME);
            if (albumId == null) {
                Album album = new Album(null, name, null);
                albumId = albumService.save(album);
                deleteOnFail = true;
            }
            String picture;
            try {
                String key = FileServiceImpl.generateKey(ALBUM_PICTURE, albumId);
                picture = fileService.put(req, key, Parameter.PICTURE, false,
                        Parameter.IMG_DIR, validator::isValidImageFileName);
            } catch (ServiceException e) {
                if (deleteOnFail) {
                    albumService.deleteById(albumId);
                }
                throw new CommandException(e);
            }
            Album album = new Album(albumId, name, picture);
            albumService.save(album);
            return CommandResult.redirect(REDIRECT_URL + albumId);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}

package com.epam.musicbox.controller.command.impl.artist;

import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.controller.command.CommandType;
import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.ArtistService;
import com.epam.musicbox.service.FileService;
import com.epam.musicbox.service.impl.ArtistServiceImpl;
import com.epam.musicbox.service.impl.FileServiceImpl;
import com.epam.musicbox.util.ParamTaker;
import com.epam.musicbox.validator.FileValidator;
import com.epam.musicbox.validator.EntityValidator;
import com.epam.musicbox.validator.impl.FileValidatorImpl;
import com.epam.musicbox.validator.impl.EntityValidatorImpl;
import jakarta.servlet.http.HttpServletRequest;

public class ArtistSaveCommand implements Command {

    private static final String ARTIST_NOT_FOUND_MSG = "Artist not found";
    private static final String INVALID_NAME_MSG = "Invalid name";

    private static final String DEFAULT_ARTIST_NAME = "Artist";
    public static final String ARTIST_AVATAR = Parameter.ARTIST + Parameter.AVATAR;

    private static final String REDIRECT_URL = String.format("controller?command=%s&%s=",
            CommandType.EDIT_ARTIST_PAGE.getName(),
            Parameter.ARTIST_ID);

    private final ArtistService artistService = ArtistServiceImpl.getInstance();
    private final FileService fileService = FileServiceImpl.getInstance();
    private final FileValidator fileValidator = FileValidatorImpl.getInstance();
    private final EntityValidator validator = EntityValidatorImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        try {
            Artist artist;
            Long id = ParamTaker.getNullableLong(req, Parameter.ALBUM_ID);
            if (id == null) {
                artist = new Artist(null, DEFAULT_ARTIST_NAME, null);
                id = artistService.save(artist);
                artist.setId(id);
                try {
                    fillAlbum(req, id, artist);
                } catch (Exception e) {
                    artistService.deleteById(id);
                    throw new CommandException(e);
                }
            } else {
                artist = artistService.findById(id).
                        orElseThrow(() -> new CommandException(ARTIST_NOT_FOUND_MSG));
                fillAlbum(req, id, artist);
            }
            artistService.save(artist);
            return CommandResult.redirect(REDIRECT_URL + id);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }

    private void fillAlbum(HttpServletRequest req, Long id, Artist artist) throws ServiceException {
        String name = req.getParameter(Parameter.NAME);
        if (name != null) {
            if (!validator.isValidName(name)) {
                throw new ServiceException(INVALID_NAME_MSG);
            }
            artist.setName(name);
        }
        String key = FileServiceImpl.generateKey(ARTIST_AVATAR, id);
        String avatar = fileService.put(req, key, Parameter.AVATAR, false,
                Parameter.IMG_DIR, fileValidator::isValidImageFileName);
        if (avatar != null) {
            artist.setAvatar(avatar);
        }
    }
}
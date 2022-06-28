package com.epam.musicbox.controller.command.impl.artist;

import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.ParameterTaker;
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
import com.epam.musicbox.util.validator.Validator;
import com.epam.musicbox.util.validator.FileValidator;
import com.epam.musicbox.util.validator.impl.ValidatorImpl;
import com.epam.musicbox.util.validator.impl.FileValidatorImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

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
    private final Validator validator = ValidatorImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        try {
            Artist artist;
            Optional<Long> optionalLong = ParameterTaker.getOptionalLong(req, Parameter.ALBUM_ID);
            if (optionalLong.isPresent()) {
                long id = optionalLong.get();
                artist = artistService.findById(id).
                        orElseThrow(() -> new CommandException(ARTIST_NOT_FOUND_MSG));
                fillAlbum(req, id, artist);
            } else {
                artist = new Artist(null, DEFAULT_ARTIST_NAME, null);
                long id = artistService.save(artist);
                artist.setId(id);
                try {
                    fillAlbum(req, id, artist);
                } catch (Exception e) {
                    artistService.deleteById(id);
                    throw new CommandException(e);
                }
            }
            artistService.save(artist);
            return CommandResult.redirect(REDIRECT_URL + artist.getId());
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }

    private void fillAlbum(HttpServletRequest req, Long id, Artist artist) throws ServiceException {
        String name = ParameterTaker.getName(req);
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
package com.epam.musicbox.controller.command.impl.artist;

import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.ParameterTaker;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandType;
import com.epam.musicbox.controller.command.Router;
import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.ArtistService;
import com.epam.musicbox.service.FileService;
import com.epam.musicbox.service.impl.ArtistServiceImpl;
import com.epam.musicbox.service.impl.FileServiceImpl;
import com.epam.musicbox.util.validator.Validator;
import com.epam.musicbox.util.validator.impl.ValidatorImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

import java.util.Optional;

public class ArtistSaveCommand implements Command {

    private static final String ARTIST_NOT_FOUND_MSG = "Artist not found";
    private static final String INVALID_NAME_MSG = "Invalid name";
    private static final String INVALID_FILE_MSG = "Invalid file";

    private static final String DEFAULT_ARTIST_NAME = "Artist";
    private static final String REDIRECT_URL = String.format("controller?%s=%s&%s=",
            Parameter.COMMAND,
            CommandType.EDIT_ARTIST_PAGE.getName(),
            Parameter.ARTIST_ID);

    private final ArtistService artistService = ArtistServiceImpl.getInstance();
    private final FileService fileService = FileServiceImpl.getInstance();
    private final Validator validator = ValidatorImpl.getInstance();

    public static String avatarKey(long id) {
        return Parameter.ARTIST + Parameter.AVATAR + id;
    }

    @Override
    public Router execute(HttpServletRequest req) throws CommandException {
        try {
            Artist artist;
            Optional<Long> optionalLong = ParameterTaker.getOptionalLong(req, Parameter.ALBUM_ID);
            if (optionalLong.isPresent()) {
                long id = optionalLong.get();
                artist = artistService.findById(id).
                        orElseThrow(() -> new CommandException(ARTIST_NOT_FOUND_MSG));
                fill(req, artist);
            } else {
                artist = new Artist(null, DEFAULT_ARTIST_NAME, null);
                long id = artistService.save(artist);
                artist.setId(id);
                try {
                    fill(req, artist);
                } catch (Exception e) {
                    artistService.deleteById(id);
                    throw new CommandException(e.getMessage(), e);
                }
            }
            artistService.save(artist);
            return Router.redirect(REDIRECT_URL + artist.getId());
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }

    private void fill(HttpServletRequest req, Artist artist) throws ServiceException {
        String name = req.getParameter(Parameter.NAME);
        if (!validator.isValidName(name)) {
            throw new ServiceException(INVALID_NAME_MSG);
        }
        artist.setName(name);

        Part part = ParameterTaker.getPart(req, Parameter.AVATAR);
        if (part != null) {
            String fileName = part.getSubmittedFileName();
            if (fileName != null && !fileName.isEmpty()) {
                if (!validator.isValidImageFileName(fileName)) {
                    throw new ServiceException(INVALID_FILE_MSG);
                }
                String dir = FileService.getUploadDir(req) + FileService.IMAGE_DIR;
                String key = avatarKey(artist.getId());
                String avatar = fileService.save(dir, key, part);
                artist.setAvatar(avatar);
            }
        }
    }
}

package com.epam.musicbox.controller.command.impl.artist;

import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.controller.command.CommandType;
import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.*;
import com.epam.musicbox.service.impl.*;
import com.epam.musicbox.util.ParamTaker;
import com.epam.musicbox.validator.Validator;
import com.epam.musicbox.validator.impl.ValidatorImpl;
import jakarta.servlet.http.HttpServletRequest;

public class ArtistSaveCommand implements Command {

    private static final String REDIRECT_URL = String.format("controller?command=%s&%s=",
            CommandType.GO_TO_EDIT_ARTIST_PAGE.getName(),
            Parameter.ARTIST_ID);

    public static final String ARTIST_AVATAR = Parameter.ARTIST + Parameter.AVATAR;

    private final ArtistService artistService = ArtistServiceImpl.getInstance();
    private final FileService fileService = FileServiceImpl.getInstance();
    private final Validator validator = ValidatorImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        try {
            boolean deleteOnFail = false;
            Long artistId = ParamTaker.getNullableLong(req, Parameter.ARTIST_ID);

            String name = req.getParameter(Parameter.NAME);
            if (artistId == null) {
                Artist artist = new Artist(null, name, null);
                artistId = artistService.save(artist);
                deleteOnFail = true;
            }
            String avatar;
            try {
                String key = FileServiceImpl.generateKey(ARTIST_AVATAR, artistId);
                avatar = fileService.put(req, key, Parameter.AVATAR, false,
                        FileService.IMG_DIR, validator::isValidImageFileName);
            } catch (ServiceException e) {
                if (deleteOnFail) {
                    artistService.deleteById(artistId);
                }
                throw new CommandException(e);
            }
            Artist artist = new Artist(artistId, name, avatar);
            artistService.save(artist);
            return CommandResult.redirect(REDIRECT_URL + artistId);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
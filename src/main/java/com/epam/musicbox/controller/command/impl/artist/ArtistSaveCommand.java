package com.epam.musicbox.controller.command.impl.artist;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.ArtistService;
import com.epam.musicbox.service.impl.ArtistServiceImpl;
import com.epam.musicbox.util.ParamTaker;
import jakarta.servlet.http.HttpServletRequest;

public class ArtistSaveCommand implements Command {

    private final ArtistService service = ArtistServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        try {
            Long artistId = ParamTaker.getNullableLong(req, Parameter.ARTIST_ID);
            String name = req.getParameter(Parameter.NAME);
            String avatar = req.getParameter(Parameter.AVATAR);
            Artist artist = new Artist(artistId, name, avatar);
            service.save(artist);
            return CommandResult.forward(PagePath.EDIT_ARTIST);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}

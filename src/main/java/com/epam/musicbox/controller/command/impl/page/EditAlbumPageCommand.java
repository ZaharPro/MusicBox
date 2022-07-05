package com.epam.musicbox.controller.command.impl.page;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.ParameterTaker;
import com.epam.musicbox.controller.command.Router;
import com.epam.musicbox.entity.Album;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.AlbumService;
import com.epam.musicbox.service.impl.AlbumServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public class EditAlbumPageCommand extends PageCommand {

    private final AlbumService albumService = AlbumServiceImpl.getInstance();

    public EditAlbumPageCommand() {
        super(PagePath.EDIT_ALBUM);
    }

    @Override
    public Router execute(HttpServletRequest req) throws CommandException {
        try {
            Optional<Long> optionalAlbumId = ParameterTaker.getOptionalLong(req, Parameter.ALBUM_ID);
            if (optionalAlbumId.isPresent()) {
                long id = optionalAlbumId.get();
                Album album = albumService.findById(id).orElse(null);
                req.setAttribute(Parameter.ALBUM, album);
            }
            return super.execute(req);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}

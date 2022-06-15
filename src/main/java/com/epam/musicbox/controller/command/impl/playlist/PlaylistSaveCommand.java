package com.epam.musicbox.controller.command.impl.playlist;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.PlaylistService;
import com.epam.musicbox.service.impl.PlaylistServiceImpl;
import com.epam.musicbox.util.ParamTaker;
import jakarta.servlet.http.HttpServletRequest;

public class PlaylistSaveCommand implements Command {

    private final PlaylistService service = PlaylistServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        try {
            Long playlistId = ParamTaker.getNullableLong(req, Parameter.PLAYLIST_ID);
            String name = req.getParameter(Parameter.NAME);
            String picture = req.getParameter(Parameter.PICTURE);
            Playlist playlist = new Playlist(playlistId, name, picture);
            service.save(playlist);
            return CommandResult.forward(PagePath.EDIT_PLAYLIST);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}

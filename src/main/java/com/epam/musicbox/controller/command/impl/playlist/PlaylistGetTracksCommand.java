package com.epam.musicbox.controller.command.impl.playlist;

import com.epam.musicbox.constant.PagePath;
import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.PlaylistService;
import com.epam.musicbox.service.impl.PlaylistServiceImpl;
import com.epam.musicbox.util.Parameters;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class PlaylistGetTracksCommand implements Command {

    private final PlaylistService service = PlaylistServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        long playlistId = Parameters.getLong(req, Parameter.PLAYLIST_ID);
        int page = Parameters.getIntOrZero(req, Parameter.PAGE);
        List<Track> list = service.getTracks(playlistId, page);
        req.setAttribute(Parameter.PLAYLIST_LIST, list);
        return CommandResult.forward(PagePath.TRACKS);
    }
}

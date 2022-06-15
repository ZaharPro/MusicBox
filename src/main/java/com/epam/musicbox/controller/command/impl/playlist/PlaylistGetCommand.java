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
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class PlaylistGetCommand implements Command {

    private final PlaylistService playlistService = PlaylistServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        int page = ParamTaker.getPage(req, Parameter.PLAYLIST_PAGE);
        int pageSize = ParamTaker.getInt(req, Parameter.PLAYLIST_PAGE_SIZE);
        List<Playlist> playlists = playlistService.findPage(page, pageSize);
        req.setAttribute(Parameter.PLAYLIST_PAGE, page);
        req.setAttribute(Parameter.PLAYLIST_LIST, playlists);
        return CommandResult.forward(PagePath.PLAYLISTS);
    }
}

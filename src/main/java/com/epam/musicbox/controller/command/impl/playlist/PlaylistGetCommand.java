package com.epam.musicbox.controller.command.impl.playlist;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.ParameterTaker;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.Router;
import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.PlaylistService;
import com.epam.musicbox.service.impl.PlaylistServiceImpl;
import com.epam.musicbox.service.page.PageSearchResult;
import jakarta.servlet.http.HttpServletRequest;

public class PlaylistGetCommand implements Command {

    private final PlaylistService playlistService = PlaylistServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest req) throws CommandException {
        try {
            int page = ParameterTaker.getPage(req, Parameter.PLAYLIST_PAGE_INDEX);
            int pageSize = ParameterTaker.getPageSize(req, Parameter.PLAYLIST_PAGE_SIZE);
            PageSearchResult<Playlist> psr = playlistService.findPage(page, pageSize);
            req.setAttribute(Parameter.PLAYLIST_PAGE_SEARCH_RESULT, psr);
            return Router.forward(PagePath.PLAYLISTS);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}

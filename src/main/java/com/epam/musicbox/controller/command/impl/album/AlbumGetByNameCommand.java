package com.epam.musicbox.controller.command.impl.album;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.ParameterTaker;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandType;
import com.epam.musicbox.controller.command.Router;
import com.epam.musicbox.entity.Album;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.AlbumService;
import com.epam.musicbox.service.impl.AlbumServiceImpl;
import com.epam.musicbox.service.page.PageSearchResult;
import jakarta.servlet.http.HttpServletRequest;

public class AlbumGetByNameCommand implements Command {

    private static final String COMMAND = String.format("%s&%s=",
            CommandType.ALBUM_GET_BY_NAME.getName(),
            Parameter.NAME);

    private final AlbumService albumService = AlbumServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest req) throws CommandException {
        try {
            String name = req.getParameter(Parameter.NAME);
            int page = ParameterTaker.getPage(req, Parameter.ALBUM_PAGE_INDEX);
            int pageSize = ParameterTaker.getPageSize(req, Parameter.ALBUM_PAGE_SIZE);
            PageSearchResult<Album> psr = albumService.findByName(name, page, pageSize);
            req.setAttribute(Parameter.ALBUM_PAGE_SEARCH_RESULT, psr);
            req.setAttribute(Parameter.COMMAND, COMMAND + name);
            return Router.forward(PagePath.ALBUMS);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}

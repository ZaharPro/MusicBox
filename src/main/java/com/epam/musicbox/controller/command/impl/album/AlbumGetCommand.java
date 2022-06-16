package com.epam.musicbox.controller.command.impl.album;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Album;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.AlbumService;
import com.epam.musicbox.service.psr.PageSearchResult;
import com.epam.musicbox.service.impl.AlbumServiceImpl;
import com.epam.musicbox.util.ParamTaker;
import jakarta.servlet.http.HttpServletRequest;

public class AlbumGetCommand implements Command {

    private final AlbumService albumService = AlbumServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        try {
            int page = ParamTaker.getPage(req, Parameter.ALBUM_PAGE_INDEX);
            int pageSize = ParamTaker.getPageSize(req, Parameter.ALBUM_PAGE_SIZE);
            PageSearchResult<Album> pageSearchResult = albumService.findPage(page, pageSize);
            req.setAttribute(Parameter.ALBUM_PAGE_SEARCH_RESULT, pageSearchResult);
            return CommandResult.forward(PagePath.ALBUMS);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}

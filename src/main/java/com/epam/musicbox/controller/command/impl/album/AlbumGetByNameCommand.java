package com.epam.musicbox.controller.command.impl.album;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Album;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.AlbumService;
import com.epam.musicbox.service.impl.AlbumServiceImpl;
import com.epam.musicbox.util.ParamTaker;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class AlbumGetByNameCommand implements Command {

    private final AlbumService albumService = AlbumServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        String name = req.getParameter(Parameter.NAME);
        int page = ParamTaker.getPage(req, Parameter.ALBUM_PAGE);
        int pageSize = ParamTaker.getInt(req, Parameter.ALBUM_PAGE_SIZE);
        List<Album> albums = albumService.findByName(name, page, pageSize);
        req.setAttribute(Parameter.ALBUM_PAGE, page);
        req.setAttribute(Parameter.ALBUM_LIST, albums);
        return CommandResult.forward(PagePath.ALBUMS_BY_NAME);
    }
}

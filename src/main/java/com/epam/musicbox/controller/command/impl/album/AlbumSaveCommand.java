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

public class AlbumSaveCommand implements Command {

    private final AlbumService service = AlbumServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        Long albumId = ParamTaker.getNullableLong(req, Parameter.ALBUM_ID);
        String name = req.getParameter(Parameter.NAME);
        String picture = req.getParameter(Parameter.PICTURE);
        Album album = new Album(albumId, name, picture);
        service.save(album);
        return CommandResult.forward(PagePath.EDIT_ALBUM);
    }
}

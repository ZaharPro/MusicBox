package com.epam.musicbox.controller.command.impl.album;

import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.entity.Album;
import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.service.AlbumService;
import com.epam.musicbox.service.impl.AlbumServiceImpl;
import com.epam.musicbox.util.Parameters;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AlbumSaveCommand implements Command {

    private final AlbumService service = AlbumServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws HttpException {
        Long albumId = Parameters.getNullableLong(req, Parameter.ALBUM_ID);
        String name = req.getParameter(Parameter.NAME);
        String picture = req.getParameter(Parameter.PICTURE);
        Album album = new Album(albumId, name, picture);
        service.save(album);
    }
}

package com.epam.musicbox.controller.command.impl.album;

import com.epam.musicbox.constant.PagePath;
import com.epam.musicbox.controller.command.impl.common.GetByNameCommand;
import com.epam.musicbox.entity.Album;
import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.service.AlbumService;
import jakarta.inject.Inject;

import java.util.List;

public class AlbumGetByNameCommand extends GetByNameCommand<Album> {
    @Inject
    private AlbumService albumService;

    public AlbumGetByNameCommand() {
        super(PagePath.ALBUM);
    }

    @Override
    protected List<Album> findByName(String name, int page) throws HttpException {
        return albumService.findByName(name, page);
    }
}

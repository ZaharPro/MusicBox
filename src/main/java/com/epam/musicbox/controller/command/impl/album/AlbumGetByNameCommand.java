package com.epam.musicbox.controller.command.impl.album;

import com.epam.musicbox.constant.PagePath;
import com.epam.musicbox.controller.command.impl.common.GetByNameCommand;
import com.epam.musicbox.entity.Album;
import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.service.AlbumService;
import com.epam.musicbox.service.impl.AlbumServiceImpl;

import java.util.List;

public class AlbumGetByNameCommand extends GetByNameCommand<Album> {

    private final AlbumService service = AlbumServiceImpl.getInstance();

    public AlbumGetByNameCommand() {
        super(PagePath.ALBUMS);
    }

    @Override
    protected List<Album> findByName(String name, int page) throws HttpException {
        return service.findByName(name, page);
    }
}

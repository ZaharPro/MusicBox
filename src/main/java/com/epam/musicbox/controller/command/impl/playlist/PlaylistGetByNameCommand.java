package com.epam.musicbox.controller.command.impl.playlist;

import com.epam.musicbox.constant.PagePath;
import com.epam.musicbox.controller.command.impl.common.GetByNameCommand;
import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.service.PlaylistService;
import com.epam.musicbox.service.impl.PlaylistServiceImpl;

import java.util.List;

public class PlaylistGetByNameCommand extends GetByNameCommand<Playlist> {

    private final PlaylistService service = PlaylistServiceImpl.getInstance();

    public PlaylistGetByNameCommand() {
        super(PagePath.PLAYLIST);
    }

    @Override
    protected List<Playlist> findByName(String name, int page) throws HttpException {
        return service.findByName(name, page);
    }
}

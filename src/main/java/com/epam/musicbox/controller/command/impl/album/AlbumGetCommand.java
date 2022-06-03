package com.epam.musicbox.controller.command.impl.album;

import com.epam.musicbox.constant.PagePath;
import com.epam.musicbox.controller.command.impl.common.GetCommand;
import com.epam.musicbox.entity.Album;
import com.epam.musicbox.service.impl.AlbumServiceImpl;

public class AlbumGetCommand extends GetCommand<Album> {

    public AlbumGetCommand() {
        super(AlbumServiceImpl.getInstance(), PagePath.ALBUMS);
    }
}

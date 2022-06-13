package com.epam.musicbox.controller.command.impl.album;

import com.epam.musicbox.util.constant.Parameter;
import com.epam.musicbox.controller.command.impl.common.DeleteCommand;
import com.epam.musicbox.entity.Album;
import com.epam.musicbox.service.impl.AlbumServiceImpl;

public class AlbumDeleteCommand extends DeleteCommand<Album, Long> {

    public AlbumDeleteCommand() {
        super(AlbumServiceImpl.getInstance(), Parameter.ALBUM_ID);
    }
}

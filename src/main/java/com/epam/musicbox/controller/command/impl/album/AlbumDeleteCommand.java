package com.epam.musicbox.controller.command.impl.album;

import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.impl.common.DeleteCommand;
import com.epam.musicbox.entity.Album;
import com.epam.musicbox.service.impl.AlbumServiceImpl;

public class AlbumDeleteCommand extends DeleteCommand<Album> {

    public AlbumDeleteCommand() {
        super(AlbumServiceImpl.getInstance(), Parameter.ALBUM_ID);
    }
}

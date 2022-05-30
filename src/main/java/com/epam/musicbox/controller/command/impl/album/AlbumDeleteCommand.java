package com.epam.musicbox.controller.command.impl.album;

import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.impl.common.DeleteCommand;
import com.epam.musicbox.entity.Album;

public class AlbumDeleteCommand extends DeleteCommand<Album> {
    public AlbumDeleteCommand() {
        super(Parameter.ALBUM_ID);
    }
}

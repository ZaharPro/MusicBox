package com.epam.musicbox.controller.command.impl.album;

import com.epam.musicbox.constant.PagePath;
import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.impl.common.GetCommand;
import com.epam.musicbox.entity.Album;

public class AlbumGetCommand extends GetCommand<Album> {
    public AlbumGetCommand() {
        super(Parameter.LIST, PagePath.ALBUM);
    }
}

package com.epam.musicbox.controller.command.impl.album;

import com.epam.musicbox.util.constant.PagePath;
import com.epam.musicbox.util.constant.Parameter;
import com.epam.musicbox.controller.command.impl.common.GetCommand;
import com.epam.musicbox.entity.Album;
import com.epam.musicbox.service.impl.AlbumServiceImpl;

public class AlbumGetCommand extends GetCommand<Album, Long> {

    public AlbumGetCommand() {
        super(AlbumServiceImpl.getInstance(), Parameter.ALBUM_PAGE, Parameter.ALBUM_LIST, PagePath.ALBUMS);
    }
}

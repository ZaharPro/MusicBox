package com.epam.musicbox.controller.command.impl.album;

import com.epam.musicbox.constant.PagePath;
import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.impl.common.GetByIdCommand;
import com.epam.musicbox.entity.Album;
import com.epam.musicbox.service.impl.AlbumServiceImpl;

public class AlbumGetByIdCommand extends GetByIdCommand<Album> {

    public AlbumGetByIdCommand() {
        super(AlbumServiceImpl.getInstance(), Parameter.ALBUM_ID, Parameter.ALBUM, PagePath.ALBUM);
    }
}

package com.epam.musicbox.controller.command.impl.playlist;

import com.epam.musicbox.constant.PagePath;
import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.impl.common.GetCommand;
import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.service.impl.PlaylistServiceImpl;

public class PlaylistGetCommand extends GetCommand<Playlist> {

    public PlaylistGetCommand() {
        super(PlaylistServiceImpl.getInstance(), Parameter.LIST, PagePath.PLAYLIST);
    }
}

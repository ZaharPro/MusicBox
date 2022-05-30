package com.epam.musicbox.controller.command.impl.playlist;

import com.epam.musicbox.constant.PagePath;
import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.impl.common.GetCommand;
import com.epam.musicbox.entity.Playlist;

public class PlaylistGetCommand extends GetCommand<Playlist> {

    public PlaylistGetCommand() {
        super(Parameter.LIST, PagePath.PLAYLIST);
    }
}

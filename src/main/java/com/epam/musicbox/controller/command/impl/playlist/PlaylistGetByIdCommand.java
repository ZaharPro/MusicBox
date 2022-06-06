package com.epam.musicbox.controller.command.impl.playlist;

import com.epam.musicbox.constant.PagePath;
import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.impl.common.GetByIdCommand;
import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.service.impl.PlaylistServiceImpl;

public class PlaylistGetByIdCommand extends GetByIdCommand<Playlist> {

    public PlaylistGetByIdCommand() {
        super(PlaylistServiceImpl.getInstance(), Parameter.PLAYLIST_ID, Parameter.PLAYLIST, PagePath.PLAYLIST);
    }
}

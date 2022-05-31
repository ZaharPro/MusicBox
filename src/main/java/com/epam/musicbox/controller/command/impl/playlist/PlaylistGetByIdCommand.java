package com.epam.musicbox.controller.command.impl.playlist;

import com.epam.musicbox.constant.PagePath;
import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.impl.common.GetByIdCommand;
import com.epam.musicbox.entity.Playlist;

public class PlaylistGetByIdCommand extends GetByIdCommand<Playlist> {
    public PlaylistGetByIdCommand() {
        super(Parameter.PLAYLIST_ID, PagePath.PLAYLIST);
    }
}

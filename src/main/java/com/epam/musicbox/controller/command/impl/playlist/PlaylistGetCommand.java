package com.epam.musicbox.controller.command.impl.playlist;

import com.epam.musicbox.util.constant.PagePath;
import com.epam.musicbox.util.constant.Parameter;
import com.epam.musicbox.controller.command.impl.common.GetCommand;
import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.service.impl.PlaylistServiceImpl;

public class PlaylistGetCommand extends GetCommand<Playlist, Long> {

    public PlaylistGetCommand() {
        super(PlaylistServiceImpl.getInstance(), Parameter.PLAYLIST_PAGE, Parameter.PLAYLIST_LIST, PagePath.PLAYLISTS);
    }
}

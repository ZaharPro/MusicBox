package com.epam.musicbox.controller.command.impl.playlist;

import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.impl.common.DeleteCommand;
import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.service.impl.PlaylistServiceImpl;

public class PlaylistDeleteCommand extends DeleteCommand<Playlist> {

    public PlaylistDeleteCommand() {
        super(PlaylistServiceImpl.getInstance(), Parameter.PLAYLIST_ID, PlaylistSaveCommand::pictureKey);
    }
}

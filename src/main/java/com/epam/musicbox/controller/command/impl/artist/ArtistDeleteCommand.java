package com.epam.musicbox.controller.command.impl.artist;

import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.impl.common.DeleteCommand;
import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.service.impl.ArtistServiceImpl;

public class ArtistDeleteCommand extends DeleteCommand<Artist> {

    public ArtistDeleteCommand() {
        super(ArtistServiceImpl.getInstance(), Parameter.ARTIST_ID);
    }
}

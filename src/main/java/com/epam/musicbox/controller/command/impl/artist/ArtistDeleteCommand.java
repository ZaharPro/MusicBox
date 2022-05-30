package com.epam.musicbox.controller.command.impl.artist;

import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.impl.common.DeleteCommand;
import com.epam.musicbox.entity.Artist;

public class ArtistDeleteCommand extends DeleteCommand<Artist> {
    public ArtistDeleteCommand() {
        super(Parameter.ARTIST_ID);
    }
}

package com.epam.musicbox.controller.command.impl.artist;

import com.epam.musicbox.constant.PagePath;
import com.epam.musicbox.controller.command.impl.common.GetCommand;
import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.service.impl.ArtistServiceImpl;

public class ArtistGetCommand extends GetCommand<Artist> {

    public ArtistGetCommand() {
        super(ArtistServiceImpl.getInstance(), PagePath.ARTISTS);
    }
}

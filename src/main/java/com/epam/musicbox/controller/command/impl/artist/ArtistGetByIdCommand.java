package com.epam.musicbox.controller.command.impl.artist;

import com.epam.musicbox.constant.PagePath;
import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.impl.common.GetByIdCommand;
import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.service.impl.ArtistServiceImpl;

public class ArtistGetByIdCommand extends GetByIdCommand<Artist> {

    public ArtistGetByIdCommand() {
        super(ArtistServiceImpl.getInstance(), Parameter.ARTIST_ID, PagePath.ARTIST);
    }
}

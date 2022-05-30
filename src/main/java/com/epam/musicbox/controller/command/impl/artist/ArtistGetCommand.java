package com.epam.musicbox.controller.command.impl.artist;

import com.epam.musicbox.constant.PagePath;
import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.impl.common.GetCommand;
import com.epam.musicbox.entity.Artist;

public class ArtistGetCommand extends GetCommand<Artist> {
    public ArtistGetCommand() {
        super(Parameter.LIST, PagePath.ARTIST);
    }
}

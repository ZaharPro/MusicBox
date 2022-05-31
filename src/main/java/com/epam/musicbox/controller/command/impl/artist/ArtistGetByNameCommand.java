package com.epam.musicbox.controller.command.impl.artist;

import com.epam.musicbox.constant.PagePath;
import com.epam.musicbox.controller.command.impl.common.GetByNameCommand;
import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.service.ArtistService;
import jakarta.inject.Inject;

import java.util.List;

public class ArtistGetByNameCommand extends GetByNameCommand<Artist> {
    @Inject
    private ArtistService ArtistService;

    public ArtistGetByNameCommand() {
        super(PagePath.ARTIST);
    }

    @Override
    protected List<Artist> findByName(String name, int page) throws HttpException {
        return ArtistService.findByName(name, page);
    }
}

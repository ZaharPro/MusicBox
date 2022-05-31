package com.epam.musicbox.controller.command.impl.track;

import com.epam.musicbox.constant.PagePath;
import com.epam.musicbox.controller.command.impl.common.GetByNameCommand;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.service.TrackService;
import jakarta.inject.Inject;

import java.util.List;

public class TrackGetByNameCommand extends GetByNameCommand<Track> {
    @Inject
    private TrackService TrackService;

    public TrackGetByNameCommand() {
        super(PagePath.TRACK);
    }

    @Override
    protected List<Track> findByName(String name, int page) throws HttpException {
        return TrackService.findByName(name, page);
    }
}

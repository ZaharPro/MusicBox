package com.epam.musicbox.controller.command.impl.track;

import com.epam.musicbox.constant.PagePath;
import com.epam.musicbox.controller.command.impl.common.GetByNameCommand;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.service.TrackService;
import com.epam.musicbox.service.impl.TrackServiceImpl;

import java.util.List;

public class TrackGetByNameCommand extends GetByNameCommand<Track> {

    private final TrackService service = TrackServiceImpl.getInstance();

    public TrackGetByNameCommand() {
        super(PagePath.TRACKS);
    }

    @Override
    protected List<Track> findByName(String name, int page) throws HttpException {
        return service.findByName(name, page);
    }
}

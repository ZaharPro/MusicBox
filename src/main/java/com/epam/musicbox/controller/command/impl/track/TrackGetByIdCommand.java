package com.epam.musicbox.controller.command.impl.track;

import com.epam.musicbox.constant.PagePath;
import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.impl.common.GetByIdCommand;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.service.impl.TrackServiceImpl;

public class TrackGetByIdCommand extends GetByIdCommand<Track> {

    public TrackGetByIdCommand() {
        super(TrackServiceImpl.getInstance(), Parameter.TRACK_ID, Parameter.TRACK, PagePath.TRACK);
    }
}

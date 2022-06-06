package com.epam.musicbox.controller.command.impl.track;

import com.epam.musicbox.constant.PagePath;
import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.impl.common.GetCommand;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.service.impl.TrackServiceImpl;

public class TrackGetCommand extends GetCommand<Track> {

    public TrackGetCommand() {
        super(TrackServiceImpl.getInstance(), Parameter.TRACK_LIST, PagePath.TRACKS);
    }
}

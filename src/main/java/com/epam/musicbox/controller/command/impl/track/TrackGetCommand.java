package com.epam.musicbox.controller.command.impl.track;

import com.epam.musicbox.util.constant.PagePath;
import com.epam.musicbox.util.constant.Parameter;
import com.epam.musicbox.controller.command.impl.common.GetCommand;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.service.impl.TrackServiceImpl;

public class TrackGetCommand extends GetCommand<Track, Long> {

    public TrackGetCommand() {
        super(TrackServiceImpl.getInstance(), Parameter.TRACK_PAGE, Parameter.TRACK_LIST, PagePath.TRACKS);
    }
}

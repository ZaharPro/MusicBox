package com.epam.musicbox.controller.command.impl.track;

import com.epam.musicbox.util.constant.Parameter;
import com.epam.musicbox.controller.command.impl.common.DeleteCommand;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.service.impl.TrackServiceImpl;

public class TrackDeleteCommand extends DeleteCommand<Track, Long> {

    public TrackDeleteCommand() {
        super(TrackServiceImpl.getInstance(), Parameter.TRACK_ID);
    }
}

package com.epam.musicbox.controller.command.impl.track;

import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.impl.common.DeleteCommand;
import com.epam.musicbox.entity.Track;

public class TrackDeleteCommand extends DeleteCommand<Track> {
    public TrackDeleteCommand() {
        super(Parameter.TRACK_ID);
    }
}

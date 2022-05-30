package com.epam.musicbox.controller.command.impl.track;

import com.epam.musicbox.constant.PagePath;
import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.impl.common.GetByIdCommand;
import com.epam.musicbox.entity.Track;

public class TrackGetByIdCommand extends GetByIdCommand<Track> {

    public TrackGetByIdCommand() {
        super(Parameter.TRACK_ID, Parameter.OBJECT, PagePath.TRACK);
    }
}

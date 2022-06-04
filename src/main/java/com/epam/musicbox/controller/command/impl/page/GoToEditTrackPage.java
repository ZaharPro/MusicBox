package com.epam.musicbox.controller.command.impl.page;

import com.epam.musicbox.constant.PagePath;

public class GoToEditTrackPage extends GoToPageCommand {
    public GoToEditTrackPage() {
        super(PagePath.EDIT_TRACK);
    }
}

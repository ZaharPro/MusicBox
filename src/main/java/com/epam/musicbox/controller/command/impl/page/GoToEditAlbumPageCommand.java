package com.epam.musicbox.controller.command.impl.page;

import com.epam.musicbox.util.constant.PagePath;

public class GoToEditAlbumPageCommand extends GoToPageCommand {
    public GoToEditAlbumPageCommand() {
        super(PagePath.EDIT_ALBUM);
    }
}

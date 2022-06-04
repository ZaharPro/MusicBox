package com.epam.musicbox.controller.command.impl.page;

import com.epam.musicbox.constant.PagePath;

public class GoToEditAlbumPage extends GoToPageCommand {
    public GoToEditAlbumPage() {
        super(PagePath.EDIT_ALBUM);
    }
}

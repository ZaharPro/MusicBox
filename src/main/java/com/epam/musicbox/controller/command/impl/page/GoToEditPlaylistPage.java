package com.epam.musicbox.controller.command.impl.page;

import com.epam.musicbox.constant.PagePath;

public class GoToEditPlaylistPage extends GoToPageCommand {
    public GoToEditPlaylistPage() {
        super(PagePath.EDIT_PLAYLIST);
    }
}

package com.epam.musicbox.controller.command.impl.page;

import com.epam.musicbox.constant.PagePath;

public class GoToEditArtistPage extends GoToPageCommand {
    public GoToEditArtistPage() {
        super(PagePath.EDIT_ARTIST);
    }
}

package com.epam.musicbox.controller.command.impl.artist;

import com.epam.musicbox.util.constant.PagePath;
import com.epam.musicbox.util.constant.Parameter;
import com.epam.musicbox.controller.command.CommandType;
import com.epam.musicbox.controller.command.impl.common.GetByNameCommand;
import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.ArtistService;
import com.epam.musicbox.service.impl.ArtistServiceImpl;

import java.util.List;

public class ArtistGetByNameCommand extends GetByNameCommand<Artist> {

    private final ArtistService service = ArtistServiceImpl.getInstance();

    public ArtistGetByNameCommand() {
        super(Parameter.NAME,
                Parameter.ARTIST_PAGE,
                Parameter.ARTIST_LIST,
                PagePath.ARTISTS,
                CommandType.ARTIST_GET.getName());
    }

    @Override
    protected List<Artist> findByName(String name, int page) throws ServiceException {
        return service.findByName(name, page);
    }
}

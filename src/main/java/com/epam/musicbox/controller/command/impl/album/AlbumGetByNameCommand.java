package com.epam.musicbox.controller.command.impl.album;

import com.epam.musicbox.util.constant.PagePath;
import com.epam.musicbox.util.constant.Parameter;
import com.epam.musicbox.controller.command.CommandType;
import com.epam.musicbox.controller.command.impl.common.GetByNameCommand;
import com.epam.musicbox.entity.Album;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.AlbumService;
import com.epam.musicbox.service.impl.AlbumServiceImpl;

import java.util.List;

public class AlbumGetByNameCommand extends GetByNameCommand<Album> {

    private final AlbumService service = AlbumServiceImpl.getInstance();

    public AlbumGetByNameCommand() {
        super(Parameter.NAME,
                Parameter.ALBUM_PAGE,
                Parameter.ALBUM_LIST,
                PagePath.ALBUMS,
                CommandType.ALBUM_GET.getName());
    }

    @Override
    protected List<Album> findByName(String name, int page) throws ServiceException {
        return service.findByName(name, page);
    }
}

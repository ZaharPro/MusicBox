package com.epam.musicbox.service.psr;

import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.ServiceException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TrackAlbumPageSearchResult extends PageSearchResult<Track> {

    private final long albumId;
    private final List<Boolean> flags;

    public TrackAlbumPageSearchResult(int page,
                                      int pageSize,
                                      long count,
                                      List<Track> elements,
                                      long albumId,
                                      List<Boolean> flags) {
        super(page, pageSize, count, elements);
        this.albumId = albumId;
        this.flags = flags;
    }

    public TrackAlbumPageSearchResult(int page, int pageSize) {
        super(page, pageSize);
        this.albumId = -1;
        this.flags = Collections.emptyList();
    }

    public long getAlbumId() {
        return albumId;
    }

    public List<Boolean> getFlags() {
        return flags;
    }

    public static TrackAlbumPageSearchResult from(PageSearchResult<Track> psr,
                                                  long albumId) throws ServiceException {
        if (psr.getCount() == 0) {
            return new TrackAlbumPageSearchResult(psr.getPage(), psr.getPageSize());
        }
        List<Boolean> belongsFlags = psr.getElements().stream()
                .map(Track::getId)
                .map(id -> id == albumId)
                .collect(Collectors.toList());

        return new TrackAlbumPageSearchResult(psr.getPage(),
                psr.getPageSize(),
                psr.getCount(),
                psr.getElements(),
                albumId,
                belongsFlags);
    }
}

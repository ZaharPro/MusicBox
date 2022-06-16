package com.epam.musicbox.service.psr;

import java.util.Collections;
import java.util.List;

public class PageSearchResult<T> {

    private final int page;
    private final int pageSize;
    private final long count;
    private final List<T> elements;

    public PageSearchResult(int page, int pageSize, long count, List<T> elements) {
        this.page = page;
        this.pageSize = pageSize;
        this.count = count;
        this.elements = elements;
    }

    public PageSearchResult(int page, int pageSize) {
        this(page, pageSize, 0, Collections.emptyList());
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getCount() {
        return count;
    }

    public List<T> getElements() {
        return elements;
    }

    public int getMaxPage() {
        if (count == 0)
            return 0;
        return (int) (count / pageSize) + 1;
    }

    public boolean hasElements() {
        return count != 0;
    }
}

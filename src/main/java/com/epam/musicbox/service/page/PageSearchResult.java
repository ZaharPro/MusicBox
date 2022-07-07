package com.epam.musicbox.service.page;

import java.util.Collections;
import java.util.List;

/**
 * The type Page search result.
 *
 * @param <T> the type parameter
 */
public class PageSearchResult<T> {

    private final int page;
    private final int pageSize;
    private final long count;
    private final List<T> elements;

    /**
     * Instantiates a new Page search result.
     *
     * @param page     the page
     * @param pageSize the page size
     * @param count    the count
     * @param elements the elements
     */
    public PageSearchResult(int page, int pageSize, long count, List<T> elements) {
        this.page = page;
        this.pageSize = pageSize;
        this.count = count;
        this.elements = elements;
    }

    /**
     * Instantiates a new Page search result.
     *
     * @param page     the page
     * @param pageSize the page size
     */
    public PageSearchResult(int page, int pageSize) {
        this(page, pageSize, 0, Collections.emptyList());
    }

    /**
     * Gets page index.
     *
     * @return the page index
     */
    public int getPage() {
        return page;
    }

    /**
     * Gets page size.
     *
     * @return the page size
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Gets count.
     *
     * @return the count
     */
    public long getCount() {
        return count;
    }

    /**
     * Gets elements.
     *
     * @return the elements
     */
    public List<T> getElements() {
        return elements;
    }

    /**
     * Has elements boolean.
     *
     * @return the boolean
     */
    public boolean hasElements() {
        return count != 0;
    }

    /**
     * Gets max page.
     *
     * @return the max page
     */
    public int getMaxPage() {
        return (int) ((count + (pageSize - 1)) / pageSize);
    }
}

package com.epam.musicbox.service.impl;

import com.epam.musicbox.entity.Entity;
import com.epam.musicbox.service.EntityService;

public abstract class AbstractEntityService<T extends Entity<K>, K> implements EntityService<T, K> {
    protected static final int DEFAULT_PAGE_SIZE = 20;

    private final int pageSize;

    protected AbstractEntityService(int pageSize) {
        this.pageSize = pageSize;
    }

    protected int getPageSize() {
        return pageSize;
    }

    protected int getOffset(int page) {
        return pageSize * page;
    }

    protected String buildRegex(String name) {
        return '[' + name + "]{2,}";
    }
}

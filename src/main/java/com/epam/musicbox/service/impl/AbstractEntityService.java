package com.epam.musicbox.service.impl;

import com.epam.musicbox.entity.Entity;
import com.epam.musicbox.exception.RepositoryException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.repository.Repository;
import com.epam.musicbox.service.EntityService;
import com.epam.musicbox.service.page.PageSearchResult;

import java.util.List;
import java.util.Optional;

public abstract class AbstractEntityService<T extends Entity> implements EntityService<T> {

    @Override
    public long count() throws ServiceException {
        try {
            return getRepository().count();
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public PageSearchResult<T> findPage(int page, int pageSize) throws ServiceException {
        try {
            if (!isValid(page, pageSize)) {
                return new PageSearchResult<>(page, pageSize);
            }
            long count = getRepository().count();
            if (count == 0) {
                return new PageSearchResult<>(page, pageSize);
            }
            List<T> list = getRepository().findAll(getOffset(page, pageSize), pageSize);
            return new PageSearchResult<>(page, pageSize, count, list);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Optional<T> findById(long id) throws ServiceException {
        try {
            return getRepository().findById(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public long save(T t) throws ServiceException {
        try {
            return getRepository().save(t);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(long id) throws ServiceException {
        try {
            getRepository().deleteById(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    protected boolean isValid(int page, int pageSize) {
        return page > 0 && pageSize > 0;
    }

    protected int getOffset(int page, int pageSize) {
        return (page - 1) * pageSize;
    }

    protected abstract Repository<T> getRepository();
}

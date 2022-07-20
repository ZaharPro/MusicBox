package com.epam.musicbox.service.impl;

import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.exception.RepositoryException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.factory.Factory;
import com.epam.musicbox.repository.PlaylistRepository;
import com.epam.musicbox.service.page.PageSearchResult;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoSession;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mockitoSession;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

public class PlaylistServiceTest {

    @Mock
    private PlaylistRepository repository;

    @InjectMocks
    private PlaylistServiceImpl service;

    private MockitoSession session;

    @DataProvider
    public Object[][] countProvider() {
        return new Object[][]{{0}, {3}, {7}, {10}, {Integer.MAX_VALUE}};
    }

    @DataProvider
    public Object[][] invalidPageProvider() {
        return new Object[][]{
                {0, 2},
                {3, 0},
                {2, -5},
                {-3, 2},
        };
    }

    @DataProvider
    public Object[][] validPageProvider() {
        return new Object[][]{
                {1, 2},
                {3, 4},
                {2, 5},
        };
    }

    @DataProvider
    public Object[][] validIdProvider() {
        return new Object[][]{{0}, {Integer.MAX_VALUE}, {Integer.MIN_VALUE}, {3}, {10}};
    }

    @BeforeMethod
    public void beforeMethod() {
        session = mockitoSession()
                .initMocks(this)
                .startMocking();
    }

    @AfterMethod
    public void afterMethod() {
        session.finishMocking();
    }

    @Test(groups = {"count"}, dataProvider = "countProvider")
    public void count(long count) throws RepositoryException, ServiceException {
        given(repository.count()).willReturn(count);
        assertEquals(service.count(), count);
    }

    @Test(groups = {"findPage"}, dataProvider = "invalidPageProvider")
    public void findInvalidPage(int page, int pageSize) throws ServiceException {
        PageSearchResult<Playlist> psr = service.findPage(page, pageSize);
        assertFalse(psr.hasElements());
    }

    @Test(groups = {"findPage"}, dataProvider = "validPageProvider")
    public void findValidPage(int page, int pageSize) throws RepositoryException, ServiceException {
        List<Playlist> list = Factory.create(Factory::createPlaylist, 32);
        given(repository.findAll(anyInt(), anyInt())).willReturn(list);
        given(repository.count()).willReturn(Long.MAX_VALUE);
        PageSearchResult<Playlist> psr = service.findPage(page, pageSize);
        assertEquals(psr.getElements(), list);
    }


    @Test(groups = {"findById"}, dataProvider = "validIdProvider")
    public void findById(long id) throws RepositoryException, ServiceException {
        Optional<Playlist> expected = Optional.of(Factory.createPlaylist());
        given(repository.findById(id)).willReturn(expected);
        Optional<Playlist> actual = service.findById(id);
        assertEquals(actual, expected);
    }

    @Test(groups = {"save"})
    public void save() throws RepositoryException, ServiceException {
        Playlist user = Factory.createPlaylist();
        long expectedId = user.getId();
        given(repository.save(user)).willReturn(expectedId);
        long actualId = service.save(user);
        assertEquals(actualId, expectedId);
    }
}

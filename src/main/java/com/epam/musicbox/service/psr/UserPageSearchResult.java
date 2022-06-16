package com.epam.musicbox.service.psr;

import com.epam.musicbox.entity.Role;
import com.epam.musicbox.entity.User;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.UserService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserPageSearchResult extends PageSearchResult<User> {

    private static final String ROLE_NOT_FOUND_MSG = "User role not found, userId=";

    private final List<Role> roles;

    public UserPageSearchResult(int page, int pageSize, long count, List<User> elements, List<Role> roles) {
        super(page, pageSize, count, elements);
        this.roles = roles;
    }

    public UserPageSearchResult(int page, int pageSize) {
        super(page, pageSize);
        this.roles = Collections.emptyList();
    }

    public List<Role> getRoles() {
        return roles;
    }

    public static UserPageSearchResult from(PageSearchResult<User> psr,
                                            UserService service) throws ServiceException {
        if (psr.getCount() == 0) {
            return new UserPageSearchResult(psr.getPage(), psr.getPageSize());
        }
        List<User> elements = psr.getElements();
        List<Role> roles = new ArrayList<>(elements.size());
        for (User user : elements) {
            Long id = user.getId();
            Optional<Role> optionalRole = service.getRole(id);
            if (optionalRole.isEmpty()) {
                throw new ServiceException(ROLE_NOT_FOUND_MSG + id);
            }
            Role role = optionalRole.get();
            roles.add(role);
        }

        return new UserPageSearchResult(psr.getPage(),
                psr.getPageSize(),
                psr.getCount(),
                elements,
                roles);
    }
}

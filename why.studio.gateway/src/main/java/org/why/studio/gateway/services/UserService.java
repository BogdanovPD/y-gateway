package org.why.studio.gateway.services;

import org.why.studio.gateway.dto.UserDto;

public interface UserService {

    void initialCreate(String email);
    void createUser(UserDto userDto);

}

package org.why.studio.gateway.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.why.studio.gateway.dto.UserDto;
import org.why.studio.gateway.services.UserService;
import org.why.studio.gateway.services.util.RestHelperService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final RestHelperService restHelperService;
    @Value("${service.user.create-url}")
    private String createUrl;

    @Override
    public void initialCreate(String email) {
        restHelperService.sendPostRequest(createUrl.concat("/init"), email, MediaType.TEXT_PLAIN, "");
    }

    @Override
    public void createUser(UserDto userDto) {
        restHelperService.sendPostRequest(createUrl, userDto, MediaType.APPLICATION_JSON, "");
    }
}

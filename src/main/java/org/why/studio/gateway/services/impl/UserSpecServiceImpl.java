package org.why.studio.gateway.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import org.why.studio.gateway.dto.UserDto;
import org.why.studio.gateway.services.AuthService;
import org.why.studio.gateway.services.UserSpecService;
import org.why.studio.gateway.services.util.RestHelperService;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserSpecServiceImpl implements UserSpecService {

    private final RestHelperService restHelperService;
    private final AuthService authService;

    @Value("${service.schedule.user-spec}")
    private String userSpecUrl;
    @Value("${service.schedule.day-free-time}")
    private String dayFreeTimeUrl;
    @Value("${service.schedule.busy-days}")
    private String busyDaysUrl;

    @Override
    public UserDto getUserSpecialist() {
        String id = authService.getUserUuidFromAccessToken();
        String uriString = UriComponentsBuilder.fromUriString(userSpecUrl).buildAndExpand(Map.of("id", id)).toUriString();
        return restHelperService.sendGetRequest(UriComponentsBuilder.fromUriString(uriString), new UserDto());
    }
}

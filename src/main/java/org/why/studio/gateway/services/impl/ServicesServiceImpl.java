package org.why.studio.gateway.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import org.why.studio.gateway.dto.ServicesDto;
import org.why.studio.gateway.services.AuthService;
import org.why.studio.gateway.services.ServicesService;
import org.why.studio.gateway.services.util.RestHelperService;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ServicesServiceImpl implements ServicesService {

    private final AuthService authService;
    private final RestHelperService restHelperService;

    @Value("${service.schedule.user-spec-services}")
    private String userSpecServicesUrl;

    @Override
    public ServicesDto getUserSpecialistServices() {
        String uuid = authService.getUserUuidFromAccessToken();
        String url = UriComponentsBuilder.fromUriString(userSpecServicesUrl)
                .buildAndExpand(Map.of("id", uuid)).toUriString();
        return restHelperService.sendGetRequest(UriComponentsBuilder.fromUriString(url), new ServicesDto());
    }
}

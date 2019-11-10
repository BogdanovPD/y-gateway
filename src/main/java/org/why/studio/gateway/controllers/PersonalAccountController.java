package org.why.studio.gateway.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.why.studio.gateway.dto.ServicesDto;
import org.why.studio.gateway.dto.UserDto;
import org.why.studio.gateway.services.ServicesService;
import org.why.studio.gateway.services.UserSpecService;

@Controller
@RequiredArgsConstructor
public class PersonalAccountController {

    private final UserSpecService userSpecService;
    private final ServicesService servicesService;

    @GetMapping("/personal")
    public String getAccessToken(Model model) {
        UserDto spec = userSpecService.getUserSpecialist();
        model.addAttribute("specName", spec.getFirstName().concat(" ").concat(spec.getLastName()));
        model.addAttribute("specId", spec.getId());
        ServicesDto servicesDto = servicesService.getUserSpecialistServices();
        model.addAttribute("servs", servicesDto.getServices());
        return "pickdatetime";
    }

}

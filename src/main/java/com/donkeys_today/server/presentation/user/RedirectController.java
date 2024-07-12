package com.donkeys_today.server.presentation.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class RedirectController {

    @GetMapping("/redirect")
    public String exRedirect() {
        return "redirect:https://kauth.kakao.com/oauth/authorize?client_id=7210cddb1650bf64ff6de8902c9f9080&"
            + "redirect_uri=http://localhost:8080/login/oauth2/code/kakao&response_type=code";
    }
}

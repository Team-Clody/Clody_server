package com.donkeys_today.server.presentation.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class RedirectController {

    @GetMapping("/redirect")
    public String exRedirect() {
        return "redirect:https://kauth.kakao.com/oauth/authorize?client_id=fd3c31a8051ac971945e7a6fe3338c8a&"
            + "redirect_uri=http%3A%2F%2Flocalhost%3A8080%2F&response_type=code";
    }
}

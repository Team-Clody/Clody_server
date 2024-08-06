package com.clody.clodyapi.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1")
public class RedirectController {

  @GetMapping("/auth/redirect")
  public String exRedirect() {
    System.out.println("REDIRECTED!");
    return
        "redirect:https://kauth.kakao.com/oauth/authorize?client_id=9cdf27d910ba342e03a082b8196c1d61&"
            + "redirect_uri=https://clody.store/oauth/kakao&response_type=code";
  }

}

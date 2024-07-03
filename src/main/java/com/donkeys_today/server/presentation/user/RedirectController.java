package com.donkeys_today.server.presentation.user;

import com.donkeys_today.server.application.user.UserService;
import com.donkeys_today.server.presentation.user.dto.requset.UserSignUpRequest;
import com.donkeys_today.server.presentation.user.dto.response.UserSignUpResponse;
import com.donkeys_today.server.support.dto.ApiResponse;
import com.donkeys_today.server.support.dto.type.SuccessType;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class RedirectController {

    private final UserService userService;

    @GetMapping("/redirect")
    public String exRedirect() {
        return "redirect:https://kauth.kakao.com/oauth/authorize?client_id={키값}&redirect_uri={리다이렉트uri}&response_type=code";
    }


    @ResponseBody
    @GetMapping("/login/oauth2/code/kakao")
    public ResponseEntity getCode(@RequestParam(name = "code") String code) {
        System.out.println(code);

        UserSignUpResponse response = userService.signUp(code,
                new UserSignUpRequest("kakao", true, LocalTime.now()));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(SuccessType.CREATED_SUCCESS, response));
    }

}

package com.donkeys_today.server.presentation.user;

import com.donkeys_today.server.application.user.UserService;
import com.donkeys_today.server.common.constants.Constants;
import com.donkeys_today.server.presentation.user.dto.requset.UserSignUpRequest;
import com.donkeys_today.server.presentation.user.dto.response.UserSignUpResponse;
import com.donkeys_today.server.support.dto.ApiResponse;
import com.donkeys_today.server.support.dto.type.SuccessType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

  private final UserService userService;

  @RequestMapping("/auth/index")
  public String main(Model model) {
    return "kakao";
  }

  @PostMapping("/auth/signUp")
  public ResponseEntity<ApiResponse<?>> signUp(
      @RequestHeader(Constants.AUTHORIZATION) final String authorization_code,
      @RequestBody final UserSignUpRequest userSignUpRequest) {
    final UserSignUpResponse response = userService.signUp(authorization_code,userSignUpRequest);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(SuccessType.CREATED_SUCCESS, response));
  }

//  @GetMapping("/user")
//  public ResponseEntity<ApiResponse<?>> getUserById(@RequestParam("id") long id) {
//    return ResponseEntity.status(HttpStatus.OK)
//        .body(ApiResponse.success(SuccessType.OK_SUCCESS, userService.getUser(id)));
//  }

}

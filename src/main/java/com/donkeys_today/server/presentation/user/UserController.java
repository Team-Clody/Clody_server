package com.donkeys_today.server.presentation.user;

import com.donkeys_today.server.application.user.UserService;
import com.donkeys_today.server.application.user.dto.request.UserSignUpRequest;
import com.donkeys_today.server.common.dto.ApiResponse;
import com.donkeys_today.server.common.dto.type.SuccessType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

  private final UserService userService;

  @PostMapping("/user/signup")
  public ResponseEntity<ApiResponse<?>> signUpUser(@RequestBody UserSignUpRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(SuccessType.CREATED_SUCCESS, userService.signUp(request)));
  }

  @GetMapping("/user")
  public ResponseEntity<ApiResponse<?>> getUserById(@RequestParam("id") long id) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(SuccessType.OK_SUCCESS,userService.getUser(id)));
  }

}

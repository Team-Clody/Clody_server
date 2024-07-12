package com.donkeys_today.server.presentation.user;

import com.donkeys_today.server.application.user.UserService;
import com.donkeys_today.server.presentation.api.UserController;
import com.donkeys_today.server.presentation.user.dto.requset.UserNameChangeRequest;
import com.donkeys_today.server.presentation.user.dto.response.UserInfoResponse;
import com.donkeys_today.server.presentation.user.dto.response.UserNameChangeResponse;
import com.donkeys_today.server.support.dto.ApiResponse;
import com.donkeys_today.server.support.dto.type.SuccessType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserControllerImple implements UserController {

    private final UserService userService;


    @Override
    @GetMapping("/user/info")
    public ResponseEntity<ApiResponse<UserInfoResponse>> info() {

        UserInfoResponse response = userService.getUserInfo();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(SuccessType.OK_SUCCESS, response));

    }

    @Override
    @PatchMapping("/user/name")
    public ResponseEntity<ApiResponse<UserNameChangeResponse>> change(
            @RequestBody final UserNameChangeRequest userNameChangeRequest) {
        UserNameChangeResponse response = userService.changeUserName(userNameChangeRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(SuccessType.OK_SUCCESS, response));
    }
}

package com.donkeys_today.server.presentation.user;

import com.donkeys_today.server.application.user.UserService;
import com.donkeys_today.server.presentation.api.UserController;
import com.donkeys_today.server.presentation.user.dto.requset.UserNamePatchRequest;
import com.donkeys_today.server.presentation.user.dto.response.UserDeleteResponse;
import com.donkeys_today.server.presentation.user.dto.response.UserInfoResponse;
import com.donkeys_today.server.presentation.user.dto.response.UserNamePatchResponse;
import com.donkeys_today.server.support.dto.ApiResponse;
import com.donkeys_today.server.support.dto.type.SuccessType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    @GetMapping("/user/info")
    public ResponseEntity<ApiResponse<UserInfoResponse>> getUserInfo() {
        UserInfoResponse response = userService.getUserInfo();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(SuccessType.OK_SUCCESS, response));
    }

    @Override
    @PatchMapping("/user/nickname")
    public ResponseEntity<ApiResponse<UserNamePatchResponse>> patchUserName(
            @RequestBody final UserNamePatchRequest patchUserNameRequest) {

        UserNamePatchResponse response = userService.patchUserName(patchUserNameRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(SuccessType.OK_SUCCESS, response));
    }

    @Override
    @DeleteMapping("/user/revoke")
    public ResponseEntity<ApiResponse<UserDeleteResponse>> deleteUser() {
        UserDeleteResponse userDeleteResponse = userService.deleteUser();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(SuccessType.OK_SUCCESS, userDeleteResponse));
    }

}

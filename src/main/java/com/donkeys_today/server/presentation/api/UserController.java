package com.donkeys_today.server.presentation.api;

import com.donkeys_today.server.presentation.user.dto.requset.UserNameChangeRequest;
import com.donkeys_today.server.presentation.user.dto.response.UserInfoResponse;
import com.donkeys_today.server.presentation.user.dto.response.UserNameChangeResponse;
import com.donkeys_today.server.support.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "유저 정보")
@RequestMapping("/api/v1")
@RestController
public interface UserController {

    @Operation(summary = "유저 정보 조회 ", description = "유저 프로필 정보를 조회합니다.")
    @GetMapping("/user/info")
    ResponseEntity<ApiResponse<UserInfoResponse>> info();

    @Operation(summary = "유저 이름 변경 ", description = "유저 이름을 변경합니다.")
    @PatchMapping("/user/name")
    ResponseEntity<ApiResponse<UserNameChangeResponse>> change(
            @RequestBody(
                    description = "새로운 이름을 받는 객체",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UserNameChangeRequest.class))
            ) final UserNameChangeRequest userNameChangeRequest);


}

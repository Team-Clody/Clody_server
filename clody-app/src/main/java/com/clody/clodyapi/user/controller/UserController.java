package com.clody.clodyapi.user.controller;

import com.clody.clodyapi.user.controller.dto.request.UserNamePatchRequest;
import com.clody.clodyapi.user.controller.dto.response.UserDeleteResponse;
import com.clody.clodyapi.user.controller.dto.response.UserInfoGetResponse;
import com.clody.clodyapi.user.controller.dto.response.UserNamePatchResponse;
import com.clody.support.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "유저 관련", description = "유저와 관련된 기능을 수행하는 API 입니다.")
@RequestMapping("/api/v1")
@RestController
public interface UserController {

    @Operation(summary = "유저 정보 조회 ", description = "유저 프로필 정보를 조회합니다.")
    @GetMapping("/user/info")
    ResponseEntity<ApiResponse<UserInfoGetResponse>> getUserInfo();

    @Operation(summary = "유저 닉네임  변경", description = "유저 닉네임을 변경합니다.")
    @PatchMapping("/user/nickname")
    ResponseEntity<ApiResponse<UserNamePatchResponse>> patchUserName(@RequestBody(
            description = "바꿀 닉네임",
            required = true,
            content = @Content(schema = @Schema(implementation = UserNamePatchRequest.class))
    ) final UserNamePatchRequest patchUserNameRequest);

    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴를 진행합니다.")
    @DeleteMapping("/user/revoke")
    ResponseEntity<ApiResponse<UserDeleteResponse>> deleteUser();
}

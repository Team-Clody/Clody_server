package com.clody.clodyapi.user.controller;

import com.clody.clodyapi.diary.usecase.UserDeletionUsecase;
import com.clody.clodyapi.diary.usecase.UserRetrieverUsecase;
import com.clody.clodyapi.diary.usecase.UserUpdateUsecase;
import com.clody.clodyapi.user.controller.dto.request.UserNamePatchRequest;
import com.clody.clodyapi.user.controller.dto.response.UserDeleteResponse;
import com.clody.clodyapi.user.controller.dto.response.UserInfoGetResponse;
import com.clody.clodyapi.user.controller.dto.response.UserNamePatchResponse;
import com.clody.support.dto.ApiResponse;
import com.clody.support.dto.type.SuccessType;
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

  private final UserRetrieverUsecase userRetrieverUsecase;
  private final UserUpdateUsecase userUpdateUsecase;
  private final UserDeletionUsecase userDeletionUsecase;

  @Override
  @GetMapping("/user/info")
  public ResponseEntity<ApiResponse<UserInfoGetResponse>> getUserInfo() {
    return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.success(SuccessType.OK_SUCCESS, userRetrieverUsecase.retrieveUser()));
  }

  @Override
  @PatchMapping("/user/nickname")
  public ResponseEntity<ApiResponse<UserNamePatchResponse>> patchUserName(
          @RequestBody final UserNamePatchRequest patchUserNameRequest) {
    return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.success(SuccessType.OK_SUCCESS,
                    userUpdateUsecase.updateUserName(patchUserNameRequest)));
  }

  @Override
  @DeleteMapping("/user/revoke")
  public ResponseEntity<ApiResponse<UserDeleteResponse>> deleteUser() {
    return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.success(SuccessType.OK_SUCCESS,
                    userDeletionUsecase.deleteUser()));
  }
}

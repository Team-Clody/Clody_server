package com.donkeys_today.server.presentation.reply;

import com.donkeys_today.server.application.reply.ReplyService;
import com.donkeys_today.server.presentation.api.ReplyController;
import com.donkeys_today.server.presentation.reply.dto.response.ReplyContent;
import com.donkeys_today.server.support.dto.ApiResponse;
import com.donkeys_today.server.support.dto.type.SuccessType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ReplyControllerImpl implements ReplyController {

    private final ReplyService replyService;

    @GetMapping("/reply")
    @Override
    public ResponseEntity<ApiResponse<ReplyContent>> getReply(@RequestParam final int year,
                                                              @RequestParam final int month,
                                                              @RequestParam final int day) {
        final ReplyContent response = replyService.getReply(year, month, day);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success(SuccessType.OK_NOT_FOUND));
        }
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(SuccessType.OK_SUCCESS, response));
    }
}

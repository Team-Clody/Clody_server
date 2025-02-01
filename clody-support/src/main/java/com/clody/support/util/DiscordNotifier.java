package com.clody.support.util;

import com.clody.support.dto.DiscordWebhookDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class DiscordNotifier {
    private final WebClient webClient;

    @Value("${discord.webhook.url.register}")
    private String webhookUrlRegister;

    private final ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        System.out.println("registerWebhookUrl = " + webhookUrlRegister);
    }


    /**
     * 기본 알림 전송 메서드.
     * DTO를 JSON으로 직렬화하여 디스코드 웹훅에 전송합니다.
     * @param dto 디스코드 알림 메시지 DTO
     * @return 전송 결과 Mono<String>
     */
    public Mono<String> sendNotification(DiscordWebhookDto dto) {
        String jsonPayload;
        try {
            jsonPayload = objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            return Mono.error(e);
        }

        return webClient.post()
                .uri(webhookUrlRegister)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .retrieve()
                .bodyToMono(String.class);
    }

    /**
     * 회원가입 성공 알림 전송
     * @param email 가입한 사용자의 이메일
     * @param userCount 현재 사용자 수
     * @return 전송 결과 Mono<String>
     */
    public Mono<String> sendRegistrationNotification(String email, Long userCount) {
        DiscordWebhookDto dto = new DiscordWebhookDto(
                String.format("\uD83C\uDF40새로운 회원 가입\uD83C\uDF40\n이메일: %s\n현재 사용자 수: %s", email, userCount),
                null,   // username: 기본값 사용
                null,   // avatar_url: 기본 아바타 사용
                null,   // embeds: 필요에 따라 추가 가능
                null    // allowed_mentions: 필요에 따라 추가 가능
        );
        return sendNotification(dto);
    }

    /**
     * 에러 발생 시 알림 전송
     * @param errorMessage 발생한 에러 메시지
     * @return 전송 결과 Mono<String>
     */
    public Mono<String> sendErrorNotification(String errorMessage) {
        DiscordWebhookDto dto = new DiscordWebhookDto(
                String.format("서버 500 에러 발생: %s", errorMessage),
                null,
                null,
                null,
                null
        );
        return sendNotification(dto);
    }




}

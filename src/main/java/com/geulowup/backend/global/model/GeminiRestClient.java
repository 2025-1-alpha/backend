package com.geulowup.backend.global.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geulowup.backend.global.exception.ApiException;
import io.swagger.v3.core.util.Json;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
@Slf4j
public class GeminiRestClient {
    private final String apiKey;

    private final String systemInstruction;

    private final RestClient client;

    private final String detailUri;

    public GeminiRestClient(
            @Value("${gemini.api-key}")
            String apiKey,
            @Value("${gemini.model:gemini-1.5-flash}")
            String model,
            @Value("${gemini.system-instruction}")
            String systemInstruction
    ) {
        this.apiKey = apiKey;
        this.systemInstruction = systemInstruction;
        this.client = RestClient.builder()
                .baseUrl("https://generativelanguage.googleapis.com/v1beta/")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("X-Goog-Api-Key", apiKey)
                .build();
        this.detailUri = String.format("models/%s:generateContent", model);
    }

    public String generateAdviceContent(String userText, String[] tags) {
        Map<String, Object> requestBody = Map.of(
                "contents", new Object[]{
                        Map.of("role", "user", "parts",
                                new Object[]{Map.of("text", systemInstruction + getAdvicePrompt(userText, tags))})
                },
                "generationConfig", Map.of("responseMimeType", "text/plain")
        );

        return generateContent(userText, requestBody);
    }

    public String generatePlaceholderContent(String userText) {
        Map<String, Object> requestBody = Map.of(
                "contents", new Object[]{
                        Map.of("role", "user", "parts", new Object[]{Map.of("text", userText + getPlaceholderPrompt())})
                },
                "generationConfig", Map.of("responseMimeType", "text/plain")
        );

        return generateContent(userText, requestBody);
    }

    private String generateContent(String userText, Map<String, Object> requestBody) {
        try {
            String response = client.post()
                    .uri(uriBuilder -> uriBuilder
                            .path(detailUri)
                            .queryParam("key", apiKey)
                            .build())
                    .body(requestBody)
                    .retrieve()
                    .body(String.class);

            logRequestAndResponse(userText, requestBody, response);

            String result;

            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(response);

                result = jsonNode.get("candidates").get(0)
                        .get("content")
                        .get("parts").get(0)
                        .get("text")
                        .asText();
            } catch (JsonProcessingException exception) {
                throw new ApiException(exception);
            }

            return result;

        } catch (RestClientException e) {
            log.error("[Gemini] API 요청 실패: {}", e.getMessage());
            throw e;
        }
    }

    private String getAdvicePrompt(String userText, String[] tags) {
        return " 다음 [" + String.join(", ", tags) + "] 조건에 맞춰서 원문을 개선하여 더 좋은 글을 반환해주세요. " +
                "단, **절대로 원문에 없는 정보를 삽입하지 마세요.** " +
                "특히 이름, 소속, 전공, 날짜, 프로젝트 등 원문에 명시되지 않은 정보는 " +
                "추측하거나 빈칸으로 처리하지 말고 우회하여 문장을 생성하세요. " +
                "사용자가 입력하지 않은 항목까지 \"형식적으로 적절하겠다\"고 독단적으로 판단하여 빈칸으로 두지마세요. " +
                "원문: " + userText;
    }

    private String getPlaceholderPrompt() {
        return """
                \n
                위 글에서 빈칸을 만들어서 글쓰기 템플릿을 만들려해.\s
                매우 중요한 주의사항: 빈칸 외 내용을 추가/변형/삭제하는 행위를 엄격히 금지함\s
                1. 다른 사용자들이 사용하기 편하도록 수정가능한 정보(날짜, 이름, 장소 등등)들은 사족없이 모두 빈칸 표시해.\s
                2. 빈칸의 유형은 {시간}, {장소}, {이름} 그 외의 범주도 가능하니까 { }안에 반드시 한 단어로 표시해줘\
                예를 들어 {어려움 내용}은 안돼. {내용}으로 통일해줘\s
                3. 원문에서 {}표시한 내용이 있다면 건너뛰어\s
                4. 원문에서 언급하지 않은 내용은 절대 덧붙이지 마. 수정할 수 있는 건 오직 빈칸뿐이야\s
                5. 원문의 내용과 표현을 추가/변형/삭제하는 행위를 엄격히 금지함.\s
                오직 줄글만 반환해줘. 엔터도 포함해서""";
    }

    private void logRequestAndResponse(String input, Map<String, Object> request, String response) {
        log.info("[Gemini 요청]");
        log.info("request: {}", Json.pretty(request));
        log.info("Prompt: {}", input);
        log.info("[Gemini 응답]");
        log.info(response);
    }
}

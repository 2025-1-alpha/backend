package com.geulowup.backend.domain.models.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geulowup.backend.domain.models.dto.request.ModelAdviceRequest;
import com.geulowup.backend.domain.models.dto.request.ModelPlaceholderRequest;
import com.geulowup.backend.domain.models.dto.response.ModelResponse;
import com.geulowup.backend.global.exception.ApiException;
import com.geulowup.backend.global.model.GeminiRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ModelService {
    private final GeminiRestClient geminiRestClient;

    public ModelResponse getModelAdvice(ModelAdviceRequest request) {
        String json = geminiRestClient.generateAdviceContent(
                request.content(),
                request.tags().toArray(new String[0])
        );

        String result = null;

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(json);

            result = jsonNode.get("candidates").get(0)
                    .get("content")
                    .get("parts").get(0)
                    .get("text")
                    .asText();
        } catch(JsonProcessingException exception) {
            throw new ApiException(exception);
        }

        return ModelResponse.builder()
                .result(result)
                .build();
    }

    public ModelResponse getModelPlaceholderResults(ModelPlaceholderRequest request) {
        String result = geminiRestClient.generatePlaceholderContent(request.content());

        return ModelResponse.builder()
                .result(result)
                .build();
    }
}

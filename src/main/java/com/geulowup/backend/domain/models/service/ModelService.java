package com.geulowup.backend.domain.models.service;

import com.geulowup.backend.domain.models.dto.request.ModelAdviceRequest;
import com.geulowup.backend.domain.models.dto.request.ModelPlaceholderRequest;
import com.geulowup.backend.domain.models.dto.response.ModelResponse;
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
        String result = geminiRestClient.generateAdviceContent(
                request.content(),
                request.tags().toArray(new String[0])
        );

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

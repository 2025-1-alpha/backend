package com.geulowup.backend.domain.models.controller;

import com.geulowup.backend.domain.models.dto.request.ModelAdviceRequest;
import com.geulowup.backend.domain.models.dto.request.ModelPlaceholderRequest;
import com.geulowup.backend.domain.models.dto.response.ModelResponse;
import com.geulowup.backend.domain.models.service.ModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/models")
@RequiredArgsConstructor
public class ModelController {
    private final ModelService modelService;

    @PostMapping("/advices")
    public ResponseEntity<ModelResponse> getModelAdvice(@RequestBody ModelAdviceRequest request) {

        return ResponseEntity.ok(modelService.getModelAdvice(request));
    }

    @PostMapping("/placeholders")
    public ResponseEntity<ModelResponse> getModelPlaceholderResults(@RequestBody ModelPlaceholderRequest request) {

        return ResponseEntity.ok(modelService.getModelPlaceholderResults(request));
    }
}

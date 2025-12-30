package com.v1.manfaa.Controller;

import com.v1.manfaa.DTO.In.Ai.EstimateHoursDTOIn;
import com.v1.manfaa.DTO.In.Ai.QueryRagDTOIn;
import com.v1.manfaa.DTO.In.Ai.RankBidsRequestDTOIn;
import com.v1.manfaa.Model.User;
import com.v1.manfaa.Service.AiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class AiController {
    private final AiService aiService;

    @PostMapping("/ask-rag")
    public ResponseEntity<?> askRag(@RequestBody @Valid QueryRagDTOIn question, @AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(aiService.askRAG(question, user.getId()));
    }

    @PostMapping("/suggest-hours")
    public ResponseEntity<?> suggestHours(@RequestBody @Valid EstimateHoursDTOIn question, @AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(aiService.estimateHours(question, user.getId()));
    }

    @PostMapping("/rank-bids")
    public ResponseEntity<?> rankBids(@RequestBody @Valid RankBidsRequestDTOIn question, @AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(aiService.suggestUser(question, user.getId()));
    }
}

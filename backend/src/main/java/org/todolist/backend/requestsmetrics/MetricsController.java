package org.todolist.backend.requestsmetrics;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.todolist.backend.requestsmetrics.dto.response.MetricsResponse;

@RequestMapping("/metrics")
@RestController
@RequiredArgsConstructor
public class MetricsController {
    private final MetricsService metricsService;

    @GetMapping("/requests")
    public ResponseEntity<MetricsResponse> getRequestsCount() {
        return ResponseEntity.ok(metricsService.getRequestsCount());
    }
}

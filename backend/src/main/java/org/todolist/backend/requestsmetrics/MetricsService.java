package org.todolist.backend.requestsmetrics;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.stereotype.Service;
import org.todolist.backend.requestsmetrics.dto.response.MetricsResponse;

@Service
@RequiredArgsConstructor
public class MetricsService {
    private final HttpExchangeRepository httpExchangeRepository;
    public MetricsResponse getRequestsCount() {
        return new MetricsResponse(httpExchangeRepository.findAll().size());
    }
}

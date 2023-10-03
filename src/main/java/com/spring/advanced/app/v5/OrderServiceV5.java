package com.spring.advanced.app.v5;


import com.spring.advanced.trace.callback.TraceTemplate;
import com.spring.advanced.trace.logtrace.LogTrace;
import com.spring.advanced.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
//@RequiredArgsConstructor
public class OrderServiceV5 {

    private final OrderRepositoryV5 orderRepository;
    private final TraceTemplate template;

    public OrderServiceV5(OrderRepositoryV5 orderRepository, LogTrace trace) {
        this.orderRepository = orderRepository;
        this.template = new TraceTemplate(trace);
    }

    public void orderItem(String itemId) {

        template.execute("OrderServiceV1.orderItem()", () -> {
            orderRepository.save(itemId);
            return null;
        });
    }
}

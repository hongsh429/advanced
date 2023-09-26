package com.spring.advanced.trace.helloTrace;

import com.spring.advanced.trace.TraceStatus;
import com.spring.advanced.trace.helloTrace.HelloTraceV1;
import org.junit.jupiter.api.Test;

class HelloTraceV1Test {

    @Test
    public void begin_end() throws Exception {
        HelloTraceV1 trace = new HelloTraceV1();
        TraceStatus status = trace.begin("hello");
        trace.end(status);
    }

    @Test
    void begin_exception() {
        HelloTraceV1 trace = new HelloTraceV1();
        TraceStatus status = trace.begin("hello");
        trace.exception(status, new IllegalStateException());
    }

    @Test
    public void test1() throws Exception {
        HelloTraceV1 trace = new HelloTraceV1();
        TraceStatus status = trace.begin("hello");
        status.getTraceId().createNextId();
        status.getTraceId().createNextId();
        status.getTraceId().createNextId();
        trace.end(status);
    }

}
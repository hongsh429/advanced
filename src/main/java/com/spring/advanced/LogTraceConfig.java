package com.spring.advanced;


import com.spring.advanced.trace.logtrace.LogTrace;
import com.spring.advanced.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogTraceConfig {

//    @Bean
//    public LogTrace logTrace() {
//        return new FieldLogTrace();
//    }

    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }
}

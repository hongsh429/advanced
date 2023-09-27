package com.spring.advanced.trace.strategy;


import com.spring.advanced.trace.strategy.code.strategy.ContextV2;
import com.spring.advanced.trace.strategy.code.strategy.Strategy;
import com.spring.advanced.trace.strategy.code.strategy.StrategyLogic1;
import com.spring.advanced.trace.strategy.code.strategy.StrategyLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

@Slf4j
public class ContextV2Test {


    /**
     * 전략 패턴 적용
     */
    @Test
    void strategyV2() {
        ContextV2 context = new ContextV2();
        context.execute(new StrategyLogic1());
        context.execute(new StrategyLogic2());
    }

    @Test
    void strategyV2_2() {
        ContextV2 context = new ContextV2();
        context.execute(() -> log.info("비지니스 로직 실행한다잉~!"));
    }
}

package com.spring.advanced.trace.threadLocal;


import com.spring.advanced.trace.threadLocal.code.FieldService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class FieldServiceTest {

    private FieldService fieldService = new FieldService();

    @Test
    @DisplayName("여기서는 동시성 문제가 발생하지 않음")
    void field(){
        log.info("main start");

        CountDownLatch latch = new CountDownLatch(2);

        Runnable userA = () -> {
            fieldService.logic("userA");
        };

        Runnable userB = () -> {
            fieldService.logic("userB");
        };

        Thread threadA = new Thread(userA);
        threadA.setName("thread-A");
        Thread threadB = new Thread(userB);
        threadB.setName("thread-B");


        threadA.start();
        sleep(2000);
        threadB.start();

        sleep(3000); // 메인쓰레드 종료 대기. 왜냐면 총 3개의 쓰레드가 실행되는데, 메인쓰레드가 이들을 기다려주지 않으므로.
                            // 이 문제를 해결하려면 countDownLatch를 사용해야한다,.!
        log.info("main exit");

        /*
         저장 name=user1 -> nameStore=null
         조회 nameStore=user1
         저장 name=user2 -> nameStore=user1
         조회 nameStore=user2
        */
    }
    @Test
    @DisplayName("여기서는 동시성 문제가 발생하지 않음 - CountDownLatch 사용")
    void field2() throws InterruptedException {
        log.info("main start");
        int threadCount = 2;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            int finalI = i+1;
            try{
                executorService.submit(() -> fieldService.logic("user" + finalI));
                sleep(2000);
            } finally {
                latch.countDown();
            }
        }

        latch.await();
//        sleep(2000); // 메인쓰레드 종료 대기. 왜냐면 총 3개의 쓰레드가 실행되는데, 메인쓰레드가 이들을 기다려주지 않으므로.
        // 이 문제를 해결하려면 countDownLatch를 사용해야한다,.!
        log.info("main exit");


        /*
         저장 name=user1 -> nameStore=null
         조회 nameStore=user1
         저장 name=user2 -> nameStore=user1
         조회 nameStore=user2

         이게 가능한 이유! 로직에선 1초를 쉬게해준뒤 조회하는데,
         다음 저장 로직이 실행되는건 2초 뒤 이므로, 조회까지 충분히 기다려주었다가 다음 쓰레드가 실행된다~!
        */
    }

    @Test
    @DisplayName("여기서는 동시성 문제가 발생")
    void field3(){
        log.info("main start");

        CountDownLatch latch = new CountDownLatch(2);

        Runnable userA = () -> {
            fieldService.logic("userA");
        };

        Runnable userB = () -> {
            fieldService.logic("userB");
        };

        Thread threadA = new Thread(userA);
        threadA.setName("thread-A");
        Thread threadB = new Thread(userB);
        threadB.setName("thread-B");


        threadA.start();
        sleep(100);
        threadB.start();

        sleep(2000); // 메인쓰레드 종료 대기. 왜냐면 총 3개의 쓰레드가 실행되는데, 메인쓰레드가 이들을 기다려주지 않으므로.
        // 이 문제를 해결하려면 countDownLatch를 사용해야한다,.!
        log.info("main exit");

        /*
         저장 name=userA -> nameStore=null
         저장 name=userB -> nameStore=userA
         조회 nameStore=userB
         조회 nameStore=userB
        */
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

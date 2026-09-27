package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        // ThreadA : 클라이언트A가 1000원 주문
        statefulService1.order("clientA",1000);

        // ThreadB : 클라이언트B가 2000원 주문
        statefulService2.order("clientB",2000);

        // ThreadA의 주문 금액
        int priceA = statefulService1.getPrice();
        assertThat(priceA).isNotEqualTo(100);
        assertThat(priceA).isEqualTo(2000); // ThreadB에 의해서 price값에 수정이 일어남 → 동시성(concurrency) 문제 발생
    }

    @Configuration
    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }
}
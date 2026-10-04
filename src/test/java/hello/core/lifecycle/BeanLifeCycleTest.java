package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest() {

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient networkClient = ac.getBean("networkClient", NetworkClient.class);

        // 스프링 컨테이너 종료 : 빈 라이프 사이클 중 종료 사이클을 공부하기 위해 명시적으로 닫아 줌
        ac.close();


    }

    @Configuration
    static class LifeCycleConfig {
        @Bean(initMethod = "init", destroyMethod = "close")
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient(); // 객체 생성
            networkClient.setUrl("http://wwww...."); // 의존 관계 주입
            return networkClient;
        }
    }
}

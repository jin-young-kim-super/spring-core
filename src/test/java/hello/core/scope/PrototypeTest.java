package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.assertThat;

public class PrototypeTest {

    @Test
    void prototypeBeanFind() {

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        // getBean에 의해 호출(사용) 시에 프로토타입 생성
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);

        assertThat(prototypeBean1).isNotSameAs(prototypeBean2);

        // 클라이언트(개발자 등)에서 소멸을 해줘야 한다
        prototypeBean1.close();
        prototypeBean2.close();
    }

    @Scope("prototype")
    static class PrototypeBean {
        @PostConstruct
        void init() {
            System.out.println("초기화 콜백 호출");
        }

        /**
         * 초기화 작업까지만 관리되게 때문에 소멸 콜백은 호출이 안됨
         * → 고로, 싱글콘과는 달리 클라이언트(개발자 등)에서 소멸을 해줘야 한다.
         */
        @PreDestroy
        void close() {
            System.out.println("소멸 콜백 호출");
        }
    }
}

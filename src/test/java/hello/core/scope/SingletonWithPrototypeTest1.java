package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind() {

        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext(PrototypeBean.class);

        // 프로토타입 생성
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();

        // 프로토타입 생성
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();

        assertThat(prototypeBean1.getCount()).isEqualTo(1);
        assertThat(prototypeBean2.getCount()).isEqualTo(1);

    }

    @Test
    void singltonClientUsePrototype() {

        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext(ClientBean.class,PrototypeBean.class);

        ClientBean clientA = ac.getBean(ClientBean.class);
        int countA = clientA.logic();
        assertThat(countA).isEqualTo(1);

        ClientBean clientB = ac.getBean(ClientBean.class);
        int countB = clientB.logic();
        assertThat(countB).isEqualTo(2);

    }

    @Scope("singleton")
    static class ClientBean {
        private final PrototypeBean prototypeBean;

        @Autowired
        public ClientBean(PrototypeBean prototypeBean) {
            this.prototypeBean = prototypeBean;
        }

        // 프로토타입 빈 간접 호출
        public int logic() {
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }


    @Scope("prototype")
    static class PrototypeBean {

        private int count = 0;

        public void addCount() {
            this.count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        void init() {
            System.out.println("PrototypeBean.init() " + this);
        }

        @PreDestroy
        void close() {
            System.out.println("PRototypeBean.close() " + this);
        }
    }
}

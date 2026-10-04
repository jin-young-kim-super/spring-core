package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
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
        assertThat(countB).isEqualTo(1);

    }

    @Scope("singleton")
    static class ClientBean {
        @Autowired
        private ObjectProvider<PrototypeBean> prototypeBeanProvider;

        /**
         * ObjectProvider는 ObjectFactory를 상속받고 있으며 아래와 같이 써도 ㄱㅊ!
         * -> ObjectProvider가 편의 기능이 더 있다.
         * 그리고 ObjectProvider, ObjectFactory는 스프링 제공 기능이므로 스프리 의존적이다 ㅠㅠ
         */
        //private ObjectFactory<PrototypeBean> prototypeBeanProvider;
        public int logic() {
            // Provider : 스프링 컨테이너에 PrototypeBean을 개발자가 직접 조회/요청
            // -> logic()호출 때마다 스프링 컨테이너에 프로토타입 빈을 조회/요청하므로, 새로운 프로토타입 생성
            // getBean(PrototypeBean.class)하는 게 귀찮으니, Provider가 개발자가 조금이나마 편리하게 직접 조회할 수 있도록
            // 도와 주는 기능이 본질이다. 즉 본질은 DL 기능 유틸리티이다~!
            PrototypeBean prototypeBean = prototypeBeanProvider.getObject();
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
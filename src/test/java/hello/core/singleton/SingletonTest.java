package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수 DI 컨테인의 문제점")
    void pureContainer() {
        AppConfig appConfig = new AppConfig();

        // 클라이언트 A 요청
        MemberService memberService1 = appConfig.memberService();
        // 클라이언트 B 요청
        MemberService memberService2 = appConfig.memberService();

        // 참조값이 서로 다른 : 클라이언트 요청마다 객체 생성됨(메모리 낭비)
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        assertThat(memberService1).isNotSameAs(memberService2);

    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest() {
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        assertThat(singletonService1).isSameAs(singletonService2);
    }

    /**
     * 스프링은 싱글톤의 단점을 모두 제거한 DI 컨테이너를 자동으로 만들어 준다
     */
    @Test
    @DisplayName("스프링 싱글톤 DI 컨테이너")
    void springDIcontainer() {

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberService memberService1 = ac.getBean("memberService", MemberService.class);
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);

        assertThat(memberService1).isSameAs(memberService2);
    }




}

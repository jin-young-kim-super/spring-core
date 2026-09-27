package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigurationSingletonTest {

    @Test
    @DisplayName("과연 MemoryMemberRepository는 싱글톤이 아닐까?")
    void configurationTest() {

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean(MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean(OrderServiceImpl.class);

        MemberRepository memberRespository1 = memberService.getMemberRespository();
        MemberRepository memberRespository2 = orderService.getMemberRespository();

        // memberRespository는 싱글톤이다.
        assertThat(memberRespository1).isSameAs(memberRespository2);
    }

    @Test
    @DisplayName("@Configuration의 바이트 코드 조작으로 싱글톤 등록 보장")
    void configurationDeep() {

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig appconfig = ac.getBean(AppConfig.class);

        // 출력 : appconfig.getClass() = class hello.core.AppConfig$$SpringCGLIB$$0
        // → AppConfig를 상속 받아서 스프링이 CGLIB라는 바이트코드 조작 라이브러리 사용하여 임의의 다른 클래스로 등록!
        // @Configuration의 이런 기능 덕분에 싱글톤이 보장이 된다.(그래서 만약 AppConfig에 @Configuration이 없고 @Bean만 있는 경우, 스프링 컨테이너에 빈이 등록은 되지만, 싱글톤 등록이 안된다)
        System.out.println("appconfig.getClass() = " + appconfig.getClass());
    }
}
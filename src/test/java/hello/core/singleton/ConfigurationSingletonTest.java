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
}

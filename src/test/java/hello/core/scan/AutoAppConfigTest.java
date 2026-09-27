package hello.core.scan;

import hello.core.AutoAppConfig;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class AutoAppConfigTest {

    @Test
    void basicScan() {
        // 자동 빈 등록과 수동 빈 중복 등록 시, 에러 발생하지 x
        // -> 항상 수동 빈 등록이 우선권을 가지기 때문에 자동 빈은 등록x
        // 그러나 이런 처리는 좋지 않은 처리이다!! 다른 개발자는 이 빈 등록이 자동인지 수동 등록인지 왠만해서는 잘 모른다.
        // 그로 인해 잡기 어려운 버그를 생산해버린다. 그래서 스프링 부트(순수 스프링x)에서는 자동 빈, 수동 빈 충돌 시 아예 오류를 내버린다.
        // ※CoreApplication에서 @SprintbootComponetScan을 통해서 실행하면 중복 등록 예외가 터질 거다.
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
        MemberService memberService = ac.getBean(MemberService.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }
}

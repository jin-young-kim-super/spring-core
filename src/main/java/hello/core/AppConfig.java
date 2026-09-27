package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfig {

    /**
     * MemoryMemberRepository 객체는 아래 과정에서 2번 호출돼 싱글톤이 꺠지는 것처럼 보인다
     * 1. memberService() -> new MemoryMemberRepository()
     * 2. orderService() -> new MemoryMemberRespository()
     * →　＠Configuration에 의해서 위 같은 상황에도 불구하고 싱글톤이 보장된다.
     * @Configuration은 싱글톤 등록 보장을 위한 것이나 다름없다. 구체적인 동작 방식에 대해서는 다음 시간에 설명하겠다
     */

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        System.out.println("AppConfig :: memberRespository()"); // 스프링 빈 등록 시, 이 로그는 딱 1번만 출력된다! 즉 memberRespository()가 1번만 호출돼서 싱글톤 등록을 보장한다
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(),discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        return new FixDiscountPolicy();
    }
}

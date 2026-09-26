package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRespitory;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

public class AppConfig {

    /**
     * 현재 AppConfig의 문제점
     * -> 애플리케이션에서 사용되는 모든 인터페이스 목록과 그에 따른 구현체 정보가 한 눈에 안 보인다.
     * MemberService, OrderService인터페이스는 보이지만 MemoryRepository, DiscountPolicy 인터페이스와 그 구현체가 파악이 안된다
     * 아래처럼 리팩터링함으로써 애플리케이션 전체 구성을 한 눈에 파악할 수가 있다.
     */

    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    private MemberRepository memberRepository() {
        return new MemoryMemberRespitory();
    }

    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(),discountPolicy());
    }

    public DiscountPolicy discountPolicy() {
        return new FixDiscountPolicy();
    }
}

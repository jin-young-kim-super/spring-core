package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    @Autowired
    public OrderServiceImpl(
            MemberRepository memberRepository,
            // @Qualifier("mainDiscountPolicy")가 붙은 빈을 찾아 의존 관계 주입
            // → @Qualifier("main..")을 찾지 못하면, mainDiscountPolicy이름의 빈을 찾아 등록한다.
            // 그러나 @Qualifier는 @Qualifier을 찾는 용도로만 써야 한다.
            @Qualifier("mainDiscountPolicy") DiscountPolicy discountPolicy
    ) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member findMember = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(findMember, itemPrice);

        return new Order(memberId,itemName,itemPrice,discountPrice);
    }

    // 테스트 용도
    public MemberRepository getMemberRespository() {
        return this.memberRepository;
    }
}

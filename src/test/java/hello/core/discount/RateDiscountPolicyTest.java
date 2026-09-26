package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RateDiscountPolicyTest {

    DiscountPolicy discountPolicy = new RateDiscountPolicy();


    @Test
    @DisplayName("VIP는 10% 할인이 적용돼야 한다")
    void vip_o() {

        //given
        Member member = new Member(1L, "memberA", Grade.VIP);

        //when
        int discount = discountPolicy.discount(member, 10000);

        //then
        assertThat(discount).isEqualTo(1000);

    }


    @Test
    @DisplayName("VIP는 10% 할인이 적용돼지 않아야 한다")
    void vip_x() {

        //given
        Member member = new Member(1L, "memberA", Grade.BASIC);

        //when
        int discount = discountPolicy.discount(member, 10000);

        //then
        assertThat(discount).isEqualTo(0);

    }

}
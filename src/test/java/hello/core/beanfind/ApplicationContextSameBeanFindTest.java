package hello.core.beanfind;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextSameBeanFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanConfig.class);

    @Configuration
    static class SameBeanConfig {
        @Bean
        public MemberRepository memberRepository1() {
            return new MemoryMemberRepository();
        }

        @Bean
        public MemberRepository memberRepository2() {
            return new MemoryMemberRepository();
        }
    }

    @Test
    @DisplayName("타입 조회 시, 같은 타입이 둘 이상 존재 시, 중복 오류 발생")
    void findBeanByTypeDuplicate1() {
        assertThrows(NoUniqueBeanDefinitionException.class ,
                ()->ac.getBean(MemberRepository.class)
        );
    }

    @Test
    @DisplayName("중복 오류 발생 시 : 빈 이름 지정")
    void findBeanByTypeDuplicate2() {
        MemberRepository memberRepository = ac.getBean("memberRepository2", MemberRepository.class);
        assertThat(memberRepository).isInstanceOf(MemoryMemberRepository.class);
    }

    @Test
    @DisplayName("중복된 타입 빈 모두 꺼내기")
    void findBeanByTypeDuplicate3() {
        Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);
        for (String key : beansOfType.keySet()) {
            System.out.println("bean = " + beansOfType.get(key));
        }
        assertThat(beansOfType.size()).isEqualTo(2);
    }
}

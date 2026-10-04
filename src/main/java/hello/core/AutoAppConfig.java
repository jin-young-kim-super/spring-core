package hello.core;


import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * 김영한 실무 팁 : AutoConfig 클래스를 프로젝트 최상단에 위치하게 함
 * -> 디폴트 자동 스캔 대상의 범위가 AutoConfig 클래스가 속한 패키지 이하이기 때문에, 프로젝트의 모든 @Coponent가 붙인 클래스를 빈 등록한다
 * 그리고 이러한 설정 정보 파일 같은 경우는 프로젝트를 대표하는 파일이기 떄문에 개발자들이 바로 찾을 수 있게 최상단에 두는 것이 좋다.
 */

@Configuration
@ComponentScan(
        // hello.core 패키지 이하를 자동 컴포넌트 스캔의 대상으로 지정
        basePackages = "hello.core",

        // AutoAppConfig 클래스가 있는 패키지 이하를 자동 컴포넌트 스캔의 대상으로 지정
        basePackageClasses = AutoAppConfig.class,

        // AppConfig.class를 자동 컴포넌트 스캔에서 제외!
        // -> @ComponentScan 내부에 @Component 애노테이션이 있다.
        excludeFilters = @ComponentScan.Filter(type= FilterType.ANNOTATION,classes = Configuration.class)
)
public class AutoAppConfig {

    // 수동 등록 vs 자동 등록 사이의 빈 중복 등록
    //@Bean(name = "memoryMemberRepository")
//    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//    }
}

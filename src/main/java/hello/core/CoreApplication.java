package hello.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication 안에 @ComponentScan이 있다
// -> 그래서 스프링 부트를 사용하면 프로젝트 최상단 CoreApplication이 자동 생성되므로, AutoConfig 클래스를 만들지 않아도 서버를 띄우는 동시에 자동 빈 등록을 한다.
// 참고 사항 : @SpringBootApplication안에는 @ComponentScan을 포함한 여러 애노테이션이 있다. 자바에는 특정 애노테이션 안의 있는 다른 애노테이션을 식별하는 기능이 없고, 그 식별 기능은 스프링의 기능임에 주의!
@SpringBootApplication
public class CoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}

}

package hello.core.annotation;


import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
/**
 * 위 4개는 @Qualifier에 있는 애노테이션을 그대로 들고 옴
 */
@Qualifier("mainDiscountPolicy")
public @interface MainDiscountPolicy {
/**
 * 저번 어딘가에서도 언급을 하였지만, 자바에는 에노테이션 상속 기능이 없다.
 * 이렇게 여러 에노테이션의 조합으로 에노테이션을 만들 수 있는 것은 스프링의 기능이다.
 */
}

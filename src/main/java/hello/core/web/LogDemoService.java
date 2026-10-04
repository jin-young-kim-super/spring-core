package hello.core.web;


import hello.core.common.MyLogger;
import jakarta.inject.Provider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogDemoService {

    private final MyLogger myLogger;

    public void logic(String id) {
        // MyLogger를 상속받은 프록시 객체에서 메서드 호출
        // -> 의존 관계에 웹 스코프 빈이 존재하며  HTTP 요청 때마다 생성
        myLogger.log("service id = " + id);
    }
}

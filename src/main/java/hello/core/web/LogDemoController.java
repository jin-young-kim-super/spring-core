package hello.core.web;

import hello.core.common.MyLogger;
import jakarta.inject.Provider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
    // MyLogger는 스코프가 request이기에, HTTP 요청이 없으면 의존관계 주입이 애초에 안된다.
    // -> Provider를 사용하여 해결(처음에는 Proxy객체가 주입된다)
    private final MyLogger myLogger;

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) {

        String requestURL = request.getRequestURL().toString();
        // MyLogger를 상속받은 프록시 객체에서 메서드 호출
        // -> 의존 관계에 웹 스코프 빈이 존재하며 HTTP 요청 때마다 생성
        myLogger.setRequestURL(requestURL);
        myLogger.log("controller test");

        logDemoService.logic("testId");
        return "ok";
    }
}

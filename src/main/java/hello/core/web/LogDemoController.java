package hello.core.web;

import hello.core.common.MyLogger;
import jakarta.inject.Provider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
    // MyLogger는 스코프가 request이기에, HTTP 요청이 없으면 의존관계 주입이 애초에 안된다.
    // -> ObjectProvider를 사용하여 해결(처음에는 Proxy객체가 주입된다)
    private final ObjectProvider<MyLogger> myLoggerObjectProvider;

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) {
        // 사용 시점, 즉 HTTP 요청이 들어 오는 시점에 DL을 통해 의존 관계 주입
        MyLogger myLogger = this.myLoggerObjectProvider.getObject();
        String requestURL = request.getRequestURL().toString();
        myLogger.setRequestURL(requestURL);
        myLogger.log("controller test");

        logDemoService.logic("testId");
        return "ok";
    }
}

package hello.core.lifecycle;


import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

public class NetworkClient {

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + this.url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // 서비스 시작 시 호출(초기화 작업) : 외부 서버 연결 작업
    public void connect() {
        System.out.println("connect : " + this.url);
    }

    // 서버에 데이터 전송
    public void send(String message) {
        System.out.println("send : " + this.url + " message : " + message);
    }

    // 서비스 종료 시 호출 : 외부 서버 연결 해제 작업
    public void disconnect() {
        System.out.println("disconnect : " + this.url);
    }

    // 초기화 콜백 메서드
    @PostConstruct
    public void init()  {
        connect(); // 초기화 작업
        send("초기화 연결 메시지 전송");
    }

    // 소멸전 콜백 메서드
    @PreDestroy
    public void close() {
        disconnect();
    }
}

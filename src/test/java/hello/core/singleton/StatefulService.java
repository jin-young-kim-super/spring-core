package hello.core.singleton;

/**
 * 싱글톤을 stateful하게 설계한 예시
 */

public class StatefulService {

    private int price; // price 상태 유지

    public void order(String name, int price) {
        System.out.println("name = " + name + "price = " + price );
        this.price = price; //여기가 문제 : 상태 유지 필드를 사용할 수밖에 없는 상황이라도 읽기만 해야 하는데, 수정이 일어났다.
    }

    public int getPrice() {
        return this.price;
    }
}

package me.tvhook.tvwebhook.domain.order;

import java.security.NoSuchAlgorithmException;
import me.tvhook.tvwebhook.common.enums.OrderSide;
import me.tvhook.tvwebhook.common.enums.OrderType;
import me.tvhook.tvwebhook.domain.user.User;
import me.tvhook.tvwebhook.domain.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("주문 가능 정보 조회 성공")
    public void getOrderChance() {
        User user = userRepository.findById(9999L).get();
        orderService.getChance(user, "KRW-BTC");

    }

    @Test
    @DisplayName("시장가 매수 성공")
    public void order_market_bid_success() throws NoSuchAlgorithmException {
        OrderRequestDto requestDto = OrderRequestDto.builder()
            .userId(9999L)
            .market("KRW-VET")
            .orderType(OrderType.price)
            .side(OrderSide.bid)
            .price("5100")
            .build();

        System.out.println("requestDto = " + requestDto);

//        orderService.create(requestDto);

    }

    @Test
    @DisplayName("시장가 매도 성공")
    public void order_market_ask_success() throws NoSuchAlgorithmException {
        OrderRequestDto requestDto = OrderRequestDto.builder()
            .userId(9999L)
            .market("KRW-VET")
            .orderType(OrderType.market)
            .side(OrderSide.ask)
            .volume("30.35714285")
            .price(null)
            .build();

        System.out.println("requestDto = " + requestDto);

//        orderService.create(requestDto);

    }


}
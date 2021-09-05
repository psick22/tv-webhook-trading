package me.tvhook.tvwebhook.domain.webhook;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.tvhook.tvwebhook.api.Upbit;
import me.tvhook.tvwebhook.api.dto.UpbitAccountDto;
import me.tvhook.tvwebhook.api.dto.UpbitOrderChanceResponseDto;
import me.tvhook.tvwebhook.api.dto.UpbitOrderResponseDto;
import me.tvhook.tvwebhook.common.enums.OrderSide;
import me.tvhook.tvwebhook.common.enums.OrderType;
import me.tvhook.tvwebhook.domain.order.OrderRepository;
import me.tvhook.tvwebhook.domain.order.OrderRequestDto;
import me.tvhook.tvwebhook.domain.order.OrderService;
import me.tvhook.tvwebhook.domain.user.User;
import me.tvhook.tvwebhook.domain.user.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/webhook")
public class WebhookController {

    private final UserRepository userRepository;
    private final OrderService orderService;

    private final Upbit upbit;

    @GetMapping
    public String signal() {

        log.info("test");

        return "test";

    }

    /**
     * { "ticker":{{ticker}}, "exchange":{{exchange}}, "candleTime":{{time}}, "time":{{timenow}},
     * "strategy": { "orderAction":{{strategy.order.action}}, "orderPrice":
     * {{strategy.order.price}}, "orderId": {{strategy.order.id}} } }
     *
     * @param webhook
     */
    @PostMapping
    public void signal(HttpServletRequest request, @RequestBody Webhook webhook)
        throws NoSuchAlgorithmException {
        // usedId -> get apiKey
        // market
        log.info("webhook = {}", webhook.toString());
        String market = null;

        if (webhook.getExchange().equals("UPBIT")) {
            String ticker = webhook.getTicker().replace("KRW", ""); // BTCKRW => BTC
            market = "KRW" + "-" + ticker;
        }
//        // 바이낸스 처리 필요
//        else if (webhook.getExchange().equals("BINANCE")) {
//
//        }

        User user = userRepository.findByUsername(webhook.getUsername()).get(0);
        UpbitOrderChanceResponseDto chance = upbit.orderChance(user, market);

        BigDecimal fee = null;
        UpbitAccountDto bidAccount = chance.getBid_account();
        UpbitAccountDto askAccount = chance.getAsk_account();

        if (webhook.getStrategy().getOrderId().equals(PositionType.LONG_BUY)) {
            int compareResult = bidAccount.getBalance().compareTo(BigDecimal.valueOf(200000));

            if (bidAccount.getCurrency().equals("KRW") && compareResult >= 0) {
                OrderRequestDto orderReq = OrderRequestDto.builder()
                    .userId(user.getId())
                    .side(OrderSide.bid)
                    .price("200000")
                    .orderType(OrderType.price)
                    .build();
                orderService.create(user, orderReq);
            }
        } else if (webhook.getStrategy().getOrderId().equals(PositionType.LONG_SELL)) {

            int compareResult = askAccount.getBalance().compareTo(BigDecimal.ZERO);

            if (compareResult > 0) {
                OrderRequestDto orderReq = OrderRequestDto.builder()
                    .userId(user.getId())
                    .side(OrderSide.ask)
                    .volume(String.valueOf(askAccount.getBalance()))
                    .orderType(OrderType.market)
                    .build();

                orderService.create(user, orderReq);
            }
        }
    }
}

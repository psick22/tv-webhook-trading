package me.tvhook.tvwebhook.domain.webhook;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.tvhook.tvwebhook.api.Upbit;
import me.tvhook.tvwebhook.api.dto.UpbitAccountDto;
import me.tvhook.tvwebhook.api.dto.UpbitOrderChanceResponseDto;
import me.tvhook.tvwebhook.common.enums.OrderSide;
import me.tvhook.tvwebhook.common.enums.OrderType;
import me.tvhook.tvwebhook.domain.order.OrderRequestDto;
import me.tvhook.tvwebhook.domain.order.OrderService;
import me.tvhook.tvwebhook.domain.user.User;
import me.tvhook.tvwebhook.domain.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/webhook")
public class WebhookController {

    private final UserRepository userRepository;
    private final OrderService orderService;
    private final WebhookService webhookService;

    private final Upbit upbit;

    @GetMapping
    public ResponseEntity<WebhookDto> getWebhookTemplate(
        @RequestParam(name = "type") WebhookType type,
        @RequestParam(name = "username") String username,
        @RequestParam(name = "strategyName") String strategyName,
        @RequestParam(name = "bidAmount") String bidAmount,
        @RequestParam(name = "askRate") String askRate) {

        WebhookDto template = webhookService.generateMessageTemplate(username, type, strategyName,
            bidAmount, askRate);

        return ResponseEntity.ok(template);
    }

    /**
     * { "ticker":{{ticker}}, "exchange":{{exchange}}, "candleTime":{{time}}, "time":{{timenow}},
     * "strategy": { "orderAction":{{strategy.order.action}}, "orderPrice":
     * {{strategy.order.price}}, "orderId": {{strategy.order.id}} } }
     *
     * @param webhook
     */
    @PostMapping
    public void signal(@RequestBody WebhookDto webhook)
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

        if (webhook.getOrderId().equals(PositionType.LONG_BUY)) {
            BigDecimal bidPrice = BigDecimal.valueOf(Long.parseLong(webhook.getBidAmount()));
            int compareResult = bidAccount.getBalance().compareTo(bidPrice);

            if (bidAccount.getCurrency().equals("KRW") && compareResult >= 0) {
                OrderRequestDto orderReq = OrderRequestDto.builder()
                    .userId(user.getId())
                    .side(OrderSide.bid)
                    .price(bidPrice.toString())
                    .orderType(OrderType.price)
                    .build();
                orderService.create(user, orderReq);
            }
        } else if (webhook.getOrderId().equals(PositionType.LONG_SELL)) {

            int compareResult = askAccount.getBalance().compareTo(BigDecimal.ZERO);

            BigDecimal rate = BigDecimal.valueOf(Long.parseLong(webhook.getAskRate()));
            BigDecimal bidVolume = rate.multiply(askAccount.getBalance());

            if (compareResult > 0) {
                OrderRequestDto orderReq = OrderRequestDto.builder()
                    .userId(user.getId())
                    .side(OrderSide.ask)
                    .volume(bidVolume.toString())
                    .orderType(OrderType.market)
                    .build();

                orderService.create(user, orderReq);
            }
        }
    }
}

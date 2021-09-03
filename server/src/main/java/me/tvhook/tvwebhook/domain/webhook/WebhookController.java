package me.tvhook.tvwebhook.domain.webhook;

import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping
    public String signal() {

        log.info("test");

        return "test";

    }

    /**
     * { "time":{{timenow}}, "ticker":{{ticker}}, "exchange":{{exchange}}, "candleTime":{{time}},
     * "orderAction" :{{strategy.order.action}}, "message":{{strategy.order.alert_message}},
     * "orderId":{{strategy.order.id}}, "orderPrice":{{strategy.order.price}} }
     *
     * @param request
     * @param webhook
     */
    @PostMapping
    public void signal(HttpServletRequest request, @RequestBody String webhook) {
        // usedId -> get apiKey
        // market
         
        log.info(request.toString());
        log.info(webhook);

    }
}

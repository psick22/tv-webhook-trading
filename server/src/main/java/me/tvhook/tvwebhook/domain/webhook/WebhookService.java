package me.tvhook.tvwebhook.domain.webhook;


import static me.tvhook.tvwebhook.domain.webhook.WebhookDto.generateTemplate;

import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import me.tvhook.tvwebhook.domain.user.User;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WebhookService {

    public WebhookDto generateMessageTemplate(String username, WebhookType type,
        String strategyName,
        String bidAmount,
        String askRate) {

        WebhookDto template = generateTemplate(
            type,
            username,
            strategyName,
            bidAmount,
            askRate
        );

        log.info("message template : {} ", template);

        return template;

    }


}

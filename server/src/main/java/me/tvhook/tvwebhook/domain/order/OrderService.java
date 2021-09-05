package me.tvhook.tvwebhook.domain.order;

import java.security.NoSuchAlgorithmException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.tvhook.tvwebhook.api.Upbit;
import me.tvhook.tvwebhook.api.dto.UpbitOrderChanceResponseDto;
import me.tvhook.tvwebhook.api.dto.UpbitOrderResponseDto;
import me.tvhook.tvwebhook.domain.user.User;
import me.tvhook.tvwebhook.domain.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final Upbit upbit;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public void getChance(User user, String market) {

        UpbitOrderChanceResponseDto response = upbit.orderChance(user, market);

        log.info("response : {}", response.toString());

    }

    public Long create(User user, OrderRequestDto orderReq) throws NoSuchAlgorithmException {

        UpbitOrderResponseDto response = upbit.postOrder(user, orderReq);
        Order newOrder = modelMapper.map(response, Order.class);
        orderRepository.save(newOrder);

        log.info("order response : {}", newOrder);
        return newOrder.getId();
//        saveOrder(user, orderRes);
    }


}

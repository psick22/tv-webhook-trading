package me.tvhook.tvwebhook.domain.order;


import java.security.NoSuchAlgorithmException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.tvhook.tvwebhook.domain.user.User;
import me.tvhook.tvwebhook.domain.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/api/orders")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;

    private final UserService userService;

    @PostMapping
    public ResponseEntity<OrderDto> postOrder(@RequestBody OrderRequestDto orderRequestDto)
        throws NoSuchAlgorithmException {

        OrderDto createdOrder = orderService.create(orderRequestDto);

        return ResponseEntity.ok(createdOrder);
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrder(@RequestBody OrderSearchDto orderSearchDto)
        throws NoSuchAlgorithmException {
        List<OrderDto> orders = orderService.searchOrderLists(orderSearchDto);
        return ResponseEntity.ok(orders);
    }


}

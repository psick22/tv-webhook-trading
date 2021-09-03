package me.tvhook.tvwebhook.api;

import java.util.ArrayList;
import java.util.List;
import me.tvhook.tvwebhook.api.dto.UpbitOrderRequestDto;
import me.tvhook.tvwebhook.api.dto.UpbitOrderResponseDto;
import me.tvhook.tvwebhook.domain.user.User;
import me.tvhook.tvwebhook.domain.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UpbitTest {

    @Autowired
    Upbit upbit;

    @Autowired
    UserRepository userRepository;


    @Test
    @DisplayName("쿼리 스트링확인")
    public void test() {

        User user = userRepository.findById(9999L).get();

        UpbitOrderRequestDto requestDto = new UpbitOrderRequestDto();
        List<String> uuids = new ArrayList<>();
        uuids.add("uuid1");
        uuids.add("uuid2");
        uuids.add("uuid3");
        requestDto.setUuids(uuids);
        requestDto.setMarket("KRW-BTC");

        List<UpbitOrderResponseDto> result = upbit.getOrderList(user, requestDto);
        System.out.println("result = " + result);
    }
}
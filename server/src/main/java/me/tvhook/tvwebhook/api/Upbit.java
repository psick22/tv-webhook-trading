package me.tvhook.tvwebhook.api;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.tvhook.tvwebhook.api.dto.UpbitOrderChanceResponseDto;
import me.tvhook.tvwebhook.api.dto.UpbitOrderRequestDto;
import me.tvhook.tvwebhook.api.dto.UpbitOrderResponseDto;
import me.tvhook.tvwebhook.domain.order.OrderRequestDto;
import me.tvhook.tvwebhook.domain.user.User;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class Upbit {

    private final WebClient webClient;

    /**
     * 주문 하기
     *
     * @param user
     * @param orderReq
     * @return
     * @throws NoSuchAlgorithmException
     */
    public UpbitOrderResponseDto postOrder(User user, OrderRequestDto orderReq)
        throws NoSuchAlgorithmException {

        HashMap<String, String> params = new HashMap<>();

        params.put("market", orderReq.getMarket()); //     "KRW-BTC"
        params.put("side", String.valueOf(orderReq.getSide())); //   "bid" | "ask"
        if (orderReq.getVolume() != null) {
            System.out.println("orderReq = " + orderReq.getVolume());
            params.put("volume", orderReq.getVolume());
        }
        if (orderReq.getPrice() != null) {
            params.put("price", orderReq.getPrice());
        }
        params.put("ord_type", String.valueOf(orderReq.getOrderType()));

        log.info("params : {}", params);
        String queryString = makeQueryString(params);

        String authToken = makeAuthToken(queryString, user);
        log.info("token : {}", authToken);

        return webClient
            .mutate()
            .build()
            .post()
            .uri("/orders/")
            .header(HttpHeaders.AUTHORIZATION, authToken)
            .body(BodyInserters.fromValue(params))
            .retrieve()
            .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                clientResponse ->
                    clientResponse.bodyToMono(String.class)
                        .map(body -> new RuntimeException(body))
            )
            .bodyToMono(UpbitOrderResponseDto.class)
            .flux()
            .toStream()
            .collect(Collectors.toList())
            .get(0);

    }

    /**
     * 주문 가능 정보 조회
     *
     * @param user
     * @param market
     * @return
     */
    public UpbitOrderChanceResponseDto orderChance(User user, String market) {
        HashMap<String, String> params = new HashMap<>();
        params.put("market", market);
        String queryString = makeQueryString(params);
        String authToken = makeAuthToken(queryString, user);
        return webClient
            .mutate()
            .build()
            .get()
            .uri(uriBuilder -> uriBuilder.path("/orders/chance")
                .queryParam("market", market)
                .build())
            .header(HttpHeaders.AUTHORIZATION, authToken)
            .retrieve()
            .bodyToMono(UpbitOrderChanceResponseDto.class)
            .flux()
            .toStream()
            .collect(Collectors.toList())
            .get(0);
    }

    /**
     * 주문 취소
     *
     * @param user
     * @param orderUuid
     * @return
     */
    public UpbitOrderResponseDto cancelOrder(User user, String orderUuid) {
        HashMap<String, String> params = new HashMap<>();
        params.put("uuid", orderUuid);
        String queryString = makeQueryString(params);
        String authToken = makeAuthToken(queryString, user);

        return webClient.mutate().build().delete()
            .uri(uriBuilder -> uriBuilder.path("/orders").queryParam("uuid", orderUuid).build())
            .header(HttpHeaders.AUTHORIZATION, authToken)
            .retrieve()
            .bodyToMono(UpbitOrderResponseDto.class)
            .flux()
            .toStream()
            .collect(Collectors.toList())
            .get(0)
            ;

    }

    public UpbitOrderResponseDto getOrder(User user, String orderUuid) {
        HashMap<String, String> params = new HashMap<>();
        params.put("uuid", orderUuid);
        String queryString = makeQueryString(params);
        String authToken = makeAuthToken(queryString, user);

        return webClient.mutate().build().get()
            .uri(uriBuilder -> uriBuilder.path("/order")
                .queryParam("uuid", orderUuid).build())
            .header(HttpHeaders.AUTHORIZATION, authToken)
            .retrieve()
            .bodyToMono(UpbitOrderResponseDto.class)
            .flux()
            .toStream()
            .collect(Collectors.toList())
            .get(0);

    }

    /**
     * 주문 리스트 조회 state: - 미체결 주문 : wait, watch - 완료 주문 : done, cancel - 미체결 주문과 완료주문을 동시에 조회 할 수 없음
     *
     * @param user
     * @param request
     * @return
     */
    public List<UpbitOrderResponseDto> getOrderList(User user, UpbitOrderRequestDto request) {
        HashMap<String, String> params = new HashMap<>();
        if (request.getMarket() != null) {
            params.put("market", request.getMarket());
        }
        if (request.getState() != null) {
            params.put("state", request.getState().toString());
        }
        String queryString = makeQueryString(params, request.getUuids());
        String authToken = makeAuthToken(queryString, user);
        return webClient.mutate().build().get()
            .uri("/orders?" + queryString)
            .header(HttpHeaders.AUTHORIZATION, authToken)
            .retrieve()
            .bodyToFlux(UpbitOrderResponseDto.class)
            .toStream()
            .collect(Collectors.toList());
    }


    /**
     * Helper method
     */
    private String makeQueryString(HashMap<String, String> params) {
        ArrayList<String> queryElements = new ArrayList<>();

        for (Map.Entry<String, String> entity : params.entrySet()) {
            queryElements.add(entity.getKey() + "=" + entity.getValue());
        }

        return String.join("&", queryElements.toArray(new String[0]));

    }

    private String makeQueryString(HashMap<String, String> params, List<String> uuids) {
        ArrayList<String> queryElements = new ArrayList<>();

        for (Map.Entry<String, String> entity : params.entrySet()) {
            queryElements.add(entity.getKey() + "=" + entity.getValue());
        }

        for (String uuid : uuids) {
            queryElements.add("uuids[]=" + uuid);
        }

        return String.join("&", queryElements.toArray(new String[0]));

    }

    private String makeAuthToken(String queryString, User user) {
        MessageDigest md = getMessageDigest(queryString);
        String queryHash = String.format("%0128x", new BigInteger(1, md.digest()));
        Algorithm algorithm = Algorithm.HMAC256(user.getUpbitSecretKey());

        String jwtToken = JWT.create()
            .withClaim("access_key", user.getUpbitApiKey())
            .withClaim("nonce", UUID.randomUUID().toString())
            .withClaim("query_hash", queryHash)
            .withClaim("query_hash_alg", "SHA512")
            .sign(algorithm);

        return "Bearer " + jwtToken;
    }

    private MessageDigest getMessageDigest(String queryString) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(queryString.getBytes(StandardCharsets.UTF_8));
        return md;
    }
}

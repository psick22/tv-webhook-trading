package me.tvhook.tvwebhook.domain.order;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {


    Order findByUuid(String uuid);

}

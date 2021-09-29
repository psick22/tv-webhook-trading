package me.tvhook.tvwebhook.domain.order;

import static me.tvhook.tvwebhook.domain.order.QOrder.order;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.function.Supplier;
import javax.persistence.EntityManager;

public class OrderRepositoryImpl implements OrderRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public OrderRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<OrderDto> findAllByConditions(OrderSearchDto conditions) {

        return queryFactory.select(
            new QOrderDto(
                order.id,
                order.uuid,
                order.market,
                order.status,
                order.type,
                order.side
            )
        )
            .from(order)
            .where(orderSearchCondition(conditions))
            .orderBy(order.id.desc())
            .fetch();

    }




    public BooleanBuilder orderSearchCondition(OrderSearchDto condition){


        return userIdEq(condition.getUserId())
            .and(marketEq(condition.getMarket()))
            .and(openEq(condition.isOpen()))
            ;

    }

    private BooleanBuilder nullSafeBuilder(Supplier<BooleanExpression> f){
        try{
            return new BooleanBuilder(f.get());
        }
        catch(Exception e){
            return new BooleanBuilder();
        }
    }

    private BooleanBuilder userIdEq(Long userId){
        return nullSafeBuilder(()-> order.id.eq(userId));
    }
    private BooleanBuilder marketEq(String market){
        return nullSafeBuilder(()-> order.market.eq(market));
    }

    private BooleanBuilder openEq(boolean open){
        return nullSafeBuilder(()-> order.open.eq(open));
    }



}

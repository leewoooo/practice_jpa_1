package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        //앤티티 조회
        Member member = memberRepository.findOneById(memberId);
        Item item = itemRepository.findOneById(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        //원래는 OrderItem, delivery를 저장해 준 이후 order를 저장해야 하지만 Cascasde.ALl option을 통해 한번에 저장을 할 수 있다.
        //Cascade를 난발하면 안되며 persist의 life 사이클이 동일할 경우 Casecade를 사용하면 된다.
        orderRepository.save(order);
        return order.getId();
    }

    /**
     * 주문 취소
     * @param orderId
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        //주문 엔티티 조회
        Order order = orderRepository.findOneById(orderId);

        // 주문 취소
        // JPA의 진짜 강점은 여기서 나온다. Database의 data를 직접 다룰 때에는 data를 변경하려면 Update Query를 사용해야 한다.
        // 하지만 JPA는 Entity안에 있는 Data를 변경하면 변경내역감지가 일어나서 Database에 Update Query를 날린다.
        order.cancel();
    }

    /**
     * 검색
     */
//    public List<Order> findOrders(OrderSearch orderSearch){
//        return orderRepository.findAll(orderSearch);
//    }
}

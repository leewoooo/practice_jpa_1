package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    @DisplayName("상품 주문")
    void order() {
        //given
        Member member = createMember();

        em.persist(member);

        Book book = createBook("시골 JPA", 10000, 10);

        //when
        Long orderId = orderService.order(member.getId(), book.getId(), 5);

        //then
        Order savedOrder = orderRepository.findOneById(orderId);

        // 상품 주문 시 상태는 Order
        assertThat(savedOrder.getStatus()).isEqualTo(OrderStatus.ORDER);

        // 주문한 상품 종류 수가 정확해야 한다.
        assertThat(savedOrder.getOrderItems().size()).isEqualTo(1);

        // 주문한 상품 가격은 가격 * 수량이다.
        assertThat(savedOrder.getTotalPrice()).isEqualTo(50000);

        // 주문이 완료되면 재고에서 주문한 수 만큼 빠져야 한다.
        assertThat(book.getStockQuantity()).isEqualTo(5);
    }

    @Test
    @DisplayName("상품 취소")
    void cancelOrder() {
        //given
        Member member = createMember();
        em.persist(member);

        Book book = createBook("시골 JPA", 10000, 10);
        em.persist(book);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order canceledOrder = orderRepository.findOneById(orderId);

        assertThat(canceledOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(book.getStockQuantity()).isEqualTo(10);
    }


    @Test
    @DisplayName("상품주문_재고수량초과")
    void overItemStock() {
        //given
        Member member = createMember();
        em.persist(member);

        Book book = createBook("시골 JPA", 10000, 10);
        em.persist(book);

        //when
        //then
        assertThrows(NotEnoughStockException.class, () -> orderService.order(member.getId(), book.getId(), 12));
    }

    private Book createBook(String name, int price, int quantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(quantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("memberA");
        member.setAddress(new Address("서울", "강가", "12345"));
        return member;
    }
}
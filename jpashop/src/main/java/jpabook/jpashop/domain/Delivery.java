package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery",fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    // enum타입을 사용할 때는 @Enumerated 애노테이션을 이용한다.
    // type에는 EnumType.STRING, EnumType.ORDINAL가 있는데 ORDINAL는 DB에 순서가 저장되고 String은 enum이 스트링값으로 저장
    // ORDINAL 경우 중간에 enum이 추가되면 index에 맞춰 들어가기 떄문에 값이 꼬일 수 있다.
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; //READY, COMP
}

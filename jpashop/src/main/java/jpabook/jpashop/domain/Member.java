package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    // 내장 타입을 쓸 때 한 쪽에 @Embedded or @Embeddable만 있어도 되지만 명시적으로 하기 위함.
    @Embedded
    private Address address;

    // 관례상 list는 빈 list로 초기화 시켜준다.
    // nullPointException에 안전해진다.
    // Hibernate가 Entity를 영속화 할 때 Hibernate가 제공하는 내장 컬렉션으로 변경한다. (Hibernate가 컬렉션의 변경을 추적하기 위함)
    // 되도록 컬렉션을 한번 초기화 하고 변경하지 않는 것을 지향
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}

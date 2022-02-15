package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

// JPA 내장타입이라는 것을 명시해 주기 위한 애노테이션
// 값 타입은 불변으로 생성되는 것이 좋다. setter를 제공 X
@Embeddable
@Getter
public class Address {
    private String city;
    private String street;
    private String zipcode;

    // JPA 스팩에서는 기본생성자를 필수로 하며 public, protected까지 허용한다.
    // JPA 내에서 객체를 생성할 때 리플렉션 등과 같은 기술을 쓰기 위해 강제한다.
    protected Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}

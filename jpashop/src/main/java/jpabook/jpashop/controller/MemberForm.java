package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberForm {

    // name을 필수로 받기 위해 설정한다. javax validate를 통해서 스프링이 validate를 해준다. 비어있으면 스프링이 오류를 발생시킨다.
    // message는 만약 empty 값이 들어왔을 때 error 메세지 이다.
    @NotEmpty(message = "회원 이름은 필수 입니다.")
    private String name;

    private String city;
    private String street;
    private String zipcode;
}

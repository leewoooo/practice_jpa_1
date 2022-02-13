package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("회원가입")
    void join(){
        //given
        Member member = new Member();
        member.setName("userA");

        //when
        //Test에서는 @Transaction은 commit하지 않고 rollback을 하기 때문에 insert문을 log에서 찾아 볼 수 없음.
        //확인하려면 EntityManager를 주입받아 저장 후 flush 호출
        Long insertedId = memberService.join(member);

        //then
        Member savedMember = memberRepository.findOneById(insertedId);
        Assertions.assertThat(savedMember).isEqualTo(member);
    }

    @Test
    @DisplayName("중복 회원 조회")
    void duplicateMember(){
        //given
        Member member = new Member();
        member.setName("userA");

        //when
        //then
        memberService.join(member);
        assertThrows(IllegalStateException.class, () -> memberService.join(member));
    }
}
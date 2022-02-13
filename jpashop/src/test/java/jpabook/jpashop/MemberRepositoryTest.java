package jpabook.jpashop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@Transactional // testCase에 적용이 되어있으면 테스트 케이스가 하나 끝날 때 마다 rollback 한다. insert와 같은 데이터 조작 query를 확인할 수 없음.
//@Rollback(value = false) // @Rollback을 이용하여 테스트 케이스가 끝날 때 마다 rollback에 대한 option을 처리할 수 있다. 또한 이걸 부여하면 testcode에서 insert 쿼리를 볼 수 있음
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("맴버 저장 테스트")
    void save(){
        //given
        Member member = new Member("memberA");

        //when
        Long lastInsertedId = memberRepository.save(member);
        Member selectedMember = memberRepository.find(lastInsertedId);

        //then
        assertThat(selectedMember.getId()).isEqualTo(lastInsertedId);
        assertThat(selectedMember.getUsername()).isEqualTo(member.getUsername());

        // 같은 트랜잭션 안에서 저장을 하고 조회를 하면 영속성 컨택스트 안에서 저장하고 조회하기 떄문에 (식별자가 같아서)
        // 기존에 관리하던 것을 1차 캐시에서 뽑아온것이다. 조회 query 조차 나가지 않는다.
        assertThat(selectedMember).isEqualTo(member); // output -> true
    }
}
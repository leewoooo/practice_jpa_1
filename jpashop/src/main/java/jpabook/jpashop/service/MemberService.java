package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
// class Level에서 @Transactional을 걸면 각각 method마다 트랜잭션 안에서 처리된다.
// readOnly = true는 JPA가 조회하는 곳에서는 조금 더 성능 최적회를 한다.
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     * @param member
     * @return memberId
     */
    @Transactional // data가 변경인 곳에서는 readOnly = true를 주면 안된다.
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member); // persist할 때 key value형식으로 id값도 부여된다.
        return member.getId();
    }

    /**
     * 중복 validate
     * @param member
     */
    private void validateDuplicateMember(Member member) {
        // EXCEPTION
        // 만약 동시에 현재 method를 실행 할 경우가 있다. 그렇기 때문에 DB에 name에 유니크 제약조건을 부여하여 최후의 방어를 하는 것을 추천한다.
        List<Member> members = memberRepository.findAllByName(member.getName());
        if (!members.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * member 전체 조회
     * @return
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    /**
     * id 값으로 member 조회
     * @param id
     * @return
     */
    public Member findOneById(Long id){
        return memberRepository.findOneById(id);
    }
}

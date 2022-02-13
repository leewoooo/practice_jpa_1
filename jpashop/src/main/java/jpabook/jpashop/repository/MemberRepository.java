package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

// 컴포넌트 스캔의 대상이 되며 내부에 @Component를 사용하고 있다.
// JPA Exception을 Spring에서 사용할 수 있는 Exception으로 변환
@RequiredArgsConstructor
@Repository
public class MemberRepository {

    private final EntityManager em;

//    // spring이 EntityManager를 만들어서 DI해준다.
//    @PersistenceContext
//    private EntityManager em;

    // spring Data JPA + spring boot를 사용하면 아래와 같이 생성자 주입을 받을 수 있다. 원래는 @PersistenceContext를 이용해야 한다.
//    @Autowired
//    public MemberRepository(EntityManager em) {
//        this.em = em;
//    }

    public void save(Member member) {
        // persist -> 영속성 컨텍스트에 Member Entity를 넣고 Commit 시점에 insert query 실행
        // @GenerateValue 전략에서는 persist 한다고 DB에 저장이 되는 것이 아님. commit을 하면 flush하면서 Insert문이 나감.
        em.persist(member);
    }

    public Member findOneById(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        // 전체 조회할 때는 JPQL을 작성해야 조회해야한다.
        // SQL은 테이블을 대상으로 query를 날리는 것.
        // JPQL은 Entity 객체를 대상으로 query를 한다고 생각하면 된다.
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findAllByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
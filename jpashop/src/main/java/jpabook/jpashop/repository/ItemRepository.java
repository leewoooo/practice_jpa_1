package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class ItemRepository {

    private final EntityManager em;

    // upsert로직
    public void save(Item item){
        // 저장하기 전까지는 Id값이 부여되지 않는다.
        if(item.getId() == null) {
            em.persist(item);
        }else{
            // Id값이 null이 아니면 한번 DB에 저장된 것이기 때문에 merge를 이용하여 update하는 로직
            em.merge(item);
        }
    }

    public Item findOneById(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}

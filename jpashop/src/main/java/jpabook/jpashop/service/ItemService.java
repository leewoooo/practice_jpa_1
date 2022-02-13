package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    //== 아래와 같이 service 단에서 repository로 위임을 하는 정도의 로직이면 controller에서 repository에 접근을 고려해 볼 필요가 있다.
    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findItem(Long id){
        return itemRepository.findOneById(id);
    }
}

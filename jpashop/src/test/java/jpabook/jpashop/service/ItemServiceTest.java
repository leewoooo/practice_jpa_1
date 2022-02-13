package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @Test
    @DisplayName("아이템 저장")
    void saveItem(){
        //given
        Book book = new Book();
        book.setAuthor("leewoooo");
        book.setIsbn("foobar");

        //when
        itemService.saveItem(book);

        //then
        Item selectedBook = itemRepository.findOneById(book.getId());
        assertThat(selectedBook).isEqualTo(book);
    }

    @Test
    @DisplayName("아이템 업데이트")
    void updateItems(){
        //given
        Book book = new Book();
        book.setAuthor("leewoooo");
        book.setIsbn("foobar");

        //when
        itemService.saveItem(book);
        book.setAuthor("change");
        book.setIsbn("change");

        Item selectedBook = itemRepository.findOneById(book.getId());
        itemService.saveItem(selectedBook);

        //then
        Item updatedItem = itemRepository.findOneById(selectedBook.getId());
        Book updatedBook = (Book) updatedItem;

        assertThat(updatedBook.getAuthor()).isEqualTo("change");
        assertThat(updatedBook.getIsbn()).isEqualTo("change");
    }
}
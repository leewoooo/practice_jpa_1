package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //싱글테이블 전략을 쓸것이고 다른 옵션들도 있다.
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //==비지니스 로직==//
    // 도메인 주도 개발을 할 때 Entity자체가 해결할 수 있는 부분들은 Entity안에서 해결하는 것이 응집도가 높아진다.

    //entity의 변경이 필요하면 setter를 사용하는 것이 아닌 아래와 같은 비지니스 로직을 entity안에서 작성하여 사용하는 것이 좋다.
    /**
     * stock 증가
     * @param quantity
     */
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    /**
     * stock 감소
     * @param quantity
     */
    public void removeStock(int quantity){
        int resultStock = this.stockQuantity - quantity;
        if (resultStock < 0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = resultStock;
    }
}



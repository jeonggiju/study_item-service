package hello.item_service.domain.item;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//@Data <- 안쓰는걸 권장, 왜? 위험함. 나중에 뜯어서 볼 것.
@Getter @Setter
public class Item {
    private Long id;
    private String itemName;
    private Integer price; // price가 안들어갈 수 있다는 가정, null이 들어갈 수 있도록함
    private Integer quantity;

    public Item(){}

    public Item( String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}

package hello.item_service.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository // Repository 안에 Component가 있어 component scan의 대상이 된다.
public class ItemRepository {

    /**
     * 실제 개발에서는 HashMap 쓰면 안된다.
     * 왜? 동시성 이슈때문이다. 싱글톤으로 생성되었는데 여러 스레드가 동시에 접근하면 에러가 발생한다.
     * 이를 고려해서 Concurrent HashMap을 써야한다.
     */
    private static final Map<Long, Item> store = new HashMap<>(); // static 씀.
    private static long sequence = 0L; // static

    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() {
        // 불변성(immutability)을 보장하거나, 반환된 리스트가 외부에서 수정되지 않도록 하기 위함
        return new ArrayList<>(store.values());
    }

    public void update(Long itemId, Item updataParam){
        Item findItem = findById(itemId);
        findItem.setItemName(updataParam.getItemName());
        findItem.setPrice(updataParam.getPrice());
        findItem.setQuantity(updataParam.getQuantity());
    }

    public void clearStore(){
        store.clear();
    }
}

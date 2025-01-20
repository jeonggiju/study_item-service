package hello.item_service.web.basic;

import hello.item_service.domain.item.Item;
import hello.item_service.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    /**
     * @Repository annotation이 붙은 ItemRepository가 주입된다.
     * 왜? BasicItemController와 ItemRepository 둘 다 spring bean으로 등록되기 때문이다.
     * - @Autowired가 하나만 있다면 생략이 가능하다.
     * - @RequiredArgsConstructor가 있다면 생성자 생략이 가능하다.
     */
    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable("itemId") Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }

//    @PostMapping("/add")
    public String addItemV1(
            @RequestParam("itemName") String itemName,
            @RequestParam("price") int price,
            @RequestParam("quantity") int quantity,
            Model model
    ){
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);
        model.addAttribute("item", item);

        return "basic/item";
    }

    @PostMapping("/add")
    public String addItemV2(
            @ModelAttribute("item") Item item, Model model
    ){
        itemRepository.save(item);
//        model.addAttribute("item", item); <- ModelAttribute는 ("xxx")를 addAttribute에 넣어준다.

        return "basic/item";
    }

    /**
     * 테스트용 데이터
     * @PostConstruct는 DI에 의해 초기화된 후 실행되어야 하는 메서드를 정의할 때 사용된다.
     * 즉, 클래스의 의존성이 주입된 후, 즉 객체가 완전히 초기화된 직후에 실행된다.
     * 보통 리소스 초기화나, 객체가 준비되었을 때 수행해야 하는 추가 설정을 위해 사용된다.
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 20));
        itemRepository.save(new Item("itemB", 20000, 10));
    }
}

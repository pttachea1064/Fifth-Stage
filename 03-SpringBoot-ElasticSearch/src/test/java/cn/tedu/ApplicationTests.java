package cn.tedu;

import cn.tedu.pojo.Item;
import cn.tedu.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class ApplicationTests {

    @Autowired
    private ItemRepository itemRepository;

    /**
     * 新增一條文檔到ES索引當中
     */

    @Test
    public void saveOne(){
        Item item = new Item().setId(1L)
                .setTitle("波賽頓")
                .setBrand("希臘神")
                .setCategory("水族")
                .setPrice(99.9)
                .setImgPath("1.png");
        //將item 物件添加到ES當中 save函數就是ES當中提供的方法
        itemRepository.save(item);
        System.out.println("添加完成");
    }

    //單一查詢
    @Test
    public void findById(){
        //Optional 類似於一個包裝類的概念 將查詢的結果封裝到了該內部
        Optional<Item> optionalItem = itemRepository.findById(1L);
        System.out.println(optionalItem.get());

    }

    @Test
    public void saveMore(){
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item(2L, "雷蛇无线激光鼠标", "雷蛇", "鼠标", 299.00, "2.jpg"));
        items.add(new Item(3L, "罗技双模鼠标", "罗技", "鼠标", 299.00, "3.jpg"));
        items.add(new Item(4L, "罗技双模键盘", "罗技", "键盘", 99.99, "4.jpg"));
        items.add(new Item(5L, "联想超薄笔记本电脑", "联想", "电脑", 4399.00, "5.jpg"));
        items.add(new Item(6L, "雷蛇有线鼠标", "雷蛇", "鼠标", 399.00, "6.jpg"));
        items.add(new Item(7L, "燕双飞超级无敌螺旋鼠标", "燕双飞", "鼠标", 999.00, "7.jpg"));
        //將item 物件添加到ES當中 save函數就是ES當中提供的方法
        itemRepository.saveAll(items);
        System.out.println("添加完成");
    }

    @Test
    public void findAll(){
        Iterable<Item> all = itemRepository.findAll();
        for (Item item : all){
            System.out.println(item);
        }
    }

    //單一條件查詢
    @Test
    public void query1 (){
        List<Item> items =itemRepository.queryItemsByTitleMatches("赫");
        for (Item item :
                items) {
            System.out.println(item);
        }
    }

    //多条件查询
    @Test
    public void queryOr() {
        List<Item> items = itemRepository.queryItemsByTitleMatchesOrBrandMatches("托爾菲", "歐洲");
        items.forEach(item -> System.out.println(item));
    }

    //多条件查询
    @Test
    public void queryAnd() {
        List<Item> items = itemRepository.queryItemsByTitleMatchesAndBrandMatches("索爾", "歐洲神");
        items.forEach(item -> System.out.println(item));
    }

    //排序查询
    @Test
    public void orderByPrice() {
        List<Item> items = itemRepository.queryItemsByTitleMatchesOrBrandMatchesOrderByPriceDesc("鼠标", "罗技");
        items.forEach(item -> System.out.println(item));
    }

    //分頁查詢
    @Test
    public void pageQuery() {
        int pageNum = 1; //页码，SpringDataES的页码是从0开始
        int pageSize = 3; //每页大小

        PageRequest pr =PageRequest.of(pageNum-1,pageSize);
        Page<Item> items = itemRepository.queryItemsByTitleMatchesOrBrandMatchesOrderByPriceDesc
                ("鼠标", "罗技", pr);
        for (Item item:items) {
            System.out.println(item);
        }
        //page中除了查询结果数据以外还有分页信息
        System.out.println("总页数:" + items.getTotalPages());
        System.out.println("总条数:" + items.getTotalElements());
        System.out.println("当前页:" + items.getNumber());
        System.out.println("每页条数:" + items.getSize());
        System.out.println("是否为首页:" + items.isFirst());
        System.out.println("是否为末页:" + items.isLast());
    }



}

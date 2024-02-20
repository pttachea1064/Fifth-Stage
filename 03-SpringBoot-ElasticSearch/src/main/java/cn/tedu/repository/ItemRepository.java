package cn.tedu.repository;

import cn.tedu.pojo.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ES中對於常用的增刪改查方法進行了封裝
 * 所以我們可以直接調用預先提供的方法來使用
 *
 * 但是我們需要做以下操作
 * 1.讓街口繼承ElasticsearchRepository類
 * 2.並且在ElasticsearchRepository類上添加泛用類型,要求寫兩個
 * 分別是
 * <[實體類類型],[實體類主鍵的類型]>
 */

@Repository
public interface ItemRepository extends ElasticsearchRepository <Item,Long> {

    //自訂方法 只想搜尋title中有包含...啥的內容之結果
    /* 要先思考 返回數值 方法名稱 (方法的參數)*/
    /**
     * 由於返回的內容可以是單一的也可以是多數地所以我們用List來做返回
     * 而返回的內容參數 String title
     * 然 自訂的方法名稱是重要的ES是基於方法名稱生成查詢
     *  使用query: 表示當前的方法為查詢的方法
     *  ItemsBy表示查詢的實體類是Item,但如果返回的內容會是多條則我們用s
     *  By表示要開始設置的條件
     *  Title表示條件字段為Title
     *  Matches表示執行查詢的分詞字串要匹配符合
     *
     */
    //單一查詢
    List<Item> queryItemsByTitleMatches (String title);

    //多條件查詢 或者
    List<Item> queryItemsByTitleMatchesOrBrandMatches(String title, String brand);
    //多條鍵查詢 且
    List<Item> queryItemsByTitleMatchesAndBrandMatches(String title, String brand);

    //排序查詢
    List<Item> queryItemsByTitleMatchesOrBrandMatchesOrderByPriceDesc (String title, String brand);

    //分页查询

    /**
     * 如果要進行分頁查詢 方法名要照普通查詢定義
     * 返回數值則要用Page類型定義
     * 並且方法的形式參數需要設置Pageable用於接受分頁
     * 了解就可以了 通常使用PageHelper
     */
    Page<Item> queryItemsByTitleMatchesOrBrandMatchesOrderByPriceDesc(String title, String brand, Pageable pageable);


}

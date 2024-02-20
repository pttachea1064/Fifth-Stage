package cn.tedu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain =true) //開啟鍊式set功能
@Document(indexName = "items")//設置索引名稱為items
public class Item implements Serializable {


    @Id //標記為ES中的主鍵
    private Long id;

    @Field(type = FieldType.Text,
            analyzer = "ik_max_word",
            searchAnalyzer = "ik_max_word")//標記當前字段為ES中的字段 並且為文本類型且分詞與搜尋都是ik詳細分詞類型
    private String title;

    @Field(type = FieldType.Keyword)//不需要分詞
    private String brand;
    @Field(type = FieldType.Keyword)//不需要分詞
    private String category;
    @Field(type = FieldType.Double)//不需要分詞
    private Double price;
    @Field(type = FieldType.Keyword,index = false)//不需要分詞 也不需要創建索引
    private String imgPath;


}

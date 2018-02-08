package top.kylewang.taotao.search;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Kyle.Wang
 * 2018/2/8 0008 19:21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SolrTest {

    @Autowired
    private SolrClient solrClient;

    @Test
    public void addDocument() throws Exception {
        // 创建一个文档对象SolrInputDocument对象。
        SolrInputDocument document = new SolrInputDocument();
        // 向文档中添加域。必须有id域，域的名称必须在schema.xml中定义。
        document.addField("id", "test001");
        document.addField("item_title", "测试商品");
        document.addField("item_price", "199");
        // 把文档添加到索引库中。
        solrClient.add(document);
        //提交。
        solrClient.commit();
    }

}

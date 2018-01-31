package top.kylewang.taotao.solrj;

import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class TestSolrCloud {

	@Test
	public void testSolrCloud() throws Exception {
		//第一步：创建一个SolrServer对象。CloudSolrServer对象。
		CloudSolrServer solrServer = new CloudSolrServer("192.168.25.154:2181,192.168.25.154:2182,192.168.25.154:2183");
		//构造方法有一个参数，zkHost，字符串类型zookeeper的地址列表。
		//第二步：需要指定默认collection。
		solrServer.setDefaultCollection("collection2");
		//第三步：向索引库中添加文档。和单机版一致。
		SolrInputDocument document = new SolrInputDocument();
		document.setField("id", "1");
		document.setField("item_title", "测试商品");
		document.setField("item_price", "199");
		solrServer.add(document);
		//提交
		solrServer.commit();
	}
	//删除文档
	@Test
	public void testDeleteDocument() throws Exception {
		//第一步：创建一个SolrServer对象。CloudSolrServer对象。
		CloudSolrServer solrServer = new CloudSolrServer("192.168.25.154:2181,192.168.25.154:2182,192.168.25.154:2183");
		//构造方法有一个参数，zkHost，字符串类型zookeeper的地址列表。
		//第二步：需要指定默认collection。
		solrServer.setDefaultCollection("collection2");
		solrServer.deleteByQuery("*:*");
		solrServer.commit();
	}
}

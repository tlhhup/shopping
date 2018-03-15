package shopping;

import java.io.IOException;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tlh.shopping.config.App;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=App.class)
public class ESTest {
	
	@Autowired
	private RestHighLevelClient client;

	@Test
	public void index() throws IOException{
		System.out.println(client.info().getNodeName());
	}
	
	@Test
	public void get() throws IOException{
		GetRequest request=new GetRequest("shopping", "products", "2");
		GetResponse response = this.client.get(request);
		System.out.println(response);
	}
	
	@Test
	public void query() throws Exception{
		SearchRequest request=new SearchRequest();
		//设置索引
		request.indices("shopping");
		//设置类型
		request.types("products");
		//构建查询条件
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder(); 
		searchSourceBuilder.query(QueryBuilders.matchAllQuery()); 
		request.source(searchSourceBuilder);
		
		SearchResponse response = this.client.search(request);
		
		System.out.println(response.getHits().getHits()[0].getSourceAsMap());
	}
	
}

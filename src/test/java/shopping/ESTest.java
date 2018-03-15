package shopping;

import java.io.IOException;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tlh.shopping.config.App;
import org.tlh.shopping.entity.Product;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = App.class)
public class ESTest {

	@Autowired
	private RestHighLevelClient client;

	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void index() throws IOException {
		System.out.println(client.info().getNodeName());
	}

	@Test
	public void get() throws IOException {
		GetRequest request = new GetRequest("shopping", "products", "2");
		GetResponse response = this.client.get(request);
		System.out.println(response);
	}

	@Test
	public void query() throws Exception {
		SearchRequest request = new SearchRequest();
		// 设置索引
		request.indices("shopping");
		// 设置类型
		request.types("products");
		// 构建查询条件
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchAllQuery());
		request.source(searchSourceBuilder);

		SearchResponse response = this.client.search(request);

		System.out.println(response.getHits().getHits()[0].getSourceAsMap());
	}

	@Test
	public void queryByName() throws IOException {
		SearchRequest request = new SearchRequest();
		// 设置索引
		request.indices("shopping");
		// 设置类型
		request.types("products");
		// 构建查询条件
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchQuery("name", "牙膏"));
		
		// 设置排序
		searchSourceBuilder.sort("price", SortOrder.DESC);
		
		// 设置高亮
		HighlightBuilder highlightBuilder = new HighlightBuilder();
		//设置高亮的属性
		HighlightBuilder.Field highlightTitle = new HighlightBuilder.Field("name");
		//设置高亮样式
		//highlightTitle.highlighterType("unified");
		//绑定属性
		highlightBuilder.field(highlightTitle);
		highlightBuilder.preTags("<a style=\"color: #e4393c\">");
		highlightBuilder.postTags("</a>");
		//添加高亮的样式--->该和前面两个是互斥的
		//highlightBuilder.tagsSchema("styled");
		//构建到source中
		searchSourceBuilder.highlighter(highlightBuilder);
		
		//设置source的返回列
		String[] includeFields = new String[] {"id","description","price","name"};
		String[] excludeFields = new String[] {"@timestamp" ,"@version"};
		searchSourceBuilder.fetchSource(includeFields, excludeFields);
		
		// 设置查询条件
		request.source(searchSourceBuilder);

		// 执行查询
		SearchResponse searchResponse = this.client.search(request);
		SearchHits hits = searchResponse.getHits();
		for (SearchHit hit : hits.getHits()) {
/*			System.out.println(hit.getSourceAsString());//获取元数据
			System.out.println(this.objectMapper.readValue(hit.getSourceAsString(), Product.class));
			
		    Map<String, HighlightField> highlightFields = hit.getHighlightFields();
		    HighlightField highlight = highlightFields.get("name"); 
		    Text[] fragments = highlight.fragments();  
		    String fragmentString = fragments[0].string();
		    
		    System.out.println(fragmentString);*/
			Product product = this.objectMapper.readValue(hit.getSourceAsString(), Product.class);
			String name = hit.getHighlightFields().get("name").getFragments()[0].toString();
			product.setName(name);
			
			System.out.println(product);
		}
	}

}

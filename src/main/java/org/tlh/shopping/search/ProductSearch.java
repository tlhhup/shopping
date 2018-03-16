package org.tlh.shopping.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tlh.shopping.entity.Product;
import org.tlh.shopping.util.PageInfo;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ProductSearch {
	
	@Autowired
	private RestHighLevelClient restHighLevelClient;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	public List<Product> searchByName(String name) throws IOException{
		SearchRequest request = new SearchRequest();
		// 设置索引
		request.indices("shopping");
		// 设置类型
		request.types("products");
		// 构建查询条件
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchQuery("name", name));
		
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
		//构建到source中
		searchSourceBuilder.highlighter(highlightBuilder);
		
		//设置source的返回列
		String[] includeFields = new String[] {"id","description","price","name"};
		String[] excludeFields = new String[] {"@timestamp" ,"@version"};
		searchSourceBuilder.fetchSource(includeFields, excludeFields);
		
		//设置分页参数
//		searchSourceBuilder.from(from);
//		searchSourceBuilder.size(size);
		
		// 设置查询条件
		request.source(searchSourceBuilder);

		// 执行查询
		SearchResponse searchResponse = this.restHighLevelClient.search(request);
		SearchHits hits = searchResponse.getHits();

		List<Product> products=new ArrayList<Product>();
		Product product=null;
		for (SearchHit hit : hits.getHits()) {
			product = this.objectMapper.readValue(hit.getSourceAsString(), Product.class);
			String productName = hit.getHighlightFields().get("name").getFragments()[0].toString();
			product.setName(productName);
		
			products.add(product);
			product=null;
		}
		return products;
	}
	
	public PageInfo<Product> searchByNameWithPage(String name,int number,int size) throws IOException{
		PageInfo<Product> pageInfo=new PageInfo<Product>();
		pageInfo.setNumber(number);
		pageInfo.setSize(size);
		
		SearchRequest request = new SearchRequest();
		// 设置索引
		request.indices("shopping");
		// 设置类型
		request.types("products");
		// 构建查询条件
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchQuery("name", name));
		
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
		//构建到source中
		searchSourceBuilder.highlighter(highlightBuilder);
		
		//设置source的返回列
		String[] includeFields = new String[] {"id","description","price","name"};
		String[] excludeFields = new String[] {"@timestamp" ,"@version"};
		searchSourceBuilder.fetchSource(includeFields, excludeFields);
		
		//设置分页参数
		searchSourceBuilder.from(pageInfo.getFrom());
		searchSourceBuilder.size(size);
		
		// 设置查询条件
		request.source(searchSourceBuilder);
		
		// 执行查询
		SearchResponse searchResponse = this.restHighLevelClient.search(request);
		SearchHits hits = searchResponse.getHits();
		
		//设置总数
		pageInfo.setTotal(new Long(hits.getTotalHits()).intValue());
		
		List<Product> products=new ArrayList<Product>();
		Product product=null;
		for (SearchHit hit : hits.getHits()) {
			product = this.objectMapper.readValue(hit.getSourceAsString(), Product.class);
			String productName = hit.getHighlightFields().get("name").getFragments()[0].toString();
			product.setName(productName);
			
			products.add(product);
			product=null;
		}
		
		//设置数据
		pageInfo.setContent(products);
		
		return pageInfo;
	}

}

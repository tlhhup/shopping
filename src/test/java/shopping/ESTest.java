package shopping;

import java.io.IOException;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RestHighLevelClient;
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
	
}

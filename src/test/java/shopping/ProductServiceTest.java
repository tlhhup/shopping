package shopping;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tlh.shopping.config.App;
import org.tlh.shopping.entity.Product;
import org.tlh.shopping.service.ProductService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=App.class)
public class ProductServiceTest {
	
	@Resource
	private ProductService productService;

	@Test
	public void save(){
		Product product=new Product();
		product.setName("云南白药牙膏");
		product.setDescription("这个牙膏有点贵啊");
		product.setPrice(45f);
		
		this.productService.insert(product);
	}
	
	@Test
	public void delete(){
		this.productService.delete(1);
	}
	
}

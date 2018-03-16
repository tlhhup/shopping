package org.tlh.shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.tlh.shopping.entity.Product;
import org.tlh.shopping.search.ProductSearch;
import org.tlh.shopping.service.ProductService;
import org.tlh.shopping.util.PageInfo;

@Controller
public class ProductController {
	
	@Autowired
	private ProductService productService;

	@RequestMapping(value="/",method=RequestMethod.GET)
	public String index(Model model,@RequestParam(name="number",defaultValue="0") int number,@RequestParam(name="size",defaultValue="2")int size){
		PageInfo<Product> pageInfo = this.productService.findAllWithPage(number,size);
		model.addAttribute("pageInfo", pageInfo);
		return "index";
	}
	
	@Autowired
	private ProductSearch productSearch;
	
	@RequestMapping(value="/search",method={RequestMethod.POST,RequestMethod.GET})
	public String search(String name,Model model,@RequestParam(name="number",defaultValue="0") int number,@RequestParam(name="size",defaultValue="2")int size) throws Exception{
		PageInfo<Product> pageInfo = this.productSearch.searchByNameWithPage(name, number, size);
		model.addAttribute("pageInfo", pageInfo);
		//缓存查询条件
		model.addAttribute("productName", name);
		return "index";
	}
	
}

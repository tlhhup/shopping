package org.tlh.shopping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/SuggestionController")
public class SuggestionController {
	
	@RequestMapping(value="index",method=RequestMethod.GET)
	public String index(){
		return "suggestion";
	}

}

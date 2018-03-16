package org.tlh.shopping.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.tlh.shopping.config.App;
import org.tlh.shopping.config.WebMvcConfig;

public class WebInitListener implements WebApplicationInitializer {

	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext act=new AnnotationConfigWebApplicationContext();
		act.register(App.class,WebMvcConfig.class);
		
		act.setServletContext(servletContext);
		
		//设置编码过滤器
		javax.servlet.FilterRegistration.Dynamic filter = servletContext.addFilter("CharacterEncodingFilter", new CharacterEncodingFilter("UTF-8"));
		filter.addMappingForUrlPatterns(null, true, "*");
		
		//配置springmvc的控制器
 		Dynamic dynamic = servletContext.addServlet("springmvc", new DispatcherServlet(act));
 		//设置后缀
 		dynamic.addMapping("/");
 		dynamic.setLoadOnStartup(1);
	}

}

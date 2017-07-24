package hello.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
   //     registry.addViewController("/form").setViewName("form");
  //      registry.addViewController("/").setViewName("form");
   //     registry.addViewController("/login").setViewName("login");
    }
}
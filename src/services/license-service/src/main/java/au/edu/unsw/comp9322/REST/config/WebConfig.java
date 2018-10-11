package au.edu.unsw.comp9322.REST.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import au.edu.unsw.comp9322.REST.TokenAuthorization;

@EnableWebMvc
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	@Bean
	TokenAuthorization getSessionManager() {
		return new TokenAuthorization();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(getSessionManager()).addPathPatterns("/**");
		// .excludePathPatterns("/renewals/**");

	}

}
package luna.service.interceptor;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


//@Configuration
public class ConfigAdapter extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截所有请求url
        registry.addInterceptor(new MockInterceptor()).addPathPatterns("/**");
    }
}

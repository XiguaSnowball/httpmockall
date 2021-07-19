package luna.service.util;

import org.springframework.context.annotation.Bean;

import javax.servlet.Filter;

//@Configuration
public class BeanConfig {

    @Bean
    public Filter FilterBean() {
        MyFilter filter = new MyFilter();

        return filter;
    }

}

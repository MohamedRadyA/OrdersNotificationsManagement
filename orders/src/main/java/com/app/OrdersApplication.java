package com.app;

<<<<<<< Updated upstream
import com.app.auth.AuthenticationFilter;
=======
import com.app.model.Order;
import com.app.model.OrderComponent;
import com.app.model.OrderState;
import com.app.model.ProductItem;
>>>>>>> Stashed changes
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.util.logging.Filter;

import java.time.LocalDateTime;

@SpringBootApplication
public class OrdersApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrdersApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean<AuthenticationFilter> filterRegistrationBean() {
        FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        registrationBean.setFilter(authenticationFilter);
//        registrationBean.addUrlPatterns("/api/*");
        return registrationBean;
    }
}

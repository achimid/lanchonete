package br.com.ecommerce.bff.adm.ecommerceadm;

import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Lourran on 15/07/2017.
 */
@Configuration
public class ApplicationConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/home");
    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return (container ->
                container.addErrorPages(
                        new ErrorPage(HttpStatus.NOT_FOUND, "/404"),
                        new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500")));
    }
}

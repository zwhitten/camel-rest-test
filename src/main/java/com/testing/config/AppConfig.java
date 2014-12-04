package com.testing.config;


import com.testing.views.CustomDataFormat;
import com.testing.model.DaoService;
import org.apache.camel.CamelContext;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;

@Configuration
public class AppConfig implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext appContext) throws BeansException {
        this.applicationContext = appContext;
    }

    public List<RouteBuilder> routes() {
        if (this.applicationContext != null) {
            Map<String, RouteBuilder> routeBuildersMap = applicationContext
                    .getBeansOfType(RouteBuilder.class);
            List<RouteBuilder> routeBuilders = new ArrayList<RouteBuilder>(
                    routeBuildersMap.size());
            for (RouteBuilder routeBuilder : routeBuildersMap.values()) {
                routeBuilders.add(routeBuilder);
            }
            return routeBuilders;
        } else {
            return emptyList();
        }
    }

    @Bean
    public CamelContext camelContext() throws Exception {
        CamelContext camelContext = new SpringCamelContext(applicationContext);
        List<RouteBuilder> routes = routes();
        for (RoutesBuilder route : routes)
            camelContext.addRoutes(route);

        return camelContext;
    }

    @Bean(name = "daoService")
    public DaoService daoService() {
        return new DaoService();
    }

    @Bean(name="customDataFormat")
    public CustomDataFormat customDataFormat(){
        return new CustomDataFormat();
    }

}


package com.testing.routes;

import com.testing.views.CustomeJsonViews;
import com.testing.config.LocalEnvironmentBean;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyRouteBuilder extends RouteBuilder{
    @Autowired
    LocalEnvironmentBean environmentBean;

    @Override
    public void configure() throws Exception {
        restConfiguration().component("jetty").host("0.0.0.0").port(80)
            .bindingMode(RestBindingMode.auto);

        rest("/testApp")
            .get("/data").route()
                .to("bean:daoService?method=getData")
                .setProperty("viewClass", constant(CustomeJsonViews.class))
                .marshal("customDataFormat").endRest()
            .get("/allData").route()
                .to("bean:daoService?method=getDatas")
                .setProperty("viewClass", constant(CustomeJsonViews.class))
                .marshal("customDataFormat").endRest();
    }
}

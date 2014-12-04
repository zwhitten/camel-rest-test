package com.testing;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.main.MainSupport;
import org.apache.camel.spring.SpringCamelContext;
import org.apache.camel.util.IOHelper;
import org.apache.camel.view.ModelFileGenerator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class JavaSpringMain extends MainSupport {

    protected AbstractApplicationContext applicationContext;
    protected String envName = "local";
    protected String port = "80";
    private String configClassesPkg="com.testing";

    public JavaSpringMain(){
        super();
    }

    public static void main(String[] args) throws Exception{
        JavaSpringMain main = new JavaSpringMain();
        main.enableHangupSupport();
        main.run(args);
    }

    @Override
    protected void doStart() throws Exception
    {
        super.doStart();
        if (applicationContext == null)
        {
            applicationContext = createDefaultApplicationContext();
        }

        LOG.debug("Starting Spring ApplicationContext: "
                + applicationContext.getId());
        applicationContext.start();
        postProcessContext();
    }

    protected void doStop() throws Exception
    {
        super.doStop();
        if (applicationContext != null)
        {
            System.out.println("Stopping Spring ApplicationContext: "
                    + applicationContext.getId());
            IOHelper.close(applicationContext);
        }
    }
    protected AbstractApplicationContext createDefaultApplicationContext()
            throws IOException
    {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.getEnvironment().setActiveProfiles(envName);
        ctx.scan(configClassesPkg.split(" "));

        PropertySourcesPlaceholderConfigurer pph = new PropertySourcesPlaceholderConfigurer();
        Properties properties = new Properties();
        properties.setProperty("port", port);
        pph.setProperties(properties);
        ctx.addBeanFactoryPostProcessor(pph);
        ctx.refresh();
        return ctx;
    }

    @Override
    protected ProducerTemplate findOrCreateCamelTemplate() {
        String[] names = getApplicationContext().getBeanNamesForType(
                ProducerTemplate.class);
        if (names != null && names.length > 0)
        {
            return getApplicationContext().getBean(names[0],
                    ProducerTemplate.class);
        }
        if (getCamelContexts().isEmpty())
        {
            throw new IllegalArgumentException(
                    "No CamelContexts are available so cannot create a ProducerTemplate!");
        }
        return getCamelContexts().get(0).createProducerTemplate();
    }

    @Override
    protected Map<String, CamelContext> getCamelContextMap() {
        Map<String, SpringCamelContext> map = applicationContext
                .getBeansOfType(SpringCamelContext.class);
        Set<Map.Entry<String, SpringCamelContext>> entries = map.entrySet();
        Map<String, CamelContext> answer = new HashMap<String, CamelContext>();
        for (Map.Entry<String, SpringCamelContext> entry : entries)
        {
            String name = entry.getKey();
            CamelContext camelContext = entry.getValue();
            answer.put(name, camelContext);
        }
        return answer;
    }

    @Override
    protected ModelFileGenerator createModelFileGenerator() throws JAXBException {
        return null;
    }

    public AbstractApplicationContext getApplicationContext()
    {
        return applicationContext;
    }
}

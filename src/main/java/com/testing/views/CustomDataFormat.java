package com.testing.views;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.camel.Exchange;
import org.apache.camel.spi.DataFormat;

import java.io.InputStream;
import java.io.OutputStream;

public class CustomDataFormat  implements DataFormat{
    private ObjectMapper jacksonMapper;
    public CustomDataFormat(){
        jacksonMapper = new ObjectMapper();
    }
    @Override
    public void marshal(Exchange exchange, Object obj, OutputStream stream) throws Exception {
        Class view = (Class) exchange.getProperty("viewClass");
        if (view != null)
        {
            ObjectWriter w = jacksonMapper.writerWithView(view);
            w.writeValue(stream, obj);
        }
        else
            stream.write(jacksonMapper.writeValueAsBytes(obj));

    }

    @Override
    public Object unmarshal(Exchange exchange, InputStream stream) throws Exception {
        return null;
    }
}

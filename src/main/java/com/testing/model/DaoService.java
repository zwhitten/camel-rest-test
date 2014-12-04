package com.testing.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@SuppressWarnings("unchecked")
@Scope("prototype")
public class DaoService {


    public DataPOJO getData() {

        return new DataPOJO().setData("Sherlock").setValue("Holmes").setCount(10);
    }
    
    public List<DataPOJO> getDatas() {
        List<DataPOJO> datas = new ArrayList<DataPOJO>();
        for (int i = 0; i < 10; i++) {

            datas.add(new DataPOJO().setCount(i).setData("data::" + i).setValue("value::" + i));
        }
        return datas;
    }

}

package com.trace_spring.charles;

import com.trace_spring.charles.dao.Dao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:aop.xml"})
public class TestAop {

    @Test
    public void testAop(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("aop.xml");

        Dao dao = (Dao) ac.getBean("daoImpl");

        dao.select();
    }
}

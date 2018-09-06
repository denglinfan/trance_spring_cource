package com.trace_spring.charles;

import com.trace_spring.charles.dao.Dao;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.expression.ParseException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:aop.xml"})
public class TestAop {

    @Test
    public void testAop(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("aop.xml");

        Dao dao = (Dao) ac.getBean("daoImpl");

        dao.select();
    }

    public static void main(String[] args) throws Exception {
        //String tt = "20130615,20130626,20130701,20130731,20130826,20130904,20130911,20130913,20130916,20130917,20130922,20130924,20130925,20130926,20131008,20131015,20131105,20131112,20131113,20131122,20131125,20131204,20131224,20140106,20140114,20140305,20140312,20140317,20140319,20140320,20140325,20140327,20140328,20140404,20140422,20140423,20140506,20140603,20140605,20140617,20140709,20140723,20140805,20140827,20140902,20140910,20140917,20140925,20140926,20140930,20141009,20141013,20141016,20141020,20141104,20141105,20141119,20141120,20141209,20150112,20150120,20150413,20150504,20150702,20150714,20150728,20150810,20150820,20150825,20150828,20150907,20150908,20151021,20151028,20151030,20151103,20151110,20151113,20151117,20151123,20151204,20151207,20151209,20151211,20160118,20160226,20160302,20160318,20160321,20160322,20160324,20160325,20160405,20160425,20160505,20160506,20160510,20160517,20160527,20160615,20160624,20160705,20160728,20160803,20160804,20160906,20160908,20161017,20161020,20161026,20161108,20161111,20161118,20161121,20161202,20161206,20161214,20161219,20161228,20170113,20170223,20170306,20170309,20170317,20170321,20170322,20170412,20170413,20170424,20170505,20170511,20170522,20170523,20170601,20170602,20170608,20170614,20170619,20170626,20170627,20170628,20170630,20170704,20170727,20170824,20170908,20170913,20170921,20170926,20170927,20170928,20171009,20171018,20171023,20171027,20171114,20171127,20171205,20171212,20171225,20171228,20180109,20180111,20180114,20180115,20180201,20180301,20180305,20180306,20180307,20180309,20180312,20180313,20180314,20180315,20180319,20180326,20180327,20180328,20180329,20180330,20180402,20180403,20180404,20180410,20180413,20180416,20180418,20180419,20180423,20180426,20180427,20180502,20180507,20180514,20180521,20180531,20180601,20180605,20180608,20180612,20180613,20180614,20180619,20180621,20180625,20180626,20180628,20180704,20180706,20180719,20180724,20180725,20180808,20180813,20180815,20180817,20180820";
        String tt = "20170930,20171014,20171020,20171028,20171107,20171110,20171116,20171122,20171124,20180120,20180217,20180212,20180831,20170306,20180807,20180811,20180812,20180814,20180815,20180817,20180819,20180822,20180823,20180825,20180831,20180901,20180902,20180903,20180904,20170223,20130922,20170223";
        final String[] dateStr = tt.split(",");
        final int middle = dateStr.length/2;
        final CountDownLatch countDownLatch = new CountDownLatch(2);
        Thread t1 = new Thread(){
            public void run(){
                pullGoodsByDays(dateStr, 0, middle);
                countDownLatch.countDown();
            }
        };

        Thread t2 = new Thread(){
            public void run(){
                pullGoodsByDays(dateStr, middle, dateStr.length);
                countDownLatch.countDown();
            }
        };

        t1.start();
        t2.start();
        try {
            countDownLatch.await();
        }catch (Exception ex){

        }
    }

    public static final String url = "http://uat-api.jdwl.com/edi/jdsale/unicomHuasheng/transportGoods";
    public static void pullGoodsByDays(String[] str, int start, int end){
        for (int i=start; i<end; i++ ){
            try {
                appadd("{\"time\":\""+str[i]+"\"}",url);
            } catch (IOException e) {
                System.out.println("拉取异常日期：" + str[i]);
            }
        }
    }

    public static volatile Date pullDate = new Date();
    public static String excludeDate = "20130615,20130626,20130701,20130731,20130826,20130904,20130911,20130913,20130916,20130917,20130922,20130924,20130925,20130926,20131008,20131015,20131105,20131112,20131113,20131122,20131125,20131204,20131224,20140106,20140114,20140305,20140312,20140317,20140319,20140320,20140325,20140327,20140328,20140404,20140422,20140423,20140506,20140603,20140605,20140617,20140709,20140723,20140805,20140827,20140902,20140910,20140917,20140925,20140926,20140930,20141009,20141013,20141016,20141020,20141104,20141105,20141119,20141120,20141209,20150112,20150120,20150413,20150504,20150702,20150714,20150728,20150810,20150820,20150825,20150828,20150907,20150908,20151021,20151028,20151030,20151103,20151110,20151113,20151117,20151123,20151204,20151207,20151209,20151211,20160118,20160226,20160302,20160318,20160321,20160322,20160324,20160325,20160405,20160425,20160505,20160506,20160510,20160517,20160527,20160615,20160624,20160705,20160728,20160803,20160804,20160906,20160908,20161017,20161020,20161026,20161108,20161111,20161118,20161121,20161202,20161206,20161214,20161219,20161228,20170113,20170223,20170306,20170309,20170317,20170321,20170322,20170412,20170413,20170424,20170505,20170511,20170522,20170523,20170601,20170602,20170608,20170614,20170619,20170626,20170627,20170628,20170630,20170704,20170727,20170824,20170908,20170913,20170921,20170926,20170927,20170928,20171009,20171018,20171023,20171027,20171114,20171127,20171205,20171212,20171225,20171228,20180109,20180111,20180114,20180115,20180201,20180301,20180305,20180306,20180307,20180309,20180312,20180313,20180314,20180315,20180319,20180326,20180327,20180328,20180329,20180330,20180402,20180403,20180404,20180410,20180413,20180416,20180418,20180419,20180423,20180426,20180427,20180502,20180507,20180514,20180521,20180531,20180601,20180605,20180608,20180612,20180613,20180614,20180619,20180621,20180625,20180626,20180628,20180704,20180706,20180719,20180724,20180725,20180808,20180813,20180815,20180817,20180820";
    public static void pullGoodsByStartDate(){
        try {
            ExecutorService cachedThreadPool = Executors.newFixedThreadPool(3);
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            final CountDownLatch countDownLatch = new CountDownLatch(3);
            final long startDate = sdf.parse("20170622").getTime() ;
            for (int i=0; i<3; i++){
                cachedThreadPool.submit(new Runnable() {
                    public void run() {
                        while(true){
                            Date tmpDate = null;
                            synchronized (TestAop.class){
                                pullDate = new Date(pullDate.getTime() - 24 * 60 * 60 * 1000);
                                tmpDate = pullDate;
                            }
                            try {
                                if(startDate > tmpDate.getTime()) {
                                    break;
                                }
                                pullGoods(sdf.format(tmpDate));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        countDownLatch.countDown();
                    }

                });
            }
            try {
                countDownLatch.await();
            }catch (Exception ex){

            }
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
    }
    public static void pullGoods(String dateStr){
        try {
            String date = dateStr;

            if (excludeDate.indexOf(date) == -1) {
                appadd("{\"time\":\"" + date + "\"}", url);
            }
        } catch (Exception e) {
        }
    }

    public static void pullGoodsByExcel() {
        try {
            Workbook book = new HSSFWorkbook(new FileInputStream("E:\\goodsNo.xlsx"));
            Sheet sheet = book.getSheet("Sheet2");
            for(int i = 0; i <= sheet.getLastRowNum();i++) {
                Row row = sheet.getRow(i);
                String time = row.getCell(0).getStringCellValue();
                appadd("{\"time\":\""+time+"\"}",url);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void appadd(String jsonParam, String urls) throws IOException {
        HttpURLConnection connection=null;
        try {
            //创建连接
            URL url = new URL(urls);
            connection = (HttpURLConnection) url.openConnection();

            //设置http连接属性
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);

            //设置http头 消息
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestProperty("Accept","application/json");
            connection.connect();

            OutputStream out = connection.getOutputStream();
            out.write(jsonParam.getBytes());
            out.flush();
            out.close();

//          读取响应
			/*BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String lines;
			StringBuffer sb = new StringBuffer("");
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8");
				sb.append(lines);
			}
			System.out.println(sb);
			System.out.println("==================================================================================");
			reader.close();*/
//          断开连接
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

/**
 * 
 */
package com.garlane.relation.common.utils.web;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**解析HTML的工具类
 * @author lixingfa
 * @date 2018年5月28日下午7:30:16
 * 
 */
public class HtmlUtil {
	
	public static void getInputs(){
		
	}
	
	public static void demo1(String htmltest) {
    	String html = htmltest;
    	Document doc = Jsoup.parse(html);
    	Elements rows = doc.select("table[class=list]").get(0).select("tr");
    	if (rows.size() == 1) {
        		System.out.println("没有结果");
    	}else {
        		System.out.println("--------------------------- 查询结果 ---------------------------");
        		Element row = rows.get(1);
       	 	System.out.println("暂存单号:" + row.select("td").get(0).text());
        		System.out.println("投保单号:" + row.select("td").get(1).text());
        		System.out.println("保单号:" + row.select("td").get(2).text());
        		System.out.println("投保人:" + row.select("td").get(3).text());
        		System.out.println("被保险人:" + row.select("td").get(4).text());
        		System.out.println("号牌号码:" + row.select("td").get(5).text());
        		System.out.println("车架号:" + row.select("td").get(6).text());
        		System.out.println("录单人:" + row.select("td").get(7).text());
        		System.out.println("投保日期:" + row.select("td").get(8).text());
        		System.out.println("暂存单状态:" + row.select("td").get(9).text());
        		System.out.println("状态：" + row.select("td").get(10).text());
        		System.out.println("-----------------------------------------------------------------");
    	}

	}
}

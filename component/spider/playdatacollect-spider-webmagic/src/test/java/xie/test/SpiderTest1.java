package xie.test;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.example.BaiduBaikePageProcessor;

import java.util.ArrayList;
import java.util.List;

public  class SpiderTest1 {
	public static void main(String[] arg) {
//		Spider.create(new GithubRepoPageProcessor()).addUrl("https://github.com/code4craft").thread(5).run();


		//single download
		Spider spider = Spider.create(new BaiduBaikePageProcessor()).thread(2);
		String urlTemplate = "http://baike.baidu.com/search/word?word=%s&pic=1&sug=1&enc=utf8";
		ResultItems resultItem = spider.<ResultItems>get(String.format(urlTemplate, "水力发电"));
		System.out.println(resultItem);

		//multidownload
		List<String> list = new ArrayList<String>();
		list.add(String.format(urlTemplate,"风力发电"));
		list.add(String.format(urlTemplate,"太阳能"));
		list.add(String.format(urlTemplate,"地热发电"));
		list.add(String.format(urlTemplate,"地热发电"));
		list.add(String.format(urlTemplate,"魔法少女"));
		list.add(String.format(urlTemplate,"真实的魔法少女"));
		List<ResultItems> resultItems = spider.<ResultItems>getAll(list);
		for (ResultItems resultItem2 : resultItems) {
			System.out.println(resultItem2.getAll());
		}
		spider.close();
	}
}

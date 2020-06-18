package com.jb.xjjreptile.reptile;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;

import java.util.ArrayList;
import java.util.List;
public class urlProcessor implements PageProcessor {

    private static Site site = new Site()
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.97 Safari/537.36 Edg/83.0.478.50")
            .setSleepTime(10)
            .setCharset("utf-8")
            .setTimeOut(10000);

    @Override
    public void process(Page page) {
        String rawText = page.getRawText();

        List<String> strings = new JsonPathSelector("$.postList").selectList(rawText);
        List<String> urlList = new ArrayList<>();
        for (String string : strings) {
//            System.out.println(string);
            String url = new JsonPathSelector("$.url").select(string);
            urlList.add(url);
        }
        String[] urls = new String[urlList.size()];
        for (int i = 0; i < urls.length; i++) {
            urls[i] = urlList.get(i);
        }
        Spider.create(new imgProcessor()).addUrl(urls).thread(1).run();
    }

    @Override
    public Site getSite() {
        return site;
    }


    public static void main(String[] args) {


        Spider.create(new urlProcessor()).addUrl("https://tuchong.com/rest/tags/少女/posts?page=1&count=20&order=weekly").thread(1).run();

    }
}

package com.github.shy526.cartoon;

import com.github.shy526.cartoon.factory.CartoonChapterFactory;
import com.github.shy526.cartoon.factory.CartoonInfoFactory;
import com.github.shy526.cartoon.pojo.CartoonChapter;
import com.github.shy526.cartoon.pojo.CartoonInfo;
import com.github.shy526.common.HttpResult;
import com.github.shy526.service.HttpClientService;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class CartoonApplication implements CommandLineRunner {
    @Autowired
    private HttpClientService httpClientService;

    public static void main(String[] args) {
        SpringApplication.run(CartoonApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        String page = "https://xmanhua.com/manga-list-0-0-p%s/";
        for (int i = 0; ; i++) {
            HttpResult httpResult1 = httpClientService.get(String.format(page, i));
            final String entityStr = httpResult1.getEntityStr();
            if (StringUtils.isEmpty(entityStr)) {
                break;
            }
            Elements xElements = Jsoup.parse(entityStr).select(".mh-item");
            xElements.forEach(item -> {
                String mCover = item.select("mh-cover").attr("src");
                Elements itemDetali = item.select(".title>a");
                String mId = itemDetali.attr("href").replaceAll("/", "");
                String xTitle = itemDetali.text();
                System.out.println(mId + ":" + xTitle);
                parse(mId);
            });

        }


    }

    private void parse(String cartoonId ) {
        CartoonInfo cartoonInfo = null;
        {
            final HttpResult mPage = httpClientService.get(String.format("https://xmanhua.com/%s/", cartoonId));
            Document doc = Jsoup.parse(mPage.getEntityStr());
            cartoonInfo = CartoonInfoFactory.produce(doc);
            final Elements select = doc.select("#chapterlistload>a");
            for (int i = select.size() - 1; i >= 0; i--) {
                Element element = select.get(i);
                CartoonChapter cartoonChapter = CartoonChapterFactory.produce(element);
                cartoonInfo.addCartoonChapters(cartoonChapter);
            }
        }

        cartoonInfo.getCartoonChapters().forEach(item -> {
            String cid = item.getId().toString();
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("javascript");
            String nowImgUrl = "https://xmanhua.com/m%s/chapterimage.ashx?cid=%s&page=%s&key=";
            Map<String, String> header = new HashMap<>(1);
            header.put("referer", "https://xmanhua.com/");
            //根据返回的图片数量取决偏移量
            int index=1;
            for (int page = 1; page <= item.getTotal(); page+=index) {
                HttpResult httpResult = httpClientService.get(String.format(nowImgUrl, cid, cid, page), null, header);
                try {
                    ScriptObjectMirror eval = (ScriptObjectMirror) engine.eval(httpResult.getEntityStr());
                    if (eval != null) {
                        index=eval.size();
                        eval.forEach((k, v) -> item.addImages(v.toString()));

                    } else {
                        break;
                    }
                } catch (ScriptException e) {
                    e.printStackTrace();
                }
            }

        });
    }
}

package com.github.shy526.cartoon.factory;

import com.github.shy526.cartoon.pojo.CartoonInfo;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Administrator
 */
public class CartoonInfoFactory {
   public static CartoonInfo produce(Document document) {
        CartoonInfo cartoonInfo = new CartoonInfo();
        Elements detailInfo = document.select(".detail-info");
        final Elements tip = detailInfo.select(".detail-info-tip>span");
        for (Element item : tip) {
            String text = item.text();
            String[] split = text.split("：");
            if ("作者".equals(split[0])) {
                cartoonInfo.setAuthor(item.select("a").text());
            } else if ("狀態".equals(split[0])) {
                cartoonInfo.setState("連載中".equals(item.select("span>span").text())?1:0);
            } else if ("題材".equals(split[0])) {
                cartoonInfo.setTopic(item.select(".item").text());
            }
        }
        cartoonInfo.setContent(detailInfo.select(".detail-info-content").text());
        cartoonInfo.setCover(detailInfo.select(".detail-info-cover").attr("src"));
        cartoonInfo.setTitle(detailInfo.select(".detail-info-title").text());
        cartoonInfo.setId(Integer.parseInt(detailInfo.select(".detial-info-btn-con>a").attr("mid")));

        return cartoonInfo;
    }
}

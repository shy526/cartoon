package com.github.shy526.cartoon.factory;

import com.github.shy526.cartoon.pojo.CartoonChapter;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


/**
 * @author Administrator
 */
public class CartoonChapterFactory {
    public static CartoonChapter produce(Element el) {
        CartoonChapter cartoonChapter = new CartoonChapter();
        cartoonChapter.setId(Integer.parseInt(el.attr("href").replaceAll("/", "").replaceAll("m","")));
        cartoonChapter.setTitle(el.attr("title"));
        cartoonChapter.setNo(el.ownText().replaceAll(cartoonChapter.getTitle(),""));
        cartoonChapter.setTotal(Integer.parseInt(el.select("span").text().replaceAll("\\（|\\）|P", "")));
        return cartoonChapter;
    }
}

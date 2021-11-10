package com.github.shy526.cartoon.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CartoonInfo implements Serializable {

    /**
     * id
     */
    private Integer id;

    /**
     * 标题
     */
    private String title;
    /**
     * 封面
     */
    private String cover;
    /**
     * 作者
     */
    private String author;
    /**
     * 题材
     */
    private String topic;
    /**
     * 是否连载 1 连载 2 完结
     */
    private Integer state;
    /**
     * 简介
     */
    private String content;
    /**
     * 子章节
     */
    private List<CartoonChapter> cartoonChapters;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<CartoonChapter> getCartoonChapters() {
        return cartoonChapters;
    }

    public void setCartoonChapters(List<CartoonChapter> cartoonChapters) {
        this.cartoonChapters = cartoonChapters;
    }

    public CartoonInfo addCartoonChapters(CartoonChapter cartoonChapter) {
        if (cartoonChapters == null) {
            synchronized (this) {
                if (cartoonChapters == null) {
                    cartoonChapters = new ArrayList<>();
                }
            }
        }
        cartoonChapters.add(cartoonChapter);
        return this;
    }
}

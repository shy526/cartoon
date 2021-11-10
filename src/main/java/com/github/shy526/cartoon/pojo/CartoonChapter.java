package com.github.shy526.cartoon.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class CartoonChapter implements Serializable {
    private Integer id;
    private String no;
    private String title;
    private Integer total;
    private Set<String> images;


    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<String> getImages() {
        return images;
    }

    public void setImages(Set<String> images) {
        this.images = images;
    }

    public CartoonChapter addImages(String image) {
        if (images == null) {
            synchronized (this) {
                if (images == null) {
                    images = new LinkedHashSet<>();
                }
            }
        }
        images.add(image);
        return this;
    }
}

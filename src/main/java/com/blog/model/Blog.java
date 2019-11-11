package com.blog.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class Blog implements Serializable {
    private Integer id;
    private String title;
    private String blogImage;
    private String content;
    private long top;
    private Integer status;
    private String description;
    private String keywords;
    private Integer comment;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;
    private Integer typeId;
    private Integer examine;//审核状态 1.审核中  2.通过 3.未通过



    //临时
    private String startcreateTime;
    private String endcreateTime;
    private String startupdateTime;
    private String endupdateTime;
    private String typeName;


    public Integer getExamine() {
        return examine;
    }

    public void setExamine(Integer examine) {
        this.examine = examine;
    }

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

    public String getBlogImage() {
        return blogImage;
    }

    public void setBlogImage(String blogImage) {
        this.blogImage = blogImage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTop() {
        return top;
    }

    public void setTop(long top) {
        this.top = top;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Integer getComment() {
        return comment;
    }

    public void setComment(Integer comment) {
        this.comment = comment;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getStartcreateTime() {
        return startcreateTime;
    }

    public void setStartcreateTime(String startcreateTime) {
        this.startcreateTime = startcreateTime;
    }

    public String getEndcreateTime() {
        return endcreateTime;
    }

    public void setEndcreateTime(String endcreateTime) {
        this.endcreateTime = endcreateTime;
    }

    public String getStartupdateTime() {
        return startupdateTime;
    }

    public void setStartupdateTime(String startupdateTime) {
        this.startupdateTime = startupdateTime;
    }

    public String getEndupdateTime() {
        return endupdateTime;
    }

    public void setEndupdateTime(String endupdateTime) {
        this.endupdateTime = endupdateTime;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}

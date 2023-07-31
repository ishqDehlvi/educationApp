package com.mp.neetjeeiitprep.dataModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuestionListDataModel {
    public QuestionListDataModel() {
    }

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("pages")
    @Expose
    private Integer pages;
    /*@SerializedName("image_url")
    @Expose
    private Integer imageUrl;*/
    @SerializedName("total_page")
    @Expose
    private Integer totalPage;
    @SerializedName("questions")
    @Expose
    private List<QuestionDataModel> questions = null;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public List<QuestionDataModel> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDataModel> questions) {
        this.questions = questions;
    }

    /*public Integer getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Integer imageUrl) {
        this.imageUrl = imageUrl;
    }*/

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

}

package com.mp.neetjeeiitprep.dataModel;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryDataModel {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("category")
    @Expose
    private List<Category> category = null;
    @SerializedName("scategory")
    @Expose
    private List<Category> subCategory = null;

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

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public List<Category> getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(List<Category> subCategory) {
        this.subCategory = subCategory;
    }

    public class Category {

        @SerializedName("cat_id")
        @Expose
        private String catId;
        @SerializedName("cat_name")
        @Expose
        private String catName;
        @SerializedName("cat_code")
        @Expose
        private String catCode;

        public String getCatId() {
            return catId;
        }

        public void setCatId(String catId) {
            this.catId = catId;
        }

        public String getCatName() {
            return catName;
        }

        public void setCatName(String catName) {
            this.catName = catName;
        }

        public String getCatCode() {
            return catCode;
        }

        public void setCatCode(String catCode) {
            this.catCode = catCode;
        }

        @Override
        public String toString() {
            return "Category{" +
                    "catId='" + catId + '\'' +
                    ", catName='" + catName + '\'' +
                    ", catCode='" + catCode + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CategoryDataModel{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", category=" + category +
                '}';
    }
}

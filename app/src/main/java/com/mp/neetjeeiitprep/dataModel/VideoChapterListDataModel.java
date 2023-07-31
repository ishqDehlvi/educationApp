package com.mp.neetjeeiitprep.dataModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class VideoChapterListDataModel {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("typelist")
    @Expose
    private Typelist typelist;

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

    public Typelist getTypelist() {
        return typelist;
    }

    public void setTypelist(Typelist typelist) {
        this.typelist = typelist;
    }


    public class Typelist {

        @SerializedName("scat_id")
        @Expose
        private String scatId;
        @SerializedName("cat_name")
        @Expose
        private String catName;
        @SerializedName("cat_code")
        @Expose
        private String catCode;
        @SerializedName("typ")
        @Expose
        private ArrayList<Typ> typ = null;

        public String getScatId() {
            return scatId;
        }

        public void setScatId(String scatId) {
            this.scatId = scatId;
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

        public ArrayList<Typ> getTyp() {
            return typ;
        }

        public void setTyp(ArrayList<Typ> typ) {
            this.typ = typ;
        }


        public class Typ {

            @SerializedName("video_type_id")
            @Expose
            private String videoTypeId;
            @SerializedName("category_id")
            @Expose
            private String categoryId;
            @SerializedName("subcat_id")
            @Expose
            private String subcatId;
            @SerializedName("type_title")
            @Expose
            private String typeTitle;
            @SerializedName("status")
            @Expose
            private String status;

            public String getVideoTypeId() {
                return videoTypeId;
            }

            public void setVideoTypeId(String videoTypeId) {
                this.videoTypeId = videoTypeId;
            }

            public String getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(String categoryId) {
                this.categoryId = categoryId;
            }

            public String getSubcatId() {
                return subcatId;
            }

            public void setSubcatId(String subcatId) {
                this.subcatId = subcatId;
            }

            public String getTypeTitle() {
                return typeTitle;
            }

            public void setTypeTitle(String typeTitle) {
                this.typeTitle = typeTitle;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

        }

    }

}
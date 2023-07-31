package com.mp.neetjeeiitprep.dataModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubTypeDataModel {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("pdf_path")
    @Expose
    private String pdfPath;
    @SerializedName("list")
    @Expose
    private java.util.List<com.mp.neetjeeiitprep.dataModel.SubTypeDataModel.List> list = null;

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

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    public java.util.List<com.mp.neetjeeiitprep.dataModel.SubTypeDataModel.List> getList() {
        return list;
    }

    public void setList(java.util.List<com.mp.neetjeeiitprep.dataModel.SubTypeDataModel.List> list) {
        this.list = list;
    }

    public class List {

        @SerializedName("type_id")
        @Expose
        private String typeId;
        @SerializedName("type_title")
        @Expose
        private String typeTitle;
        @SerializedName("mark")
        @Expose
        private String mark;
        @SerializedName("minus_marks")
        @Expose
        private String minusMarks;
        @SerializedName("duration")
        @Expose
        private String duration;
        @SerializedName("cat_id")
        @Expose
        private String catId;
        @SerializedName("subcat_id")
        @Expose
        private String subcatId;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("ncrt_file")
        @Expose
        private String ncrtFile;
        @SerializedName("pre_yr_file")
        @Expose
        private String preYrFile;
        @SerializedName("status")
        @Expose
        private String status;

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        public String getTypeTitle() {
            return typeTitle;
        }

        public void setTypeTitle(String typeTitle) {
            this.typeTitle = typeTitle;
        }

        public String getMark() {
            return mark;
        }

        public void setMark(String mark) {
            this.mark = mark;
        }

        public String getMinusMarks() {
            return minusMarks;
        }

        public void setMinusMarks(String minusMarks) {
            this.minusMarks = minusMarks;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getCatId() {
            return catId;
        }

        public void setCatId(String catId) {
            this.catId = catId;
        }

        public String getSubcatId() {
            return subcatId;
        }

        public void setSubcatId(String subcatId) {
            this.subcatId = subcatId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getNcrtFile() {
            return ncrtFile;
        }

        public void setNcrtFile(String ncrtFile) {
            this.ncrtFile = ncrtFile;
        }

        public String getPreYrFile() {
            return preYrFile;
        }

        public void setPreYrFile(String preYrFile) {
            this.preYrFile = preYrFile;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "List{" +
                    "typeId='" + typeId + '\'' +
                    ", typeTitle='" + typeTitle + '\'' +
                    ", catId='" + catId + '\'' +
                    ", subcatId='" + subcatId + '\'' +
                    ", type='" + type + '\'' +
                    ", ncrtFile='" + ncrtFile + '\'' +
                    ", preYrFile='" + preYrFile + '\'' +
                    ", status='" + status + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SubTypeDataModel{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", pdfPath='" + pdfPath + '\'' +
                ", list=" + list +
                '}';
    }
}

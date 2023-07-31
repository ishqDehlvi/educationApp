package com.mp.neetjeeiitprep.dataModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PdfListNcertDataModel {

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
    private List<Pdlist> pdlist = null;

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

    public List<Pdlist> getPdlist() {
        return pdlist;
    }

    public void setPdlist(List<Pdlist> pdlist) {
        this.pdlist = pdlist;
    }

    public class Pdlist {

        @SerializedName("ncert_id")
        @Expose
        private String ncertId;
        @SerializedName("type_id")
        @Expose
        private String typeId;
        @SerializedName("filenames")
        @Expose
        private String filenames;
        @SerializedName("ncert_file_name")
        @Expose
        private String ncertFileName;
        @SerializedName("status")
        @Expose
        private String status;

        public String getNcertId() {
            return ncertId;
        }

        public void setNcertId(String ncertId) {
            this.ncertId = ncertId;
        }

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        public String getFilenames() {
            return filenames;
        }

        public void setFilenames(String filenames) {
            this.filenames = filenames;
        }

        public String getNcertFileName() {
            return ncertFileName;
        }

        public void setNcertFileName(String ncertFileName) {
            this.ncertFileName = ncertFileName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }

}
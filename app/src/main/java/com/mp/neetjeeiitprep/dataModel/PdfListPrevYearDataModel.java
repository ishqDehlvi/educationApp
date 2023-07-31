package com.mp.neetjeeiitprep.dataModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PdfListPrevYearDataModel {

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

        @SerializedName("pdf_id")
        @Expose
        private String pdfId;
        @SerializedName("type_id")
        @Expose
        private String typeId;
        @SerializedName("filenames")
        @Expose
        private String filenames;
        @SerializedName("pre_yr_file")
        @Expose
        private String preYrFile;
        @SerializedName("status")
        @Expose
        private String status;

        public String getPdfId() {
            return pdfId;
        }

        public void setPdfId(String pdfId) {
            this.pdfId = pdfId;
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

    }
}
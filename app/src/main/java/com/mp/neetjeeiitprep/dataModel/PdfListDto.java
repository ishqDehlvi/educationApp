package com.mp.neetjeeiitprep.dataModel;

public class PdfListDto {

    String id;
    String pdfTitle;
    String pdfFileName;

    public PdfListDto(String id, String pdfTitle, String pdfFileName, String fullPdfUrl) {
        this.id = id;
        this.pdfTitle = pdfTitle;
        this.pdfFileName = pdfFileName;
        this.fullPdfUrl = fullPdfUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPdfTitle() {
        return pdfTitle;
    }

    public void setPdfTitle(String pdfTitle) {
        this.pdfTitle = pdfTitle;
    }

    public String getPdfFileName() {
        return pdfFileName;
    }

    public void setPdfFileName(String pdfFileName) {
        this.pdfFileName = pdfFileName;
    }

    public String getFullPdfUrl() {
        return fullPdfUrl;
    }

    public void setFullPdfUrl(String fullPdfUrl) {
        this.fullPdfUrl = fullPdfUrl;
    }

    String fullPdfUrl;

}

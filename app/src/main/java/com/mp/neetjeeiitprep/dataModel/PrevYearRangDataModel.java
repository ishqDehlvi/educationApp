package com.mp.neetjeeiitprep.dataModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PrevYearRangDataModel {

    @SerializedName("year")
    @Expose
    private List<Year> year = null;

    public List<Year> getYear() {
        return year;
    }

    public void setYear(List<Year> year) {
        this.year = year;
    }

    public class Year {

        @SerializedName("year_id")
        @Expose
        private String yearId;
        @SerializedName("year_range")
        @Expose
        private String yearRange;
        @SerializedName("cat_id")
        @Expose
        private String catId;
        @SerializedName("subcat_id")
        @Expose
        private String subcatId;
        @SerializedName("status")
        @Expose
        private String status;

        public String getYearId() {
            return yearId;
        }

        public void setYearId(String yearId) {
            this.yearId = yearId;
        }

        public String getYearRange() {
            return yearRange;
        }

        public void setYearRange(String yearRange) {
            this.yearRange = yearRange;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "Year{" +
                    "yearId='" + yearId + '\'' +
                    ", yearRange='" + yearRange + '\'' +
                    ", catId='" + catId + '\'' +
                    ", subcatId='" + subcatId + '\'' +
                    ", status='" + status + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "PrevYearRangDataModel{" +
                "year=" + year +
                '}';
    }
}
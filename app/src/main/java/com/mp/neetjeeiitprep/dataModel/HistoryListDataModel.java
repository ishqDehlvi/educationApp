package com.mp.neetjeeiitprep.dataModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HistoryListDataModel {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("results")
    @Expose
    private List<Result> results = null;

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

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public class Result {

        @SerializedName("sbcategory")
        @Expose
        private String subcatName;
        @SerializedName("type_name")
        @Expose
        private String typeName;
        @SerializedName("total_question")
        @Expose
        private String totalQuestion;
        @SerializedName("subcat_id")
        @Expose
        private String subcatId;
        @SerializedName("type_id")
        @Expose
        private String typeId;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("total_correct_answer")
        @Expose
        private String totalCorrectAnswer;
        @SerializedName("attemp_answer")
        @Expose
        private String attempAnswer;
        @SerializedName("attemp_date")
        @Expose
        private String attempDate;
        @SerializedName("percentage")
        @Expose
        private String percentage;
        @SerializedName("last_id")
        @Expose
        private String lastId;

        public String getSubcatName() {
            return subcatName;
        }

        public void setSubcatName(String subcatName) {
            this.subcatName = subcatName;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getTotalQuestion() {
            return totalQuestion;
        }

        public void setTotalQuestion(String totalQuestion) {
            this.totalQuestion = totalQuestion;
        }

        public String getSubcatId() {
            return subcatId;
        }

        public void setSubcatId(String subcatId) {
            this.subcatId = subcatId;
        }

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTotalCorrectAnswer() {
            return totalCorrectAnswer;
        }

        public void setTotalCorrectAnswer(String totalCorrectAnswer) {
            this.totalCorrectAnswer = totalCorrectAnswer;
        }

        public String getAttempAnswer() {
            return attempAnswer;
        }

        public void setAttempAnswer(String attempAnswer) {
            this.attempAnswer = attempAnswer;
        }

        public String getAttempDate() {
            return attempDate;
        }

        public void setAttempDate(String attempDate) {
            this.attempDate = attempDate;
        }

        public String getPercentage() {
            return percentage;
        }

        public void setPercentage(String percentage) {
            this.percentage = percentage;
        }

        public String getLastId() {
            return lastId;
        }

        public void setLastId(String lastId) {
            this.lastId = lastId;
        }

    }

}

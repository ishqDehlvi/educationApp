package com.mp.neetjeeiitprep.dataModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultListDataModel {

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

        @SerializedName("parent_cat")
        @Expose
        private String parentCat;
        @SerializedName("cname")
        @Expose
        private String cname;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("total_question")
        @Expose
        private String totalQuestion;
        @SerializedName("total_correct_answer")
        @Expose
        private String totalCorrectAnswer;
        @SerializedName("attempted_answer")
        @Expose
        private String attemptedAnswer;
        @SerializedName("attemp_date")
        @Expose
        private String attempDate;
        @SerializedName("percentage")
        @Expose
        private String percentage;
        @SerializedName("marks_obtained")
        @Expose
        private String marksObtained;

        public String getParentCat() {
            return parentCat;
        }

        public void setParentCat(String parentCat) {
            this.parentCat = parentCat;
        }

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTotalQuestion() {
            return totalQuestion;
        }

        public void setTotalQuestion(String totalQuestion) {
            this.totalQuestion = totalQuestion;
        }

        public String getTotalCorrectAnswer() {
            return totalCorrectAnswer;
        }

        public void setTotalCorrectAnswer(String totalCorrectAnswer) {
            this.totalCorrectAnswer = totalCorrectAnswer;
        }

        public String getAttemptedAnswer() {
            return attemptedAnswer;
        }

        public void setAttemptedAnswer(String attemptedAnswer) {
            this.attemptedAnswer = attemptedAnswer;
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

        public String getMarksObtained() {
            return marksObtained;
        }

        public void setMarksObtained(String marksObtained) {
            this.marksObtained = marksObtained;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "parentCat='" + parentCat + '\'' +
                    ", cname='" + cname + '\'' +
                    ", type='" + type + '\'' +
                    ", totalQuestion='" + totalQuestion + '\'' +
                    ", totalCorrectAnswer='" + totalCorrectAnswer + '\'' +
                    ", attemptedAnswer='" + attemptedAnswer + '\'' +
                    ", attempDate='" + attempDate + '\'' +
                    ", percentage='" + percentage + '\'' +
                    ", marksObtained='" + marksObtained + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ResultListDataModel{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", results=" + results +
                '}';
    }
}
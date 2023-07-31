package com.mp.neetjeeiitprep.dataModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultDataModel {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("results")
    @Expose
    private String results;
    @SerializedName("percentage")
    @Expose
    private float percentage;
    @SerializedName("results_data")
    @Expose
    private ResultsData resultsData;

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

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public ResultsData getResultsData() {
        return resultsData;
    }

    public void setResultsData(ResultsData resultsData) {
        this.resultsData = resultsData;
    }

    public class ResultsData {

        @SerializedName("total_question")
        @Expose
        private String totalQuestion;
        @SerializedName("total_correct_answer")
        @Expose
        private String totalCorrectAnswer;
        @SerializedName("attemp_date")
        @Expose
        private String attempDate;
        @SerializedName("percentage")
        @Expose
        private String percentage;
        @SerializedName("marks_obtained")
        @Expose
        private String marksObtained;
        @SerializedName("negative_marks")
        @Expose
        private String negativeMarks;
        @SerializedName("attempted_answer")
        @Expose
        private String attemptedAnswer;

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

        public String getNegativeMarks() {
            return negativeMarks;
        }

        public void setNegativeMarks(String negativeMarks) {
            this.negativeMarks = negativeMarks;
        }

        public String getAttemptedAnswer() {
            return attemptedAnswer;
        }

        public void setAttemptedAnswer(String attemptedAnswer) {
            this.attemptedAnswer = attemptedAnswer;
        }

        @Override
        public String toString() {
            return "ResultsData{" +
                    "totalQuestion='" + totalQuestion + '\'' +
                    ", totalCorrectAnswer='" + totalCorrectAnswer + '\'' +
                    ", attempDate='" + attempDate + '\'' +
                    ", percentage='" + percentage + '\'' +
                    ", marksObtained='" + marksObtained + '\'' +
                    ", negativeMarks='" + negativeMarks + '\'' +
                    ", attemptedAnswer='" + attemptedAnswer + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ResultDataModel{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", results='" + results + '\'' +
                ", percentage=" + percentage +
                ", resultsData=" + resultsData +
                '}';
    }
}

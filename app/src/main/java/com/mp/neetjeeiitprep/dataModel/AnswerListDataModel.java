package com.mp.neetjeeiitprep.dataModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AnswerListDataModel {

    @SerializedName("answer_id")
    @Expose
    private String answerId;
    @SerializedName("question_id")
    @Expose
    private String questionId;
    @SerializedName("answer")
    @Expose
    private String answer;
    @SerializedName("correct")
    @Expose
    private String correct;
    @SerializedName("status")
    @Expose
    private String status;

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

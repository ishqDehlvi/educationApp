package com.mp.neetjeeiitprep.dataModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuestionDataModel {

    @SerializedName("question_id")
    @Expose
    private String questionId;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("question_image_detail")
    @Expose
    private String question_image_detail;
    @SerializedName("question_image")
    @Expose
    private String questionImage;
    @SerializedName("explaination")
    @Expose
    private String explaination;
    @SerializedName("minus_mark")
    @Expose
    private String minusMark;
    @SerializedName("mark")
    @Expose
    private String questionMark;
    @SerializedName("anser_list")
    @Expose
    private List<AnswerListDataModel> anserList = null;


    /*** Question Manipulation **/

    private int userSelectedAnswerId;
    private int userSelectAnswer;
    private int selectedRdoId;
    private Boolean questionStatus=false;
    private Boolean isUserAnswerCorrect=false;

    private int imageWidth=0;
    private int imageHeight=0;


    public int getUserSelectedAnswerId() {
        return userSelectedAnswerId;
    }

    public void setUserSelectedAnswerId(int userSelectedAnswerId) {
        this.userSelectedAnswerId = userSelectedAnswerId;
    }

    public int getUserSelectAnswer() {
        return userSelectAnswer;
    }

    public void setUserSelectAnswer(int userSelectAnswer) {
        this.userSelectAnswer = userSelectAnswer;
    }

    public int getSelectedRdoId() {
        return selectedRdoId;
    }

    public Boolean getQuestionStatus() {
        return questionStatus;
    }

    public void setQuestionStatus(Boolean questionStatus) {
        this.questionStatus = questionStatus;
    }

    public void setSelectedRdoId(int selectedRdoId) {
        this.selectedRdoId = selectedRdoId;
    }

    public Boolean getUserAnswerCorrect() {
        return isUserAnswerCorrect;
    }

    public void setUserAnswerCorrect(Boolean userAnswerCorrect) {
        isUserAnswerCorrect = userAnswerCorrect;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    /*** End Of Question Manipulation ***/


    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestion_image_detail() {
        return question_image_detail;
    }

    public void setQuestion_image_detail(String question_image_detail) {
        this.question_image_detail = question_image_detail;
    }

    public String getExplaination() {
        return explaination;
    }

    public void setExplaination(String explaination) {
        this.explaination = explaination;
    }

    public List<AnswerListDataModel> getAnserList() {
        return anserList;
    }

    public void setAnserList(List<AnswerListDataModel> anserList) {
        this.anserList = anserList;
    }

    public String getQuestionImage() {
        return questionImage;
    }

    public void setQuestionImage(String questionImage) {
        this.questionImage = questionImage;
    }

    public String getMinusMark() {
        return minusMark;
    }

    public void setMinusMark(String minusMark) {
        this.minusMark = minusMark;
    }

    public String getQuestionMark() {
        return questionMark;
    }

    public void setQuestionMark(String questionMark) {
        this.questionMark = questionMark;
    }

    @Override
    public String toString() {
        return "QuestionDataModel{" +
                "questionId='" + questionId + '\'' +
                ", question='" + question + '\'' +
                ", question_image_detail='" + question_image_detail + '\'' +
                ", questionImage='" + questionImage + '\'' +
                ", explaination='" + explaination + '\'' +
                ", minusMark='" + minusMark + '\'' +
                ", questionMark='" + questionMark + '\'' +
                ", anserList=" + anserList +
                ", userSelectedAnswerId=" + userSelectedAnswerId +
                ", userSelectAnswer=" + userSelectAnswer +
                ", selectedRdoId=" + selectedRdoId +
                ", questionStatus=" + questionStatus +
                ", isUserAnswerCorrect=" + isUserAnswerCorrect +
                '}';
    }
}

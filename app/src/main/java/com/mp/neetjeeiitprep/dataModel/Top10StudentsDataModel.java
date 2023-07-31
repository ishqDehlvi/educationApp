package com.mp.neetjeeiitprep.dataModel;

public class Top10StudentsDataModel {
    float circularProgress;
    String name, resultHead, qaAttemptedHead, qaAttempted, answerHead, answer, marksHead, marks, dateHead, date, progressPercent;

    public float getCircularProgress() {
        return circularProgress;
    }

    public void setCircularProgress(float circularProgress) {
        this.circularProgress = circularProgress;
    }

    public String getProgressPercent() {
        return progressPercent;
    }

    public void setProgressPercent(String progressPercent) {
        this.progressPercent = progressPercent;
    }

    public Top10StudentsDataModel(float circularProgress, String name, String resultHead,
                                  String qaAttemptedHead, String qaAttempted, String answerHead,
                                  String answer, String marksHead, String marks, String dateHead, String date, String progressPercent
    ){
        this.circularProgress = circularProgress;
        this.name = name;
        this.resultHead = resultHead;
        this.qaAttemptedHead = qaAttemptedHead;
        this.qaAttempted = qaAttempted;
        this.answerHead = answerHead;
        this.answer = answer;
        this.marksHead = marksHead;
        this.marks = marks;
        this.dateHead = dateHead;
        this.date = date;
        this.progressPercent = progressPercent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResultHead() {
        return resultHead;
    }

    public void setResultHead(String resultHead) {
        this.resultHead = resultHead;
    }

    public String getQaAttemptedHead() {
        return qaAttemptedHead;
    }

    public void setQaAttemptedHead(String qaAttemptedHead) {
        this.qaAttemptedHead = qaAttemptedHead;
    }

    public String getQaAttempted() {
        return qaAttempted;
    }

    public void setQaAttempted(String qaAttempted) {
        this.qaAttempted = qaAttempted;
    }

    public String getAnswerHead() {
        return answerHead;
    }

    public void setAnswerHead(String answerHead) {
        this.answerHead = answerHead;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getMarksHead() {
        return marksHead;
    }

    public void setMarksHead(String marksHead) {
        this.marksHead = marksHead;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getDateHead() {
        return dateHead;
    }

    public void setDateHead(String dateHead) {
        this.dateHead = dateHead;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Top10StudentsDataModel(int circularProgress, String name, String resultHead,
                                  String qaAttempted, String answer,
                                  String marks, String date
    ){
        this.circularProgress = circularProgress;
        this.name = name;
        this.resultHead = resultHead;
        this.qaAttemptedHead = qaAttempted;
        this.answerHead = answer;
        this.marksHead = marks;
        this.dateHead = date;

    }
}




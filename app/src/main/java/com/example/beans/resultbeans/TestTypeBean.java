package com.example.beans.resultbeans;

public class TestTypeBean {

    private int type;
    private Content parentComment;
    private SubComment subComment;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Content getParentComment() {
        return parentComment;
    }

    public void setParentComment(Content parentComment) {
        this.parentComment = parentComment;
    }

    public SubComment getSubComment() {
        return subComment;
    }

    public void setSubComment(SubComment subComment) {
        this.subComment = subComment;
    }


    @Override
    public String toString() {
        return "TestTypeBean{" +
                "type=" + type +
                ", parentComment=" + parentComment +
                ", subComment=" + subComment +
                '}';
    }
}

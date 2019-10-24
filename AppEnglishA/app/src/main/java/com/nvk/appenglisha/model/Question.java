package com.nvk.appenglisha.model;

public class Question {
    private int id;
    private String question_name;
    private String schemes_a;
    private String schemes_b;
    private String schemes_c;
    private String schemes_d;
    private String answer;
    private String explain;
    private int code_id;
    private int status;

    public Question() {
    }

    public Question(int id, String question_name, String schemes_a, String schemes_b, String schemes_c, String schemes_d, String answer, String explain, int code_id, int status) {
        this.id = id;
        this.question_name = question_name;
        this.schemes_a = schemes_a;
        this.schemes_b = schemes_b;
        this.schemes_c = schemes_c;
        this.schemes_d = schemes_d;
        this.answer = answer;
        this.explain = explain;
        this.code_id = code_id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion_name() {
        return question_name;
    }

    public void setQuestion_name(String question_name) {
        this.question_name = question_name;
    }

    public String getSchemes_a() {
        return schemes_a;
    }

    public void setSchemes_a(String schemes_a) {
        this.schemes_a = schemes_a;
    }

    public String getSchemes_b() {
        return schemes_b;
    }

    public void setSchemes_b(String schemes_b) {
        this.schemes_b = schemes_b;
    }

    public String getSchemes_c() {
        return schemes_c;
    }

    public void setSchemes_c(String schemes_c) {
        this.schemes_c = schemes_c;
    }

    public String getSchemes_d() {
        return schemes_d;
    }

    public void setSchemes_d(String schemes_d) {
        this.schemes_d = schemes_d;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public int getCode_id() {
        return code_id;
    }

    public void setCode_id(int code_id) {
        this.code_id = code_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

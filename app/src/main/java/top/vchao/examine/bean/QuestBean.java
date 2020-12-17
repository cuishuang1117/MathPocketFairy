package top.vchao.examine.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

/**

 */

public class QuestBean {
    private String title;// 问题
    private String answer;// 正确答案
    private String myanswer;// 我的答案

    public QuestBean(String title, String answer, String myanswer) {

        this.title = title;
        this.answer = answer;
        this.myanswer = myanswer;
    }


    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getMyanswer() {
        return this.myanswer;
    }

    public void setMyanswer(String myanswer) {
        this.myanswer = myanswer;
    }


}
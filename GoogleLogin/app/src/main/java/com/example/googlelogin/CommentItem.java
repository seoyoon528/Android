package com.example.googlelogin;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;

public class CommentItem  {
    private int num;
    private String name;
    private String comment;
    private String icon;


    public CommentItem(int num, String name, String comment, String icon) {
        this.num = num;
        this.name = name;
        this.comment = comment;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}

//
//import android.graphics.drawable.Drawable;
//        import android.graphics.drawable.Icon;
//
//public class CommentItem  {
//    private int num;
//    private String name;
//    private String comment;
//    private Drawable icon;
//
//    public CommentItem(String name, String comment, Drawable icon) {
//        this.name = name;
//        this.comment = comment;
//        this.icon = icon;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getComment() {
//        return comment;
//    }
//
//    public void setComment(String comment) {
//        this.comment = comment;
//    }
//
//    public Drawable getIcon() {
//        return icon;
//    }
//
//    public void setIcon(Drawable icon) {
//        this.icon = icon;
//    }
//
//    public int getNum() {
//        return num;
//    }
//
//    public void setNum(int num) {
//        this.num = num;
//    }
//}
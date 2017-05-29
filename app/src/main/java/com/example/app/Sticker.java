package com.example.app;

import org.litepal.crud.DataSupport;

/**
 * Created by DBW on 2017/5/28.
 *
 */

public class Sticker extends DataSupport{

    private String content;
    private int position;
    private boolean checked;


    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

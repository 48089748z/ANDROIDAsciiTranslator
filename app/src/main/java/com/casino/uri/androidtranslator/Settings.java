package com.casino.uri.androidtranslator;

import android.app.Application;

/**
 * Created by uRi on 15/02/2016.
 */
public class Settings extends Application
{
    String value = "TEXT_TO_ASCII";

    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
}

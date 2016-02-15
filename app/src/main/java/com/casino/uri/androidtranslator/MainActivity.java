package com.casino.uri.androidtranslator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity
{
    Settings settings;
    EditText translate;
    TextView result;
    TextView text;
    TextView ascii;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("TEXT to ASCII");
        setSupportActionBar(toolbar);
        settings = (Settings) getApplication();
        translate = (EditText) this.findViewById(R.id.ETtranslate);
        result = (TextView) this.findViewById(R.id.TVresult);
        text = (TextView) this.findViewById(R.id.TVtext);
        ascii = (TextView) this.findViewById(R.id.TVascii);
        translate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s)
            {
                if (settings.getValue().equals("TEXT_TO_ASCII"))
                {
                    result.setText(textToAscii(translate.getText().toString()));
                }
                else if (settings.getValue().equals("ASCII_TO_TEXT"))
                {
                    try
                    {
                        result.setText(asciiToText(translate.getText().toString()));
                    }catch (Exception e){}

                }
            }
        });

    }
    public String textToAscii(String text)
    {
        String asciiReturn="";
        byte[] bytes = new byte[0];
        try
        {
            bytes = text.getBytes("US-ASCII");
        }
        catch (UnsupportedEncodingException e) {}
        for (int x=0; x< bytes.length; x++)
        {
            asciiReturn = asciiReturn+String.valueOf(bytes[x] + " ");
        }
        return asciiReturn;
    }
    public String asciiToText(String ascii)
    {
        String textReturn="";
        String[] numbers = ascii.split(" ");
        for(int x=0; x<numbers.length; x++)
        {
            int number = Integer.parseInt(numbers[x]);
            textReturn = textReturn+Character.toString((char) number);
        }
        return textReturn;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_change)
        {
            if (settings.getValue().equals("ASCII_TO_TEXT"))
            {
                setValues();
                settings.setValue("TEXT_TO_ASCII");
                toolbar.setTitle("TEXT to ASCII");
                text.setText("TEXT");
                ascii.setText("ASCII");
            }
            else if(settings.getValue().equals("TEXT_TO_ASCII"))
            {
                setValues();
                settings.setValue("ASCII_TO_TEXT");
                toolbar.setTitle("ASCII to TEXT");
                text.setText("ASCII");
                ascii.setText("TEXT");
            }
            return true;
        }
        if (id == R.id.action_info)
        {
            showInformation();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void showInformation()
    {
        new AlertDialog.Builder(this)
                .setTitle("    INFORMATION")
                .setMessage("This application translates ASCII to TEXT & TEXT to ASCII.\n\nIf you enter invalid ASCII numbers, nothing will be shown.\n\nThe text of the translation is selectable, so you can copy & paste it.")
                .setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {}
                })
                .setIcon(R.drawable.ic_information)
                .show();
    }
    public void setValues()
    {
        translate.setText("");
        translate.setHint("What to translate?");
        result.setText("");
        result.setHint("Result");
    }
}

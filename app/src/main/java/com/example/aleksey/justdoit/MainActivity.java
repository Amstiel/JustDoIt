package com.example.aleksey.justdoit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    String message;
    AlertDialog.Builder alert;
    TextView tw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alert = new AlertDialog.Builder(this);
        tw = (TextView) findViewById(R.id.textView);
    }


    public void addBtnClick(View view) {
        final EditText edittext = new EditText(MainActivity.this);
        alert.setTitle("Add ToDo item, please");

        alert.setView(edittext);

        alert.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                message = edittext.getText().toString();
                tw.setText(message.toString()); //Just a testing shit
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        alert.show();
    }
}

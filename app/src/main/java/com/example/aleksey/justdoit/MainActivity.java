package com.example.aleksey.justdoit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseListAdapter;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    String message;
    AlertDialog.Builder alert;
    AlertDialog.Builder alert1;
    TextView tw;
    Firebase mRef;
    FirebaseListAdapter<String> fireAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);
        mRef = new Firebase("https://amber-fire-3490.firebaseio.com");
        lv = (ListView) findViewById(R.id.listView);

        fireAdapter = new FirebaseListAdapter<String>(this, String.class, android.R.layout.simple_list_item_1, mRef) {
            @Override
            protected void populateView(View view, String s, int i) {
                TextView text = (TextView)view.findViewById(android.R.id.text1);
                text.setText(s + i);
            }
        };
        alert1 = new AlertDialog.Builder(this);
        alert = new AlertDialog.Builder(this);
        tw = (TextView) findViewById(R.id.textView);
        lv.setAdapter(fireAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                alert1.setTitle("Are you want to delete this item?");

                alert1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Firebase tmp = fireAdapter.getRef(position);
                        tmp.removeValue();
                    }
                });

                alert1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
                alert1.show();
            }
        });
    }

    public void addItem(String item){
        mRef.push().setValue(item);
    }

    public void addBtnClick(View view) {
        final EditText edittext = new EditText(MainActivity.this);
        alert.setTitle("Add ToDo item, please");

        alert.setView(edittext);

        alert.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (!edittext.getText().toString().isEmpty()) {
                    addItem(edittext.getText().toString());
                }
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        alert.show();
    }
}

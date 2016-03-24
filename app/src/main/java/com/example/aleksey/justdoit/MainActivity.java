package com.example.aleksey.justdoit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseListAdapter;
import com.melnykov.fab.FloatingActionButton;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    AlertDialog.Builder alertAdd;
    AlertDialog.Builder alertDelete;
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
                text.setText(s);
            }
        };

        alertAdd = new AlertDialog.Builder(this);
        alertDelete = new AlertDialog.Builder(this);

        lv.setAdapter(fireAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                alertDelete.setTitle(R.string.delete_item_message);

                alertDelete.setPositiveButton(R.string.delete_yes_buttom, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Firebase tmp = fireAdapter.getRef(position);
                        tmp.removeValue();
                    }
                });

                alertDelete.setNegativeButton(R.string.delete_no_buttom, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
                alertDelete.show();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.attachToListView(lv);

        View.OnClickListener listener= new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText edittext = new EditText(MainActivity.this);
                alertAdd.setTitle(R.string.add_item_message);

                alertAdd.setView(edittext);

                alertAdd.setPositiveButton(R.string.add_yes_buttom, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (!edittext.getText().toString().isEmpty()) {
                            addItem(edittext.getText().toString());
                        }
                    }
                });

                alertAdd.setNegativeButton(R.string.add_no_buttom, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
                alertAdd.show();
            }
        };
        fab.setOnClickListener(listener);
    }

    public void addItem(String item){
        mRef.push().setValue(item);
    }
}

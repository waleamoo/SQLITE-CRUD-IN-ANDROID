package com.example.pc.sqliteapp;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // interface variables
    EditText et_name, et_surname, et_marks, et_id;
    Button bt_insert, bt_view, bt_update, bt_delete;

    // declare variables
    DatabaseHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // code starts here
        myDB = new DatabaseHelper(this);

        // initialize the interface variables
        et_id = (EditText)findViewById(R.id.et_id);
        et_name = (EditText)findViewById(R.id.et_name);
        et_surname = (EditText)findViewById(R.id.et_surname);
        et_marks = (EditText)findViewById(R.id.et_mark);
        bt_insert = (Button)findViewById(R.id.bt_insert);
        bt_view = (Button)findViewById(R.id.bt_view);
        bt_update = (Button)findViewById(R.id.bt_update);
        bt_delete = (Button)findViewById(R.id.bt_delete);
    }

    // method to insert the data at the click of the button
    public void InsertData(View v){
        boolean isInserted = myDB.insertData(et_name.getText().toString(), et_surname.getText().toString(), et_marks.getText().toString());

        // if the data is inserted successfully
        if(isInserted){
            Toast.makeText(getApplicationContext(), "Data inserted.", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(), "Data insertion failure.", Toast.LENGTH_LONG).show();
        }
    }

    // method to retrieve all the data from the database
    public void ViewAllData(View v){
        Cursor res = myDB.viewAllData();
        // if there are no data
        if (res.getCount() == 0) {
            // show an error message
            showMessage("Error", "Nothing found");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while(res.moveToNext()){
            buffer.append("Id :" + res.getString(0) + "\n");
            buffer.append("Name :" + res.getString(1) + "\n");
            buffer.append("Surname :" + res.getString(2) + "\n");
            buffer.append("Marks :" + res.getString(3) + "\n\n");
        }

        // then show all the data inside the buffer
        showMessage("Data", buffer.toString());
    }

    // method to show the alert dialog that show data from the database
    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    // method to update the data at the click of the update button
    public void UpdateData(View v){
        boolean isUpdated = myDB.updateData(et_id.getText().toString(),
                et_name.getText().toString(),
                et_surname.getText().toString(),
                et_marks.getText().toString());
        if(isUpdated){
            // show update message
            Toast.makeText(getApplicationContext(), "Update successful.", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(), "Update failure.", Toast.LENGTH_LONG).show();
        }
    }

    public void DeleteData(View v){
        Integer deletedRows = myDB.DeleteData(et_id.getText().toString());
        if(deletedRows > 0){
            // show update message
            Toast.makeText(getApplicationContext(), "Data Deleted.", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(), "Delete failure.", Toast.LENGTH_LONG).show();
        }
    }
}

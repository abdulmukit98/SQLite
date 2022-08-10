package edu.cseju.example;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import edu.cseju.example.model.DBHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtName, edtContact, edtDOB;
    Button btnInsert, btnUpdate, btnDelete, btnView;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtName = findViewById(R.id.edtName);
        edtContact = findViewById(R.id.edtContact);
        edtDOB = findViewById(R.id.edtDob);

        btnUpdate = findViewById(R.id.btnUpdate);
        btnInsert = findViewById(R.id.btnInsert);
        btnDelete = findViewById(R.id.btnDelete);
        btnView = findViewById(R.id.btnView);

        dbHelper = new DBHelper(getApplicationContext());

        btnInsert.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnInsert:
                insertData();
                break;
            case R.id.btnUpdate:
                updateData();
                break;
            case R.id.btnDelete:
                deleteData();
                break;
            case R.id.btnView:
                viewData();
                break;
        }
    }

    private void insertData() {
        String name = edtName.getText().toString().trim();
        String contact = edtContact.getText().toString().trim();
        String dob = edtDOB.getText().toString().trim();

        boolean checkInsert = dbHelper.insertUserData(name, contact, dob);
        if (checkInsert == true)
            Toast.makeText(this, "Inserted", Toast.LENGTH_SHORT).show();
        else Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();

        edtName.setText("");
        edtContact.setText("");
        edtDOB.setText("");
    }

    private void updateData() {
        String name = edtName.getText().toString().trim();
        String contact = edtContact.getText().toString().trim();
        String dob = edtDOB.getText().toString().trim();

        boolean checkUpdate = dbHelper.updateUserData(name, contact, dob);
        if (checkUpdate == true)
            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
        else Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show();

        edtName.setText("");
        edtContact.setText("");
        edtDOB.setText("");
    }

    private void deleteData() {
        String name = edtName.getText().toString().trim();
        boolean checkDelete = dbHelper.deleteUserData(name);
        if (checkDelete == true)
            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
        else Toast.makeText(this, "Failed to delete", Toast.LENGTH_SHORT).show();

        edtName.setText("");
    }

    private void viewData() {
        Cursor cursor = dbHelper.getData();
        if (cursor.getCount() == 0)
        {
            Toast.makeText(this, "No Entry Exists", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuffer stringBuffer = new StringBuffer();
        while (cursor.moveToNext())
        {
            stringBuffer.append("Name: " + cursor.getString(0) + "\n");
            stringBuffer.append("Contact: "+ cursor.getString(1)+ "\n");
            stringBuffer.append("Date of Birth: " + cursor.getString(2) + "\n\n");
        }

        System.out.println(stringBuffer);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("User Entries");
        builder.setCancelable(true);
        builder.setMessage(stringBuffer.toString());
        builder.show();
    }


}
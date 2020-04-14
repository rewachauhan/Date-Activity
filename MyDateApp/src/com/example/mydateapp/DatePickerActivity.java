package com.example.mydateapp;

import java.util.ArrayList;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class DatePickerActivity extends Activity {
	EditText et;
	ListView lv1;
	ImageView ib;
	DatePicker datep;
    @SuppressLint("NewApi") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);
        final SQLiteDatabase mydatabase=openOrCreateDatabase("mydb.dat",MODE_PRIVATE, null);
        String sql= "CREATE TABLE IF NOT EXISTS datestore(dId VARCHAR,dDate VARCHAR);";
        lv1=(ListView) findViewById(R.id.listView1);
        ib=(ImageView) findViewById(R.id.imageView1);
        et=(EditText) findViewById(R.id.editText1);
        et.setEnabled(false);
        datep=(DatePicker) findViewById(R.id.datePicker1);
        datep.setVisibility(View.INVISIBLE);
        String fetchSql= "SELECT dDate FROM datastore;";
        Cursor c = mydatabase.rawQuery(fetchSql, null);
        ArrayList<String> arraylist = new ArrayList<String>();
        c.moveToFirst();
        if(c.getCount()>0)
        {
        	do{
        		arraylist.add(c.getString(0));
        		c.moveToNext();
        	}while(!c.isAfterLast());
        	ArrayAdapter<String> itemadapter =new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,arraylist);
        	lv1.setAdapter(itemadapter);
        }
        else{
        	Toast.makeText(getApplicationContext(),"hey", Toast.LENGTH_LONG).show();
        }
        
        ib.setOnClickListener(new OnClickListener() {
			
			@SuppressLint("NewApi") @Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				datep.setVisibility(View.VISIBLE);
			}
		});
        datep.getCalendarView().setOnDateChangeListener(new OnDateChangeListener() {
			
			@Override
			public void onSelectedDayChange(CalendarView arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				String printdate= datep.getDayOfMonth()+ " / " + (datep.getMonth());
				String printDataWithArg = arg3 + "/" +(arg2+1)+ "/" + arg1;
				et.setText(printDataWithArg);
				datep.setVisibility(View.INVISIBLE);
			}
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.date_picker, menu);
        return true;
    }
    
}

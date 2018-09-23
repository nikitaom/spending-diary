package com.example.nikita.spendingdiary;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.StrictMath.round;

public class MainActivity extends AppCompatActivity {

    Button buttonIncome;
    Button buttonSpends;
    TextView balanse;
    DBHelper dbHelper;
    double totalBalanse = 0;

    public void btnClick(){
                buttonIncome= (Button) findViewById(R.id.incomeBtn);
        buttonIncome.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,Income.class);
                    startActivity(intent);
                    finish();
            }
        });
        buttonSpends =(Button) findViewById(R.id.spendsBtn);
        buttonSpends.setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this,Spends.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        balanse = (TextView) findViewById(R.id.balanse);
        dbHelper = new DBHelper(this);
        shovBalanse();
        btnClick();
    }

    private HashMap<String, Integer> getArr() {
        HashMap<String, Integer> tab = new HashMap<>();
         SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor c =null;
        c = database.rawQuery("SELECT * FROM spends", null);
        if(c.moveToFirst()) {
            for (int i = 0; i < c.getCount(); i++) {
                int amount = c.getColumnIndex(DBHelper.AMOUNT);
                int switchIndex = c.getColumnIndex(DBHelper.TYPE);
                tab.put(c.getString(amount), (c.getInt(switchIndex)));
                // System.out.println(c.getString(idIndex)+"&"+c.getString(amountIndex)+"&"+c.getString(dateIndex)+"&"+c.getString(timeIndex)+"&"+c.getString(categoryIndex));
                c.moveToNext();
            }
            c.close();

        }
        //database.close();
        return tab;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void shovBalanse(){
        balanse = (TextView) findViewById(R.id.balanse);
        HashMap<String,Integer> hm = getArr();
        hm.forEach((k,v) -> {
            if(v==0)
                totalBalanse += Double.parseDouble(k)*(-1);
            if(v==1)
                totalBalanse += Double.parseDouble(k);
        });
        if(totalBalanse<=0)
            balanse.setTextColor(Color.RED);
        else
            balanse.setTextColor(Color.parseColor("#33cc33"));
        DecimalFormat df = new DecimalFormat("####0.00");
        balanse.setText( String.valueOf(df.format(totalBalanse))+" $");
    }



}

package com.example.nikita.spendingdiary;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Spends extends AppCompatActivity {
    DBHelper dbHelper;
    LinearLayout ml;
    Button add;
    TabLayout tl;
    ArrayList<Integer> hm = new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spends);
        ml = (LinearLayout) findViewById(R.id.myLinerLayout);
        dbHelper = new DBHelper(this);
        tl = (TabLayout) findViewById(R.id.tabLay);
        getAll();
        final int amount = ml.getChildCount();
        tl.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

              //  hm = getArr("SELECT * FROM spends");
                if(tl.getTabAt(0).isSelected()){
                    hm = getArr("SELECT * FROM spends");
                   for(int i =0;i<hm.size();i++){
                       ml.getChildAt(i).setVisibility(View.VISIBLE);
                   }

                    for (Integer s:hm
                            ) {
                        System.out.println(s);
                    }
                }
                if(tl.getTabAt(1).isSelected()){
                    hm = getArr("SELECT * FROM spends");
                    for (int i =0;i<hm.size();i++) {

                        if(hm.get(i) == 0){
                            ml.getChildAt(i).setVisibility(View.VISIBLE);

                        }
                        if(hm.get(i) == 1){
                            ml.getChildAt(i).setVisibility(View.GONE);
                        }
                    }

                    for (Integer s:hm
                            ) {
                        System.out.println(s);
                    }
                 }

                if(tl.getTabAt(2).isSelected()){
                    hm = getArr("SELECT * FROM spends");
                for (int i =0;i<hm.size();i++) {
                    if(hm.get(i) == 0){
                        ml.getChildAt(i).setVisibility(View.GONE);
                    }
                    if(hm.get(i) == 1){
                        ml.getChildAt(i).setVisibility(View.VISIBLE);
                    }
                }
                }

                for (Integer s:hm
                     ) {
                    System.out.println(s);
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
      }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(Spends.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onClickDelete(View button, final int id, final Card card){
        final SQLiteDatabase database ;
        database = dbHelper.getReadableDatabase();
        button.setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        database.delete(DBHelper.TABLE_SPENDS, "_id=?", new String[]{Integer.toString(id)});
                        System.out.println("DELETE");
                        card.getLl().removeView(card.getCard());
                        //hm = getArr("SELECT * FROM spends");

                    }
                });
        //database.close();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void getAll(){
        ArrayList<String> spendEntities = getResults();
        ArrayList<SpendEntity> spendEntList = new ArrayList<>();
        SpendEntity[] arrSP = new SpendEntity[spendEntities.size()];
        Boolean boo;
        for (int i =0;i<spendEntities.size();i++){
            String[] arrObj   = spendEntities.get(i).split("///");
            arrSP[i]=(new SpendEntity(Integer.parseInt(arrObj[0]),arrObj[1],arrObj[2],arrObj[3],arrObj[4],Integer.parseInt(arrObj[5])));
            System.out.println(arrObj[5]+"----------------------");
            if(arrObj[5].toString().equals("1")) boo = true;
            else boo = false;
            Card c = new Card(ml);
            c.setId(Integer.parseInt(arrObj[0]));
            c.createCard(getBaseContext(),boo, arrObj[1],arrObj[2],arrObj[3],arrObj[4]);
            onClickDelete( c.buttonDelete,c.getId(),c);
            onClickEdit( c.buttonEdit,c.getId(),c);
        }
    }

    public void onClickEdit(final View button, final int id,final Card card){
       button.setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                      Intent intent = new Intent(Spends.this,Income.class);
                      intent.putExtra("id",String.valueOf(id));
                        //System.out.println(intent.getStringExtra("id"));
                      startActivity(intent);
                       // System.out.println(id+"-------------"+intent.getStringExtra("id"));
                        finish();
                    }
                });
    }

    private ArrayList<String> getResults() {
        ArrayList<String> tab = new ArrayList<String>();
        final SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor c =null;
        c = database.rawQuery("SELECT * FROM spends INNER JOIN categoryTable ON spends.spends_id = categoryTable._id_cat", null);
        if(c.moveToFirst()) {/* WHERE category='"+cat+"'*/
            for (int i = 0; i < c.getCount(); i++) {
                int idIndex = c.getColumnIndex(DBHelper.ID);
                int amountIndex = c.getColumnIndex(DBHelper.AMOUNT);
                int timeIndex = c.getColumnIndex(DBHelper.TIME);
                int dateIndex = c.getColumnIndex(DBHelper.DATE);
                int categoryIndex = c.getColumnIndex(DBHelper.CATEGORY);
                int switchIndex = c.getColumnIndex(DBHelper.TYPE);
                tab.add(c.getString(idIndex)+"///"+c.getString(amountIndex)+"///"+c.getString(dateIndex)+"///"+
                        c.getString(timeIndex)+"///"+c.getString(categoryIndex)+"///"+(c.getInt(switchIndex)));
               // System.out.println(c.getString(idIndex)+"&"+c.getString(amountIndex)+"&"+c.getString(dateIndex)+"&"+c.getString(timeIndex)+"&"+c.getString(categoryIndex));
                c.moveToNext();
            }
            c.close();
        }
       // database.close();
        return tab;
    }

    private ArrayList<Integer> getArr(String s) {
        ArrayList<Integer> tab = new ArrayList<>();
        final SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor c =null;
        c = database.rawQuery(s, null);
        if(c.moveToFirst()) {
            for (int i = 0; i < c.getCount(); i++) {
                int idIndex = c.getColumnIndex(DBHelper.ID);
                int switchIndex = c.getColumnIndex(DBHelper.TYPE);
                tab.add((c.getInt(switchIndex)));
                // System.out.println(c.getString(idIndex)+"&"+c.getString(amountIndex)+"&"+c.getString(dateIndex)+"&"+c.getString(timeIndex)+"&"+c.getString(categoryIndex));
                c.moveToNext();
            }
            c.close();

        }
        //database.close();
        return tab;
    }
}

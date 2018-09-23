package com.example.nikita.spendingdiary;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Income extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    TextView currentDate;
    TextView currentTime;
    EditText etammount;
    EditText newCategory;
    Button buttonAdd;
    Button buttonDate;
    Button buttonTime;
    Button buttonaddCategory;
    Button buttonNCOk;
    Button buttonTestShow;
    Calendar dateAndTime=Calendar.getInstance();
    DBHelper dbHelper;
    Spinner spinnerCategory;
    ArrayList<String> spc;
    Switch switchType;
    int checked =0;
    //Intent intent;

    private ArrayList<String> category  ;// = new ArrayList<String>(){{add("Products"); add("Funs"); add("FastFood");}};

    @Override
    public void onCreate(Bundle savedInstance) {
        dbHelper = new DBHelper(this);
        final SQLiteDatabase database = dbHelper.getWritableDatabase();
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_income);
        currentDate=(TextView)findViewById(R.id.textViewDate);
        currentTime =(TextView) findViewById(R.id.textViewTime) ;
        etammount = (EditText) findViewById(R.id.editTextAmmount);
        newCategory = (EditText) findViewById(R.id.editTextNewCategory);
        spinnerCategory = (Spinner) findViewById(R.id.spinnerCategory);

        switchType = (Switch) findViewById(R.id.switchType);
        switchType.setOnCheckedChangeListener(this);

        Intent intent = getIntent();

        buttonAdd = (Button) findViewById(R.id.buttonAdd);///////////////
        buttonAdd.setOnClickListener(this);

        buttonTestShow = (Button) findViewById(R.id.buttonTestShow);///////////
        buttonTestShow.setOnClickListener(this);

        buttonDate =(Button) findViewById(R.id.buttonDate);
        buttonDate.setOnClickListener(this);

        buttonTime =(Button)findViewById(R.id.buttonTime);
        buttonTime.setOnClickListener(this);

        buttonaddCategory =(Button) findViewById(R.id.buttonAddCategory);
        buttonaddCategory.setOnClickListener(this);

        buttonNCOk = (Button) findViewById(R.id.buttonNCOk);
        buttonNCOk.setOnClickListener(this);

        setInitialDateTime();
       // ArrayList<String> catSP = setSpinnerCategory();
        category = setSpinnerCategory();
        spnCategory(category);
        }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(Income.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(switchType.isChecked()){
            checked = 1;
        }
        else checked = 0;
    }

    @Override
    public void onClick(View v) {
        dbHelper = new DBHelper(this);
        String ammountT = etammount.getText().toString();
        String date = currentDate.getText().toString();
        String time = currentTime.getText().toString();
        String categorySelected = spinnerCategory.getSelectedItem().toString();
        final SQLiteDatabase database = dbHelper.getWritableDatabase();
        final ContentValues contentValues = new ContentValues();

        //Integer intRes ;
        switch (v.getId()) {
            case R.id.buttonAdd:
               int categoryId = getCategoryId(categorySelected);
                String s;
                Intent intent = getIntent();
                    if(intent!=null){
                        s = intent.getStringExtra("id");
                    }
                    else{
                        s="";
                    }
                //System.out.println(intent.getStringExtra("id").toString());
                if(s ==null) System.out.println("INTENT NULL");
                else System.out.println("INTENT +++++");
                if(!ammountT.equals("")){
                    if(s ==null){
                contentValues.put(DBHelper.AMOUNT, ammountT);
                contentValues.put(DBHelper.TIME, time);
                contentValues.put(DBHelper.DATE, date);
                contentValues.put(DBHelper.FK_ID, categoryId);
                contentValues.put(DBHelper.TYPE, checked);
                database.insert(DBHelper.TABLE_SPENDS,null,contentValues);
                    }
                    else{
                        /*Edit code*/
                        System.out.println(s);
                        contentValues.put(DBHelper.AMOUNT, ammountT);
                        contentValues.put(DBHelper.TIME, time);
                        contentValues.put(DBHelper.DATE, date);
                        contentValues.put(DBHelper.FK_ID, categoryId);
                        contentValues.put(DBHelper.TYPE, checked);
                        database.update(dbHelper.TABLE_SPENDS, contentValues, "_id = ?",
                                new String[] { s });
                        finish();
                        Intent intent1 = new Intent(Income.this,Spends.class);
                        startActivity(intent1);
                    }
                 }
                else{
                    Toast.makeText(getBaseContext(), R.string.messageEditTextIsEmpty, Toast.LENGTH_LONG).show();
                }
                break;
                //****************************************************************************************
            //
            //
            ///
            ///
            case R.id.buttonTestShow:
               Cursor cursor = database.rawQuery("SELECT * FROM spends INNER JOIN categoryTable ON spends.spends_id = categoryTable._id_cat",null);
                if (cursor.moveToFirst()) {
                    int idIndex = cursor.getColumnIndex(DBHelper.ID);
                    int amountIndex = cursor.getColumnIndex(DBHelper.AMOUNT);
                    int timeIndex = cursor.getColumnIndex(DBHelper.TIME);
                    int dateIndex = cursor.getColumnIndex(DBHelper.DATE);
                    int categoryIndex = cursor.getColumnIndex(DBHelper.CATEGORY);
                    int switchIndex = cursor.getColumnIndex(DBHelper.TYPE);
                     do {
                        Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                                ", amount = " + cursor.getString(amountIndex)+
                                ", date = " + cursor.getString(dateIndex)+
                                ", time = " + cursor.getString(timeIndex)+
                                ", category = " + cursor.getString(categoryIndex)+
                                ", type = " + cursor.getInt(switchIndex));
                    } while (cursor.moveToNext());
                } else
                    Log.d("mLog","0 rows");
                cursor.close();
                Cursor cursor1 = database.query(DBHelper.TABLE_CATEGORY, null,null,null, null, null, null);
                if (cursor1.moveToFirst()) {
                    int idIndex = cursor1.getColumnIndex(DBHelper.ID_CAT);
                    int categoryIndex = cursor1.getColumnIndex(DBHelper.CATEGORY);
                    do {

                        Log.d("mLog", "ID = " + cursor1.getInt(idIndex) +
                               ", category = " + cursor1.getString(categoryIndex) );
                    } while (cursor1.moveToNext());
                } else
                    Log.d("mLog","0 rows");
                cursor1.close();

                spc = setSpinnerCategory();
                for (String st:spc
                     ) {
                    System.out.println(st+" -----CATEGORY");
                }
//                Cursor cursorQ2Q = database.rawQuery("select * from categoryTable where category = FastFood",null);
//                cursorQ2Q.moveToFirst();
//                intRes = cursorQ2Q.getInt(cursorQ2Q.getColumnIndex(DBHelper.ID_CAT));
//                System.out.println(intRes.toString());
//                System.out.println(categorySelected.toString());
//                cursorQ2Q.close();*/
//               /* ArrayList<String> spendEntities = getResults("Funs");
//
//                ArrayList<SpendEntity> spendEntList = new ArrayList<>();
//                SpendEntity[] arrSP = new SpendEntity[spendEntities.size()];
//
//                for (int i =0;i<spendEntities.size();i++) {
//                    String[] arrObj = spendEntities.get(i).split("///");
//
//                    arrSP[i] = (new SpendEntity(Integer.parseInt(arrObj[0]), arrObj[1], arrObj[2], arrObj[3], arrObj[4]));
//                    System.out.println(arrSP[0].toString());
//                    //spendEntList.add(spendEntity);
//                    //spendEntity =new SpendEntity(Integer.parseInt(arrObj[0]),arrObj[1],arrObj[2],arrObj[3],arrObj[4]);
//                    //spendEntList.add(spendEntity);
//                }
//
//                database.delete(DBHelper.TABLE_SPENDS, "_id=?", new String[]{Integer.toString(1)});
                break;
            case R.id.buttonDate:
                setDate(v);
                break;
            case R.id.buttonTime:
                setTime(v);
                //System.out.println(spinnerCategory.getSelectedItem().toString());
                break;
            case R.id.buttonAddCategory:
                newCategory.setVisibility(View.VISIBLE);
                buttonNCOk.setVisibility(View.VISIBLE);
                openKeyboardFrom(getBaseContext(),v);
                break;
            case R.id.buttonNCOk:
                newCategory.setVisibility(View.INVISIBLE);
                buttonNCOk.setVisibility(View.INVISIBLE);
                hideKeyboardFrom(getBaseContext(),v);

//               /* boolean exist = false;
//                for (int i =0;i<category.size();i++){
//                    if(existCategory(newCategory.getText().toString())){
//                        exist = true;
//                    }
//                }*/
                if((newCategory.getText().toString().length() > 0)&&(!existCategory(newCategory.getText().toString()))) {
                    //category.add(newCategory.getText().toString());
                    //Toast.makeText(getBaseContext(),newCategory.getText().toString(), Toast.LENGTH_LONG).show();
                    contentValues.put(DBHelper.CATEGORY, newCategory.getText().toString());
                    database.insert(DBHelper.TABLE_CATEGORY,null,contentValues);
                    
                }
                else {
                    if(existCategory(newCategory.getText().toString())) Toast.makeText(getBaseContext(), R.string.messageElementExist, Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getBaseContext(), R.string.messageEditTextIsEmpty, Toast.LENGTH_LONG).show();
                }
                newCategory.setText("");
                category = setSpinnerCategory();
                spnCategory(category);
                break;
        }
    }
    //**********************************************************************************************
    public int getCategoryId(String category){
        int idr =0;
        final SQLiteDatabase database ;
        database = dbHelper.getReadableDatabase();
        Cursor c = database.rawQuery("select "+DBHelper.ID_CAT+" from "+ DBHelper.TABLE_CATEGORY+" where "
                + DBHelper.CATEGORY+" = '"+category+"'",null);
        if (c.moveToFirst()){
            do{
                //if you not need the loop you can remove that
                idr  = c.getInt(c.getColumnIndex(DBHelper.ID_CAT));
            }
            while(c.moveToNext());
        }c.close();

        return idr;
    }


    public ArrayList<String> setSpinnerCategory(){
        ArrayList<String> spinnerlist = new ArrayList<>();
       /* final SQLiteDatabase database1 ;
        database1 = dbHelper.getReadableDatabase();*/
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cc = database.rawQuery("select * from "+ DBHelper.TABLE_CATEGORY+"",null);
        if (cc.moveToFirst()){
            do{
                spinnerlist.add(cc.getString(cc.getColumnIndex(DBHelper.CATEGORY)));
            }
            while(cc.moveToNext());
        }cc.close();

        return spinnerlist;
    }

    public boolean existCategory(String catEx){
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String categoryIn ="";
        Cursor c = database.rawQuery("select category from "+ DBHelper.TABLE_CATEGORY+" where category = '" +catEx+"'",null);
        if (c.moveToFirst()){
            do{
                categoryIn = c.getString(c.getColumnIndex(DBHelper.CATEGORY));
            }
            while(c.moveToNext());
                }c.close();
        if(catEx.equals(categoryIn))return true;
        else
        return false;
    }

//**************************************************************************************************
//***********************NOT USED*******************************************************************
    SpendEntity spendEntityOb;
    public void getSelectedCategory(String categorySelected/**/){
        String strCat;
        final SQLiteDatabase database ;
        database = dbHelper.getReadableDatabase();
        Cursor c1 = database.rawQuery("select "+DBHelper.CATEGORY+" from "+ DBHelper.TABLE_CATEGORY+" where "+ DBHelper.ID_CAT+" = '"+getCategoryId(categorySelected)+"'",null);
        if (c1.moveToFirst()){
            do{
                //if you not need the loop you can remove that
                strCat  = c1.getString(c1.getColumnIndex(DBHelper.CATEGORY));
            }
            while(c1.moveToNext());
        }c1.close();

    }
    public List <SpendEntity> getIdentifiantProduct(String categoryOfSpend) {
        ArrayList <SpendEntity> tab = new ArrayList <>();
        final SQLiteDatabase database ;
        database = dbHelper.getReadableDatabase();
        List<String> stringList = new ArrayList<>();
        int count = 0;
        List<SpendEntity> finallist= new ArrayList<>();
        SpendEntity[] spendEntities;
        Cursor c =null;
        c = database.rawQuery("SELECT * FROM spends WHERE category='"+categoryOfSpend+"'", null);
        if(c.moveToFirst()) {
            spendEntities = new SpendEntity[c.getCount()];
            for (int i = 0; i < c.getCount(); i++) {
                count++;
              /*  spendEntityOb = new SpendEntity(Integer.parseInt(c.getString(c.getColumnIndex(DBHelper.ID))),
                        c.getString(c.getColumnIndex(DBHelper.AMOUNT)),
                        c.getString(c.getColumnIndex(DBHelper.TIME)),
                        c.getString(c.getColumnIndex(DBHelper.DATE)),
                        c.getString(c.getColumnIndex(DBHelper.CATEGORY)));*/
                tab.add(spendEntityOb);
                c.moveToNext();
            }
            c.close();
            database.close();
        }
        return tab;
    }
    public List<SpendEntity>  getElementsByCategory (String categoryOfSpend){
            final SQLiteDatabase database ;
            database = openOrCreateDatabase(dbHelper.getDatabaseName(), 0, null);
            Cursor cursorc = database.rawQuery("SELECT * FROM spends WHERE category='"+categoryOfSpend+"'", null);
            cursorc.moveToFirst();

            List<SpendEntity> spend = new ArrayList<>();
             if(cursorc!=null&&cursorc. moveToFirst()){
                do{
                    int idIndex = cursorc.getColumnIndex(DBHelper.ID);
                    int amountIndex = cursorc.getColumnIndex(DBHelper.AMOUNT);
                    int timeIndex = cursorc.getColumnIndex(DBHelper.TIME);
                    int dateIndex = cursorc.getColumnIndex(DBHelper.DATE);
                    int categoryIndex = cursorc.getColumnIndex(DBHelper.CATEGORY);

                    SpendEntity spendEntity= new SpendEntity();
                    spendEntity.setId(Integer.parseInt(cursorc.getString(idIndex)));
                    spendEntity.setAmmount(cursorc.getString(amountIndex));
                    spendEntity.setDate(cursorc.getString(dateIndex));
                    spendEntity.setTime(cursorc.getString(timeIndex));
                    spendEntity.setCategory(cursorc.getString(categoryIndex));
                    //System.out.println(spendEntity.toString() + "in metgod");
                    spend.add(spendEntity);
                    //System.out.println(spend.indexOf(spend.size()-1));
                    //spend.add(spend.size(),spendEntity);

                }while(cursorc.moveToNext());
            }
        System.out.println(spend.size());
            cursorc.close();
            for (SpendEntity sp:spend
                 ) {
                System.out.println(sp.toString());
            }
            database.close();
            return spend;
        }
    public ArrayList <Integer> getIdent(String cat)    {
        ArrayList <Integer> tab = new ArrayList <Integer>();
        final SQLiteDatabase database = dbHelper.getReadableDatabase();


            Cursor c =null;
            c = database.rawQuery("SELECT " + dbHelper.ID + " FROM "
                    + dbHelper.TABLE_SPENDS + " WHERE " + dbHelper.CATEGORY + "=  '" + cat + "'", null);
            if(c.moveToFirst()){

                for(int i=0;i<c.getCount();i++)
                {
                    tab.add(c.getInt(c.getColumnIndex(dbHelper.ID)));
                    c.moveToNext();
                }
                c.close();
                database.close();
            }
        return tab;
    }
    private   ArrayList<String> getResults(String cat) {
       ArrayList<String> tab = new ArrayList<String>();
        final SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor c =null;
        c = database.rawQuery("SELECT * FROM spends WHERE category='"+cat+"'", null);
        if(c.moveToFirst()) {
            for (int i = 0; i < c.getCount(); i++) {
                int idIndex = c.getColumnIndex(DBHelper.ID);
                int amountIndex = c.getColumnIndex(DBHelper.AMOUNT);
                int timeIndex = c.getColumnIndex(DBHelper.TIME);
                int dateIndex = c.getColumnIndex(DBHelper.DATE);
                int categoryIndex = c.getColumnIndex(DBHelper.CATEGORY);



                tab.add(c.getString(idIndex)+"///"+c.getString(amountIndex)+"///"+c.getString(dateIndex)+"///"+c.getString(timeIndex)+"///"+c.getString(categoryIndex));
                System.out.println(c.getString(idIndex)+"&"+c.getString(amountIndex)+"&"+c.getString(dateIndex)+"&"+c.getString(timeIndex)+"&"+c.getString(categoryIndex));

                c.moveToNext();
            }
            c.close();
            database.close();
        }
        return tab;
    }

//**************************************************************************************************
    public void spnCategory(ArrayList<String> cat){
        //Устанавливаем выпадающему меню список сategory как список
        //ArrayList<String> categoryFromDb = setSpinnerCategory();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cat);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinnerCategory.setAdapter(adapter);
        // заголовок
        spinnerCategory.setPrompt("Title");
        // выделяем элемент
        spinnerCategory.setSelection(2);
        // устанавливаем обработчик нажатия
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
               // Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }
    // отображаем диалоговое окно для выбора даты
    public void setDate(View v) {
        new DatePickerDialog(Income.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }
    // отображаем диалоговое окно для выбора времени
    public void setTime(View v) {
        new TimePickerDialog(Income.this, t,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), false)
                .show();
    }
    // установка начальных даты и времени
    private void setInitialDateTime() {
        currentDate.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
        currentTime.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                      DateUtils.FORMAT_SHOW_TIME));
    }
    // установка обработчика выбора времени
    TimePickerDialog.OnTimeSetListener t=new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            setInitialDateTime();
        }
    };
    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };
    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public static void openKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }
}

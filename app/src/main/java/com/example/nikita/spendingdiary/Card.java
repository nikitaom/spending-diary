package com.example.nikita.spendingdiary;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.Time;
import java.util.Date;

public class Card {
    CardView card;

    TextView title;
    TextView amount;
    TextView category;
    TextView dataAndTime;

    int id;
    String time;
    String date;
    String ammount;
    String catedort;

    View buttonEdit;
    View buttonDelete;

    View separator;
    View separatorBottom;
    View lineTop;
    LinearLayout ll;
    LinearLayout innerll;
    LinearLayout linearLayoutHorizontalTop;
    LinearLayout linearLayoutHorizontalBottom;
    Boolean bb;
    int [] gradientRed = {Color.parseColor("#df6a57"), Color.parseColor("#ffffff")};
    int [] gradientGreen = {Color.parseColor("#5DD497"), Color.parseColor("#ffffff")};


    public Card(LinearLayout ll) {
        this.ll = ll;
    }

    public Card(int id, String time, String date, String ammount, String catedort, LinearLayout ll) {
        this.id = id;
        this.time = time;
        this.date = date;
        this.ammount = ammount;
        this.catedort = catedort;
        this.ll = ll;
    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void createCard(Context c, boolean b, String amount,String date,String time,String category1) {
       // SimpleDateFormat dateFormat = new SimpleDateFormat();
        bb = b;
        card = new CardView(c);
        lineTop = new View(c);
        buttonEdit = new View(c);
        buttonDelete = new View(c);
        buttonEdit.setBackgroundResource(R.drawable.edit);
        buttonDelete.setBackgroundResource(R.drawable.delete);
        buttonEdit.setClickable(true);
        buttonDelete.setClickable(true);
        dataAndTime = new TextView(c);
        dataAndTime.setText(date+" "+time);
        dataAndTime.setTextSize(12);
        dataAndTime.setTextColor(Color.LTGRAY);
        category = new TextView(c);
        category.setText(category1);
        category.setBackgroundResource(R.drawable.rect);
        category.setTextColor(Color.parseColor("#efefef"));
        LinearLayout.LayoutParams categoryParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        categoryParams.setMargins(10, 10, 10, 10);
        category.setPadding(10, 0, 10, 10);
        ViewGroup.MarginLayoutParams buttonMargin = new ViewGroup.MarginLayoutParams(64, 64);
        buttonMargin.setMargins(20, 20, 0, 20);
        lineTop.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 10));
        linearLayoutHorizontalTop = new LinearLayout(c);
        linearLayoutHorizontalTop.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayoutHorizontalBottom = new LinearLayout(c);
        linearLayoutHorizontalBottom.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        card.addView(lineTop);
        GradientDrawable gradientDrawableRed = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, gradientRed);
        GradientDrawable gradientDrawableGreen = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, gradientGreen);
        title = new TextView(c);
        if(bb) {
            title.setText("Доход");
            title.setTextColor(Color.LTGRAY);
            lineTop.setBackground(gradientDrawableGreen);
        } else {
            title.setText("Расход");
            title.setTextColor(Color.LTGRAY);
            lineTop.setBackground(gradientDrawableRed);
        }
        LinearLayout.LayoutParams lpTitle = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lpTitle.gravity = Gravity.CENTER_VERTICAL;
        LinearLayout.LayoutParams lpAmount = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lpAmount.setMargins(20, 20, 0, 20);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 300);
        lpTitle.setMargins(20, 10, 0, 10);
        this.amount = new TextView(c);
        this.amount.setText(amount);
        this.amount.setTextSize(18);
        separator = new View(c);
        separatorBottom = new View(c);
        innerll = new LinearLayout(c);
        innerll.setOrientation(LinearLayout.VERTICAL);
        separator.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
        separator.setBackgroundColor(Color.LTGRAY);
        separatorBottom.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
        separatorBottom.setBackgroundColor(Color.LTGRAY);
        card.setCardBackgroundColor(Color.WHITE);
        card.setCardElevation(10);
        ll.addView(card, lp);
        card.addView(innerll, lp);
        innerll.addView(linearLayoutHorizontalTop);
        linearLayoutHorizontalTop.addView(title, lpTitle);
        linearLayoutHorizontalTop.addView(buttonEdit, buttonMargin);
        linearLayoutHorizontalTop.addView(buttonDelete, buttonMargin);
        innerll.addView(separator);
        innerll.addView(this.amount, lpAmount);
        innerll.addView(separatorBottom);
        innerll.addView(linearLayoutHorizontalBottom);
        linearLayoutHorizontalBottom.addView(dataAndTime, lpTitle);
        if(!bb) linearLayoutHorizontalBottom.addView(category, categoryParams);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) card.getLayoutParams();
        layoutParams.setMargins(15, 15, 15, 0);
        card.requestLayout();


    }

    public Boolean getBb() {
        return bb;
    }

    public void setBb(Boolean bb) {
        this.bb = bb;
    }

    public CardView getCard() {
        return card;
    }

    public void setCard(CardView card) {
        this.card = card;
    }

    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    public TextView getAmount() {
        return amount;
    }

    public void setAmount(TextView amount) {
        this.amount = amount;
    }

    public TextView getCategory() {
        return category;
    }

    public void setCategory(TextView category) {
        this.category = category;
    }

    public TextView getDataAndTime() {
        return dataAndTime;
    }

    public void setDataAndTime(TextView dataAndTime) {
        this.dataAndTime = dataAndTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmmount() {
        return ammount;
    }

    public void setAmmount(String ammount) {
        this.ammount = ammount;
    }

    public String getCatedort() {
        return catedort;
    }

    public void setCatedort(String catedort) {
        this.catedort = catedort;
    }

    public View getButtonEdit() {
        return buttonEdit;
    }

    public void setButtonEdit(View buttonEdit) {
        this.buttonEdit = buttonEdit;
    }

    public View getButtonDelete() {
        return buttonDelete;
    }

    public void setButtonDelete(View buttonDelete) {
        this.buttonDelete = buttonDelete;
    }

    public View getSeparator() {
        return separator;
    }

    public void setSeparator(View separator) {
        this.separator = separator;
    }

    public View getSeparatorBottom() {
        return separatorBottom;
    }

    public void setSeparatorBottom(View separatorBottom) {
        this.separatorBottom = separatorBottom;
    }

    public View getLineTop() {
        return lineTop;
    }

    public void setLineTop(View lineTop) {
        this.lineTop = lineTop;
    }

    public LinearLayout getLl() {
        return ll;
    }

    public void setLl(LinearLayout ll) {
        this.ll = ll;
    }

    public LinearLayout getInnerll() {
        return innerll;
    }

    public void setInnerll(LinearLayout innerll) {
        this.innerll = innerll;
    }

    public LinearLayout getLinearLayoutHorizontalTop() {
        return linearLayoutHorizontalTop;
    }

    public void setLinearLayoutHorizontalTop(LinearLayout linearLayoutHorizontalTop) {
        this.linearLayoutHorizontalTop = linearLayoutHorizontalTop;
    }

    public LinearLayout getLinearLayoutHorizontalBottom() {
        return linearLayoutHorizontalBottom;
    }

    public void setLinearLayoutHorizontalBottom(LinearLayout linearLayoutHorizontalBottom) {
        this.linearLayoutHorizontalBottom = linearLayoutHorizontalBottom;
    }

    public int[] getGradientRed() {
        return gradientRed;
    }

    public void setGradientRed(int[] gradientRed) {
        this.gradientRed = gradientRed;
    }

    public int[] getGradientGreen() {
        return gradientGreen;
    }

    public void setGradientGreen(int[] gradientGreen) {
        this.gradientGreen = gradientGreen;
    }
///*}
//    CardView card;
//    int id;
//   String time;
//   String date;
//   String ammount;
//   String catedort;
//    View separator;
//    LinearLayout ll;
//    TextView tv;
//    Button buttonDelete;
//    Button buttonEdit;
//
//    View separatorBottom;
//
//
//    LinearLayout innerll;
//    TextView title;
//    TextView amount;
//    TextView category;
//
//
//    public Card(LinearLayout ll) {
//        this.ll = ll;
//    }
//
//    public Card(int id, String time, String date, String ammount, String catedort, LinearLayout ll) {
//        this.id = id;
//        this.time = time;
//        this.date = date;
//        this.ammount = ammount;
//        this.catedort = catedort;
//        this.ll = ll;
//    }
//
//    public void createCard(Context c, boolean b, String amount) {
//        title = new TextView(c);
//        if(b) {
//            title.setText("Доход");
//            title.setTextColor(Color.GREEN);
//        } else {
//            title.setText("Расход");
//            title.setTextColor(Color.RED);
//        }
//        card = new CardView(c);
//        this.amount = new TextView(c);
//        this.amount.setText(amount);
//        this.amount.setTextSize(18);
//        buttonDelete = new Button(c);//////
//        buttonDelete.setText("X");///////////
//        buttonDelete.setHeight(20);
//        buttonDelete.setBackgroundColor(Color.RED);//////////
//        buttonEdit = new Button(c);
//        buttonEdit.setText("Edit");
//        buttonEdit.setBackgroundColor(Color.GREEN);
//        separator = new View(c);
//        separatorBottom = new View(c);
//        innerll = new LinearLayout(c);
//        innerll.setOrientation(LinearLayout.VERTICAL);
//        separator.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
//        separator.setBackgroundColor(Color.GRAY);
//        separatorBottom.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
//        separatorBottom.setBackgroundColor(Color.GRAY);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 300);
//        card.setCardBackgroundColor(Color.WHITE);
//        card.setCardElevation(10);
//        ll.addView(card, lp);
//        card.addView(innerll, lp);
//        innerll.addView(title);
//        innerll.addView(separator);
//        innerll.addView(this.amount);
//        innerll.addView(separatorBottom);
//        innerll.addView(buttonDelete);//////
//        innerll.addView(buttonEdit);
//        ViewGroup.MarginLayoutParams layoutParams =
//                (ViewGroup.MarginLayoutParams) card.getLayoutParams();
//        layoutParams.setMargins(15, 15, 15, 0);
//        card.requestLayout();
//
//    }
//
//    public LinearLayout getLl() {
//        return ll;
//    }
//
//    public void setLl(LinearLayout ll) {
//        this.ll = ll;
//    }
//
//    public CardView getCard() {
//        return card;
//    }
//
//    public void setCard(CardView card) {
//        this.card = card;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getTime() {
//        return time;
//    }
//
//    public void setTime(String time) {
//        this.time = time;
//    }
//
//    public String getDate() {
//        return date;
//    }
//
//    public void setDate(String date) {
//        this.date = date;
//    }
//
//    public String getAmmount() {
//        return ammount;
//    }
//
//    public void setAmmount(String ammount) {
//        this.ammount = ammount;
//    }
//
//    public String getCatedort() {
//        return catedort;
//    }
//
//    public void setCatedort(String catedort) {
//        this.catedort = catedort;
//    }*/
}

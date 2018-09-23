package com.example.nikita.spendingdiary;

public class SpendEntity {
    public static int id;
    public static String ammount;
    public static String time;
    public static String date;
    public static String category;
    public static int type;

    public SpendEntity(){
    }
    public SpendEntity(int id, String ammount, String time, String date,String category,int type) {
        this.id = id;
        this.ammount  = ammount;
        this.time = time;
        this.date = date;
        this.category = category;
        this.type = type;
    }

    public static int getType() {
        return type;
    }

    public static void setType(int type) {
        SpendEntity.type = type;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        SpendEntity.id = id;
    }

    public static String getAmmount() {
        return ammount;
    }

    public static void setAmmount(String ammount) {
        SpendEntity.ammount = ammount;
    }

    public static String getTime() {
        return time;
    }

    public static void setTime(String time) {
        SpendEntity.time = time;
    }

    public static String getDate() {
        return date;
    }

    public static void setDate(String date) {
        SpendEntity.date = date;
    }

    public static String getCategory() {
        return category;
    }

    public static void setCategory(String category) {
        SpendEntity.category = category;
    }

    @Override
    public String toString() {
        return "ID: '" + this.id +
                "', Ammount: '" + this.ammount +
                "', Category: '" + this.category +
                "', Date: '" + this.date +
                "', Time: '" + this.time +
                "', Type: '" + this.type +"'";
    }
}


package com.knight.clothes.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.knight.clothes.models.Cart;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "e_commerce_manager";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "cart";

    private static final String KEY_ID = "product_id";
    private static final String KEY_QTY = "quantity";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_cart_table = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s INTEGER)",
                TABLE_NAME, KEY_ID, KEY_QTY);
        db.execSQL(create_cart_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop_cart_table = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        db.execSQL(drop_cart_table);

        onCreate(db);
    }

    public int getProductsLength(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        return cursor.getCount();
    }

    public List<Cart> getCart(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        List<Cart> list = new ArrayList<>();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            list.add(new Cart(cursor.getInt(0), cursor.getInt(1)));
            cursor.moveToNext();
        }
        return list;
    }

    public void addCart(int productId, int qty){
        SQLiteDatabase dbRead = this.getReadableDatabase();
        Cursor cursor = dbRead.query(TABLE_NAME, null, KEY_ID + " = ?",
                new String[] { String.valueOf(productId) },null, null, null);

        if(cursor != null && cursor.moveToFirst()){
            cursor.moveToFirst();
            updateCart(cursor.getInt(0), qty);
        }
        else{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_ID, productId);
            values.put(KEY_QTY, qty);
            db.insert(TABLE_NAME, null, values);
            db.close();
        }
    }

    public void updateCart(int productId, int qty) {
        SQLiteDatabase db = this.getWritableDatabase();

        if(qty <= 0){
            db.delete(TABLE_NAME, KEY_ID + " = ?", new String[] { String.valueOf(productId) });
        }
        else{
            ContentValues values = new ContentValues();
            values.put(KEY_QTY, qty);
            db.update(TABLE_NAME, values, KEY_ID + " = ?", new String[] { String.valueOf(productId) });
        }
        db.close();
    }

    public void removeCart(){
        SQLiteDatabase db = this.getWritableDatabase();
        String clearDBQuery = "DELETE FROM "+TABLE_NAME;
        db.execSQL(clearDBQuery);
    }
}

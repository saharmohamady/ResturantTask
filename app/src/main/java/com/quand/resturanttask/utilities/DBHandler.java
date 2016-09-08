package com.quand.resturanttask.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.quand.resturanttask.model.Customer;
import com.quand.resturanttask.model.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sahar on 07-Sep-16.
 */
public class DBHandler extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "ResturantDatabase.db";
    private static final String CUSTOMER_TABLE_NAME = "customer_table";
    private static final String TABLES_MAP_TABLE_NAME = "tables_map";
    private static final String KEY_ID = "id";
    private static final String KEY_FIRST_NAME = "first_name";
    private static final String KEY_LAST_NAME = "last_name";
    private static final String TABLE_KEY_ID = "id";
    private static final String TABLE_CUSTOMER_ID = "customer_id";
    private static final String TABLE_RESERVED = "reserved";


    String CREATE_CUSTOMER_TABLE = "CREATE TABLE " + CUSTOMER_TABLE_NAME + " (" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FIRST_NAME + " TEXT," + KEY_LAST_NAME + " TEXT)";
    String DROP_CUSTOMER_TABLE = "DROP TABLE IF EXISTS " + CUSTOMER_TABLE_NAME;

    String CREATE_TABLES_MAP_TABLE = "CREATE TABLE " + TABLES_MAP_TABLE_NAME + " (" + TABLE_KEY_ID + " INTEGER PRIMARY KEY," + TABLE_CUSTOMER_ID + " INTEGER FORIGEN KEY," + TABLE_RESERVED + " TEXT)";
    String DROP_TABLES_MAP_TABLE = "DROP TABLE IF EXISTS " + CUSTOMER_TABLE_NAME;


    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CUSTOMER_TABLE);
        db.execSQL(CREATE_TABLES_MAP_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_CUSTOMER_TABLE);
        db.execSQL(DROP_TABLES_MAP_TABLE);
        onCreate(db);
    }

    public void addCustomers(ArrayList<Customer> customers) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<ContentValues> list = new ArrayList<ContentValues>();
        Customer customer;
        try {
            for (int i = 0; i < customers.size(); i++) {
                customer = customers.get(i);
                ContentValues values = new ContentValues();
                values.put(KEY_ID, customer.getId());
                values.put(KEY_FIRST_NAME, customer.getFirstName());
                values.put(KEY_LAST_NAME, customer.getLastName());
                db.insert(CUSTOMER_TABLE_NAME, null, values);
            }
            db.close();
        } catch (Exception e) {
            Log.d("DB Error : ", e.getMessage());
        }
    }

    public ArrayList<Customer> getAllCustomers() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Customer> customerList = null;
        try {
            customerList = new ArrayList<Customer>();
            String QUERY = "SELECT * FROM " + CUSTOMER_TABLE_NAME;
            Cursor cursor = db.rawQuery(QUERY, null);
            if (!cursor.isLast()) {
                while (cursor.moveToNext()) {
                    Customer customer = new Customer();
                    customer.setId(cursor.getInt(0));
                    customer.setFirstName(cursor.getString(1));
                    customer.setLastName(cursor.getString(2));

                    customerList.add(customer);
                }
            }
            db.close();
        } catch (Exception e) {
            Log.e("error", e + "");
        }
        return customerList;
    }

    public int getCustomerCount() {
        int num = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String QUERY = "SELECT * FROM " + CUSTOMER_TABLE_NAME;
            Cursor cursor = db.rawQuery(QUERY, null);
            num = cursor.getCount();
            db.close();
            return num;
        } catch (Exception e) {
            Log.e("error", e + "");
        }
        return 0;
    }

    public int updateReserved(Table table) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(TABLE_CUSTOMER_ID, table.getCustomerId());
        cv.put(TABLE_RESERVED, table.isReserved());

        db.update(TABLES_MAP_TABLE_NAME, cv, TABLE_KEY_ID + "=" + table.getId(), null);
        db.close();

        return 0;
    }

    public void addTable(Table table) {
        SQLiteDatabase db = this.getWritableDatabase();
        String ROW1 = "INSERT INTO " + TABLES_MAP_TABLE_NAME + " Values ('" + table.getId() + "','" + table.getCustomerId() + "','" + table.isReserved() + "')";
        db.execSQL(ROW1);
    }

    public ArrayList<Table> getAllTables() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Table> tablesList = null;
        try {
            tablesList = new ArrayList<Table>();
            String QUERY = "SELECT * FROM " + TABLES_MAP_TABLE_NAME;
            Cursor cursor = db.rawQuery(QUERY, null);
            if (!cursor.isLast()) {
                while (cursor.moveToNext()) {
                    Table table = new Table();
                    table.setId(cursor.getInt(0));
                    table.setCustomerId(cursor.getInt(1));
                    if (cursor.getString(2).equalsIgnoreCase("false"))
                        table.setReserved(false);
                    else table.setReserved(true);

                    tablesList.add(table);
                }
            }
            db.close();
        } catch (Exception e) {
            Log.e("error", e + "");
        }
        return tablesList;
    }

    public void clearReservations() {
        SQLiteDatabase db = this.getWritableDatabase();
        String ROW1 = "update " + TABLES_MAP_TABLE_NAME + " set " + TABLE_RESERVED + "='false'";
        db.execSQL(ROW1);
        db.close();
    }

}

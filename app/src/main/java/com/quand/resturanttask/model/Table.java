package com.quand.resturanttask.model;

import android.content.Context;

import com.quand.resturanttask.utilities.DBHandler;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by salmohamady on 9/7/2016.
 */
public class Table {

    private int id;
    private int customerId;
    private boolean reserved;

    public Table() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public Table(int id, boolean reserved) {
        this.id = id;
        this.customerId = -1;
        this.reserved = reserved;
    }

    public static void parseAndStoreTablesData(Context context, String tablesData) {
        DBHandler handler = new DBHandler(context);

        try {
            JSONArray tableArrJson = new JSONArray(tablesData);
            Boolean reserved;
            for (int i = 0; i < tableArrJson.length(); i++) {
                reserved = (Boolean) tableArrJson.get(i);
                Table table = new Table(i, reserved);
                handler.addTable(table);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}

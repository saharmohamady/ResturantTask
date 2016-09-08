package com.quand.resturanttask.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.quand.resturanttask.utilities.DBHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by salmohamady on 9/5/2016.
 */
public class Customer implements Parcelable {

    private int id;
    private String firstName;
    private String lastName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Customer() {

    }

    public Customer(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Customer(Parcel in) {
        String[] data = new String[3];

        in.readStringArray(data);
        this.id = Integer.parseInt(data[0]);
        this.firstName = data[1];
        this.lastName = data[2];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[]{Integer.toString(this.id),
                this.firstName,
                this.lastName,
        });
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Customer createFromParcel(Parcel in) {
            return new Customer(in);
        }

        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };

    public static ArrayList<Customer> loadAndStoreFromJson(Context context, String jsonArray) {
        ArrayList<Customer> customersList = new ArrayList<>();

        try {
            JSONArray customersArrJson = new JSONArray(jsonArray);
            Customer tempCustomer;
            JSONObject customer;
            DBHandler handler = new DBHandler(context);
            for (int i = 0; i < customersArrJson.length(); i++) {
                customer = customersArrJson.getJSONObject(i);
                tempCustomer = new Customer();
                tempCustomer.setId(customer.getInt("id"));
                tempCustomer.setFirstName(customer.getString("customerFirstName"));
                tempCustomer.setLastName(customer.getString("customerLastName"));

                customersList.add(tempCustomer);
            }
            handler.addCustomers(customersList);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return customersList;
    }


    public String getName() {
        return firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object o) {
        return this.id == (((Customer) o).id);
    }
}

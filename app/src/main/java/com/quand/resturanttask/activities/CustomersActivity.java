package com.quand.resturanttask.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.quand.resturanttask.R;
import com.quand.resturanttask.adapters.CustomerAdapter;
import com.quand.resturanttask.model.Customer;
import com.quand.resturanttask.utilities.DBHandler;

import java.util.ArrayList;

public class CustomersActivity extends AppCompatActivity {
    // List view
    private ListView lv;
    // Listview Adapter
    CustomerAdapter customerAdapter;
    // Search EditText
    EditText inputSearch;
    // ArrayList for Listview
    ArrayList<Customer> customersList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);

        // Listview Data
        //load customers from db
        DBHandler handler = new DBHandler(this);
        customersList = handler.getAllCustomers();

        lv = (ListView) findViewById(R.id.list_view);
        // Adding items to listview
        customerAdapter = new CustomerAdapter(this, customersList);
        lv.setAdapter(customerAdapter);

        //on click for customer go to tables view to select table
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(CustomersActivity.this, TablesActivity.class);
                intent.putExtra("customer", customerAdapter.getCustomer(i));
                startActivity(intent);
            }
        });
        /**
         * Enabling Search Filter
         * */
        inputSearch = (EditText) findViewById(R.id.inputSearch);
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                CustomersActivity.this.customerAdapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
    }
}


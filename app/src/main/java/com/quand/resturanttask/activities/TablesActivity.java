package com.quand.resturanttask.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.quand.resturanttask.R;
import com.quand.resturanttask.adapters.TablesAdapter;
import com.quand.resturanttask.model.Customer;
import com.quand.resturanttask.model.RecyclerItemClickListener;
import com.quand.resturanttask.model.Table;
import com.quand.resturanttask.utilities.DBHandler;

import java.util.ArrayList;

public class TablesActivity extends AppCompatActivity {

    private TablesAdapter tablesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tables);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.tables_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        DBHandler handler = new DBHandler(this);
        final ArrayList<Table> arr = handler.getAllTables();

        final Customer customer = getIntent().getParcelableExtra("customer");

        final TextView noteTV = (TextView) findViewById(R.id.note);
        noteTV.setText(getString(R.string.PleasechoseTable) + " " + customer.getName());

        // specify an adapter
        tablesAdapter = new TablesAdapter(arr);
        mRecyclerView.setAdapter(tablesAdapter);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (arr.get(position).isReserved() == true) {
                            Toast.makeText(TablesActivity.this, getString(R.string.alreadyReserved), Toast.LENGTH_LONG).show();
                        } else {
                            tablesAdapter.setReserved(position);
                            noteTV.setText(getString(R.string.youselect) + " " + (position + 1) + " " + getString(R.string.forCustomer) + " " + customer.getName());
                        }
                    }
                })
        );

    }

    @Override
    public void onBackPressed() {
        DBHandler handler = new DBHandler(this);
        if (tablesAdapter.getSelectedIndex() != -1)
            handler.updateReserved(tablesAdapter.getItemAtPostion(tablesAdapter.getSelectedIndex()));
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

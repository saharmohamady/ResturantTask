package com.quand.resturanttask.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.quand.resturanttask.R;
import com.quand.resturanttask.model.Customer;

import java.util.ArrayList;

/**
 * Created by salmohamady on 2/29/2016.
 */
public class CustomerAdapter extends ArrayAdapter<Customer> implements Filterable {
    private ArrayList<Customer> mOriginalValues; // Original Values
    private ArrayList<Customer> mDisplayedValues;    // Values to be displayed

    public CustomerAdapter(Context context, ArrayList<Customer> customers) {
        super(context, 0);
        this.mOriginalValues = customers;
        this.mDisplayedValues = customers;

    }

    public ArrayList<Customer> getCustomers() {
        return mDisplayedValues;
    }

    @Override
    public int getCount() {
        return mDisplayedValues.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cusomers_list_item, parent, false);
            holder = new ViewHolder();
            holder.customerId = (TextView) convertView.findViewById(R.id.cusomer_id);
            holder.customerName = (TextView) convertView.findViewById(R.id.cusomer_name);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        Customer customer = getCustomer(position);
        holder.customerId.setText(customer.getId()+"");
        holder.customerName.setText(customer.getName());

        return convertView;
    }


    public Customer getCustomer(int position) {
        return mDisplayedValues.get(position);
    }

    public static class ViewHolder {
        public TextView customerName;
        public TextView customerId;

    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                mDisplayedValues = (ArrayList<Customer>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<Customer> FilteredArrList = new ArrayList<Customer>();

                if (mOriginalValues == null) {
                    mOriginalValues = new ArrayList<Customer>(mDisplayedValues); // saves the original data in mOriginalValues
                }

                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = mOriginalValues.size();
                    results.values = mOriginalValues;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < mOriginalValues.size(); i++) {
                        String name = mOriginalValues.get(i).getName();
                        if (name.toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrList.add(mOriginalValues.get(i));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }
}

package com.bhattaraibikash.api.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bhattaraibikash.api.R;
import com.bhattaraibikash.api.model.Employee;

import java.util.List;

import retrofit2.Callback;


public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {

    Context context;
    List<Employee> employeeList;


    public EmployeeAdapter(Context context, List<Employee> contactsList) {
        this.context = context;
        this.employeeList = contactsList;
    }


    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_employee, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        final Employee employee = employeeList.get(position);
        holder.tvName.setText(employee.getEmployee_name());
        holder.tvSalary.setText(Float.toString(employee.getEmployee_salary()));
        holder.tvAge.setText(Integer.toString(employee.getEmployee_age()));

    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }


    public class EmployeeViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvSalary, tvAge;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvSalary = itemView.findViewById(R.id.tvSalary);
            tvAge = itemView.findViewById(R.id.tvAge);
        }
    }

}

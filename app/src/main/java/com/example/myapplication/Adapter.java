package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList<ContactModel> arrayList;

    public Adapter(Activity activity, ArrayList<ContactModel> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ContactModel model = arrayList.get(position);
        holder.nameText.setText(model.getName());
        holder.numberText.setText(model.getNumber());

        String name = model.getName();
        String number = model.getNumber();

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("number", number);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public void filterList(ArrayList<ContactModel> filteredlist) {
        arrayList = filteredlist;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameText;
        TextView numberText;
        RelativeLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            numberText = itemView.findViewById(R.id.number);
            nameText = itemView.findViewById(R.id.name);
            layout = itemView.findViewById(R.id.clikibleLayout);

        }

    }

}

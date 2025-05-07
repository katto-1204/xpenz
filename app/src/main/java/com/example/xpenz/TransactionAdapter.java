package com.example.xpenz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {
    private List<Transaction> transactions = new ArrayList<>();

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);
        holder.titleText.setText(transaction.getTitle());
        holder.amountText.setText(String.format("%s$%.2f",
                transaction.getType().equals("Income") ? "+" : "-",
                transaction.getAmount()));
        holder.amountText.setTextColor(holder.itemView.getResources().getColor(
                transaction.getType().equals("Income") ? android.R.color.holo_green_dark : android.R.color.holo_red_dark));
        holder.dateText.setText(transaction.getDate());
        holder.timeText.setText(transaction.getTime());
        holder.typeText.setText(transaction.getType());
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleText;
        TextView amountText;
        TextView dateText;
        TextView timeText;
        TextView typeText;

        ViewHolder(View view) {
            super(view);
            titleText = view.findViewById(R.id.titleText);
            amountText = view.findViewById(R.id.amountText);
            dateText = view.findViewById(R.id.dateText);
            timeText = view.findViewById(R.id.timeText);
            typeText = view.findViewById(R.id.typeText);
        }
    }
}
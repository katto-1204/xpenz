package com.example.xpenz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {
    private List<Transaction> transactions;
    private final OnTransactionClickListener listener;

    public interface OnTransactionClickListener {
        void onTransactionClick(Transaction transaction);
    }

    public TransactionAdapter(List<Transaction> transactions, OnTransactionClickListener listener) {
        this.transactions = transactions;
        this.listener = listener;
    }

    public void updateTransactions(List<Transaction> newTransactions) {
        this.transactions = newTransactions;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);
        holder.bind(transaction);
    }

    @Override
    public int getItemCount() {
        return transactions != null ? transactions.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView transactionTitle;
        private final TextView transactionAmount;
        private final TextView transactionDate;
        private final TextView transactionType;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            transactionTitle = itemView.findViewById(R.id.transactionTitle);
            transactionAmount = itemView.findViewById(R.id.transactionAmount);
            transactionDate = itemView.findViewById(R.id.transactionDate);
            transactionType = itemView.findViewById(R.id.transactionType);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onTransactionClick(transactions.get(position));
                }
            });
        }

        void bind(Transaction transaction) {
            transactionTitle.setText(transaction.getTitle());

            String prefix = transaction.getType().equals("Income") ? "+" : "-";
            String amount = String.format("%s$%.2f", prefix, transaction.getAmount());
            transactionAmount.setText(amount);

            transactionDate.setText(transaction.getDate());
            transactionType.setText(transaction.getType());

            int colorResId = transaction.getType().equals("Income") ?
                    R.color.income_green : R.color.expense_red;
            transactionAmount.setTextColor(itemView.getContext().getColor(colorResId));
        }
    }
}
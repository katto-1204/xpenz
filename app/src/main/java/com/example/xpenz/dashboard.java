package com.example.xpenz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class dashboard extends Fragment {
    private TextView totalBalance;
    private TextView incomeAmount;
    private TextView expensesAmount;
    private RecyclerView transactionsRecyclerView;
    private TransactionAdapter adapter;
    private TransactionManager transactionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        totalBalance = view.findViewById(R.id.totalBalance);
        incomeAmount = view.findViewById(R.id.incomeAmount);
        expensesAmount = view.findViewById(R.id.expensesAmount);
        transactionsRecyclerView = view.findViewById(R.id.transactionsRecyclerView);

        transactionManager = new TransactionManager(requireContext());
        adapter = new TransactionAdapter();
        transactionsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        transactionsRecyclerView.setAdapter(adapter);

        updateDashboardValues();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateDashboardValues();
    }

    private void updateDashboardValues() {
        List<Transaction> transactions = transactionManager.getTransactions();
        double totalIncome = 0;
        double totalExpenses = 0;

        for (Transaction transaction : transactions) {
            if (transaction.getType().equals("Income")) {
                totalIncome += transaction.getAmount();
            } else {
                totalExpenses += transaction.getAmount();
            }
        }

        double balance = totalIncome - totalExpenses;

        totalBalance.setText(String.format("$%.2f", balance));
        incomeAmount.setText(String.format("+$%.2f", totalIncome));
        expensesAmount.setText(String.format("-$%.2f", totalExpenses));

        // Show only recent transactions in dashboard
        adapter.setTransactions(transactions.subList(0, Math.min(5, transactions.size())));
    }
}
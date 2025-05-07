package com.example.xpenz;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class dashboard extends Fragment implements TransactionAdapter.OnTransactionClickListener {
    private TextView totalBalance;
    private TextView incomeAmount;
    private TextView expensesAmount;
    private RecyclerView transactionsRecyclerView;
    private TextView noTransactionsText;
    private TextView recentTransactionsTitle;
    private TransactionAdapter adapter;
    private TransactionManager transactionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        totalBalance = view.findViewById(R.id.totalBalance);
        incomeAmount = view.findViewById(R.id.incomeAmount);
        expensesAmount = view.findViewById(R.id.expensesAmount);
        transactionsRecyclerView = view.findViewById(R.id.transactionsRecyclerView);
        noTransactionsText = view.findViewById(R.id.noTransactionsText);
        recentTransactionsTitle = view.findViewById(R.id.recentTransactionsTitle);

        transactionManager = new TransactionManager(requireContext());
        adapter = new TransactionAdapter(new ArrayList<>(), this);

        transactionsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        transactionsRecyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateDashboardValues();
    }

    @Override
    public void onTransactionClick(Transaction transaction) {
        Intent intent = new Intent(getContext(), Transaction_detail.class);
        intent.putExtra("transaction", transaction);
        startActivity(intent);
    }

    private void updateDashboardValues() {
        List<Transaction> transactions = transactionManager.getTransactions();

        if (transactions.isEmpty()) {
            showEmptyState();
            return;
        }

        showTransactions(transactions);
    }

    private void showEmptyState() {
        noTransactionsText.setVisibility(View.VISIBLE);
        transactionsRecyclerView.setVisibility(View.GONE);
        recentTransactionsTitle.setVisibility(View.GONE);
        totalBalance.setText("$0.00");
        incomeAmount.setText("+$0.00");
        expensesAmount.setText("-$0.00");
    }

    private void showTransactions(List<Transaction> transactions) {
        noTransactionsText.setVisibility(View.GONE);
        transactionsRecyclerView.setVisibility(View.VISIBLE);
        recentTransactionsTitle.setVisibility(View.VISIBLE);

        double totalIncome = 0;
        double totalExpenses = 0;

        for (Transaction transaction : transactions) {
            if ("Income".equals(transaction.getType())) {
                totalIncome += transaction.getAmount();
            } else {
                totalExpenses += transaction.getAmount();
            }
        }

        double balance = totalIncome - totalExpenses;
        updateBalanceDisplays(balance, totalIncome, totalExpenses);

        List<Transaction> recentTransactions = transactions.subList(0, Math.min(5, transactions.size()));
        adapter.updateTransactions(recentTransactions);
    }

    private void updateBalanceDisplays(double balance, double income, double expenses) {
        totalBalance.setText(String.format("₱%.2f", balance));
        incomeAmount.setText(String.format("+₱%.2f", income));
        expensesAmount.setText(String.format("-₱%.2f", expenses));
    }
}
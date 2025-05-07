package com.example.xpenz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class transactions extends Fragment {
    private RecyclerView recyclerView;
    private TransactionAdapter adapter;
    private TransactionManager transactionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transactions, container, false);

        transactionManager = new TransactionManager(requireContext());
        recyclerView = view.findViewById(R.id.transactionsListRecyclerView);
        adapter = new TransactionAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        loadTransactions();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadTransactions(); // Refresh list when returning to fragment
    }

    private void loadTransactions() {
        adapter.setTransactions(transactionManager.getTransactions());
    }
}
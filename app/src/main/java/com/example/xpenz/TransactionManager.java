package com.example.xpenz;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

public class TransactionManager {
    private static List<Transaction> transactions = new ArrayList<>();
    private static int nextId = 1;

    public TransactionManager(Context context) {
        // Constructor kept for compatibility
    }

    public void saveTransaction(Transaction transaction) {
        Transaction newTransaction = new Transaction(
                nextId++,
                transaction.getTitle(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getNote(),
                transaction.getDate()
        );
        transactions.add(0, newTransaction); // Add at beginning for recent first
    }

    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions);
    }

    public Transaction getTransaction(int id) {
        for (Transaction transaction : transactions) {
            if (transaction.getId() == id) {
                return transaction;
            }
        }
        return null;
    }

    public void deleteTransaction(int id) {
        transactions.removeIf(transaction -> transaction.getId() == id);
    }

    public void updateTransaction(Transaction updatedTransaction) {
        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getId() == updatedTransaction.getId()) {
                transactions.set(i, updatedTransaction);
                break;
            }
        }
    }
}
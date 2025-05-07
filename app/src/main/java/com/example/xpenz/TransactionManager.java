package com.example.xpenz;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TransactionManager {
    private static final String PREF_NAME = "TransactionPrefs";
    private static final String KEY_TRANSACTIONS = "transactions";
    private SharedPreferences preferences;
    private Gson gson;

    public TransactionManager(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void saveTransaction(Transaction transaction) {
        List<Transaction> transactions = getTransactions();
        transactions.add(0, transaction); // Add at beginning of list
        saveTransactionList(transactions);
    }

    public List<Transaction> getTransactions() {
        String json = preferences.getString(KEY_TRANSACTIONS, "");
        if (json.isEmpty()) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<List<Transaction>>(){}.getType();
        return gson.fromJson(json, type);
    }

    private void saveTransactionList(List<Transaction> transactions) {
        String json = gson.toJson(transactions);
        preferences.edit().putString(KEY_TRANSACTIONS, json).apply();
    }
}
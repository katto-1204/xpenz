package com.example.xpenz;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Transaction_detail extends AppCompatActivity {
    private Transaction currentTransaction;
    private TransactionManager transactionManager;
    private EditText titleEdit;
    private EditText amountEdit;
    private EditText typeEdit;
    private EditText noteEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_detail);

        transactionManager = new TransactionManager(this);

        titleEdit = findViewById(R.id.titleEdit);
        amountEdit = findViewById(R.id.amountEdit);
        typeEdit = findViewById(R.id.typeEdit);
        noteEdit = findViewById(R.id.noteEdit);

        Button updateButton = findViewById(R.id.updateButton);
        Button deleteButton = findViewById(R.id.deleteButton);

        currentTransaction = (Transaction) getIntent().getSerializableExtra("transaction");

        if (currentTransaction != null) {
            displayTransactionDetails();
        }

        updateButton.setOnClickListener(v -> updateTransaction());
        deleteButton.setOnClickListener(v -> deleteTransaction());
    }

    private void displayTransactionDetails() {
        if (currentTransaction != null) {
            titleEdit.setText(currentTransaction.getTitle());
            amountEdit.setText(String.valueOf(currentTransaction.getAmount()));
            typeEdit.setText(currentTransaction.getType());
            noteEdit.setText(currentTransaction.getNote());
        }
    }

    private void updateTransaction() {
        try {
            String title = titleEdit.getText().toString().trim();
            double amount = Double.parseDouble(amountEdit.getText().toString());
            String type = typeEdit.getText().toString().trim();
            String note = noteEdit.getText().toString().trim();

            if (title.isEmpty() || type.isEmpty()) {
                Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            Transaction updatedTransaction = new Transaction(
                    currentTransaction.getId(),
                    title,
                    type,
                    amount,
                    note,
                    currentTransaction.getDate()
            );

            transactionManager.updateTransaction(updatedTransaction);
            Toast.makeText(this, "Transaction updated successfully", Toast.LENGTH_SHORT).show();
            finish();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteTransaction() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Transaction")
                .setMessage("Are you sure you want to delete this transaction?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    transactionManager.deleteTransaction(currentTransaction.getId());
                    Toast.makeText(this, "Transaction deleted successfully", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("No", null)
                .show();
    }
}
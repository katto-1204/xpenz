package com.example.xpenz;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddTransactionActivity extends AppCompatActivity {
    private TextInputEditText titleInput;
    private TextInputEditText amountInput;
    private AutoCompleteTextView typeDropdown;
    private TextInputEditText tagsInput;
    private TextInputEditText noteInput;
    private Button saveButton;
    private TransactionManager transactionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        transactionManager = new TransactionManager(this);

        titleInput = findViewById(R.id.titleInput);
        amountInput = findViewById(R.id.amountInput);
        typeDropdown = findViewById(R.id.typeDropdown);
        tagsInput = findViewById(R.id.tagsInput);
        noteInput = findViewById(R.id.noteInput);
        saveButton = findViewById(R.id.saveButton);

        String[] types = new String[]{"Income", "Expense"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, types);
        typeDropdown.setAdapter(adapter);

        saveButton.setOnClickListener(v -> saveTransaction());
    }

    private void saveTransaction() {
        String title = titleInput.getText().toString().trim();
        String amountStr = amountInput.getText().toString().trim();
        String type = typeDropdown.getText().toString();
        String tags = tagsInput.getText().toString().trim();
        String note = noteInput.getText().toString().trim();

        if (title.isEmpty() || amountStr.isEmpty() || type.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid amount", Toast.LENGTH_SHORT).show();
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mma", Locale.getDefault());
        Date now = new Date();

        Transaction transaction = new Transaction(
                title, amount, type, tags, note,
                dateFormat.format(now),
                timeFormat.format(now)
        );

        transactionManager.saveTransaction(transaction);
        Toast.makeText(this, "Transaction saved", Toast.LENGTH_SHORT).show();
        finish();
    }
}
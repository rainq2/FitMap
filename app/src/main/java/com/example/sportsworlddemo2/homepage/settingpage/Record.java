package com.example.sportsworlddemo2.homepage.settingpage;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sportsworlddemo2.R;

public class Record extends AppCompatActivity {
    private EditText heightEditText;
    private EditText weightEditText;
    private TextView resultTextView;
    private TextView categoryTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_record);

        // Find the views in the layout XML file
        heightEditText = findViewById(R.id.textView99);
        weightEditText = findViewById(R.id.textView101);
        resultTextView = findViewById(R.id.textView103);
        categoryTextView = findViewById(R.id.textView104);

        // Set a TextWatcher for the height and weight input fields
        heightEditText.addTextChangedListener(textWatcher);
        weightEditText.addTextChangedListener(textWatcher);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Do nothing
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Get the height and weight values entered by the user
            double height = parseDouble(heightEditText.getText().toString());
            double weight = parseDouble(weightEditText.getText().toString());

            // Calculate the BMI
            double bmi = calculateBMI(height, weight);

            // Display the result in the TextView
            resultTextView.setText(String.format(" %.2f", bmi));

            // Determine the BMI category
            String category = getBMICategory(bmi);

            // Display the category in the TextView
            categoryTextView.setText(category);
        }

        @Override
        public void afterTextChanged(Editable s) {
            // Do nothing
        }
    };

    // Calculate BMI using the height and weight values
    private double calculateBMI(double height, double weight) {
        // Convert height to meters
        double heightInMeters = height / 100.0;

        // Calculate BMI using the formula: weight (kg) / height^2 (m^2)
        return weight / (heightInMeters * heightInMeters);
    }

    private double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0.0; // Return a default value if parsing fails
        }
    }

    // Get the BMI category based on the calculated BMI value
    private String getBMICategory(double bmi) {
        if (bmi < 18.5) {
            return "太瘦啦！多吃點";
        } else if (bmi < 25) {
            return "很棒喔繼續維持";
        } else if (bmi < 30) {
            return "加油！是不是沒有認真照菜單做！！";
        } else {
            return "危險！！！！！快動起來";
        }
    }
}

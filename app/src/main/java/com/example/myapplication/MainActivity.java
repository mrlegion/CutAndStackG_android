package com.example.myapplication;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.myapplication.domain.CutAndStackCalculator;

public class MainActivity extends AppCompatActivity {

    //private EditText colsText;
    //private EditText rowsText;

    private NumberPicker colsPicker;
    private NumberPicker rowsPicker;
    private CheckBox isOneSideChecked;

    private int colsValue = 0;
    private int rowsValue = 0;

    private Button copyButton;

    private TextView resultText;

    private CutAndStackCalculator cutAndStackCalculator;

    private String generateText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cutAndStackCalculator = new CutAndStackCalculator();

        // init private component
        InitComponent();
    }

    private void InitComponent()
    {
        NumberPicker.OnValueChangeListener listener = new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (picker.getId() == R.id.cols_picker)
                    reGenerate("cols", newVal);
                else if (picker.getId() == R.id.rows_picker)
                    reGenerate("rows", newVal);
            }
        };

        colsPicker = this.findViewById(R.id.cols_picker);
        colsPicker.setMaxValue(100);
        colsPicker.setMinValue(0);
        colsPicker.setOnValueChangedListener(listener);

        rowsPicker = this.findViewById(R.id.rows_picker);
        rowsPicker.setMaxValue(100);
        rowsPicker.setMinValue(0);
        rowsPicker.setOnValueChangedListener(listener);

        copyButton = this.findViewById(R.id.copyBtn);
        resultText = this.findViewById(R.id.resultText);
        isOneSideChecked = this.findViewById(R.id.oneSideCheckBox);

        isOneSideChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                colsValue = colsPicker.getValue();
                rowsValue = rowsPicker.getValue();
                reGenerate();
            }
        });

        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager manager = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
                ClipData data = ClipData.newPlainText("data", generateText);
                manager.setPrimaryClip(data);
                copyButton.setText("Copied!");
            }
        });

        resultText.setText(this.getString(R.string.welcome_message));
    }

    private void reGenerate(String name, int value) {

        copyButton.setText("Copy");

        switch (name) {
            case "cols":
                colsValue = value;
                break;
            case "rows":
                rowsValue = value;
                break;
        }

        reGenerate();
    }
    private void reGenerate() {
        String result = cutAndStackCalculator.Generate(colsValue, rowsValue, isOneSideChecked.isChecked());
        generateText = result;
        resultText.setText((result.length() > 100) ? result.substring(0, 100) + " ..." : result);
    }
}



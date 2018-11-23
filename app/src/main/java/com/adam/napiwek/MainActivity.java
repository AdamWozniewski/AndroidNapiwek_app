package com.adam.napiwek;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private EditText amountEditText;
    private TextView amountTextView;

    private TextView percentTextView;
    private SeekBar percentSeekBar;

    private TextView tipLabelTextView, tipTextView;

    private TextView totalLabelTextView, totalTextView;

    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();

    private double billAmount = 0.0;
    private double tipPercent = 0.15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.amountEditText = (EditText) findViewById(R.id.amountEditText);
        this.amountTextView = (TextView) findViewById(R.id.amountTextView);

        this.percentTextView = (TextView) findViewById(R.id.percentTextView);
        this.percentSeekBar = (SeekBar) findViewById(R.id.percentSeekBar);

        this.tipLabelTextView = (TextView) findViewById(R.id.tipLabelTextView);
        this.tipTextView = (TextView) findViewById(R.id.tipTextView);

        this.totalLabelTextView = (TextView) findViewById(R.id.totalLabelTextView);
        this.totalTextView = (TextView) findViewById(R.id.totalTextView);

        this.amountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    MainActivity.this.billAmount = Double.parseDouble(s.toString()) / 100;
                    MainActivity.this.amountTextView.setText(MainActivity.currencyFormat.format(MainActivity.this.billAmount));
//                    MainActivity.this.amountTextView.setText(MainActivity.this.addWhiteSpaces(s.length()));
                } catch (NumberFormatException ex) {
                    MainActivity.this.amountTextView.setText("");
                    MainActivity.this.billAmount = 0.0;
                }

                MainActivity.this.calculateTipAndTotalAmount();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        this.percentSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                MainActivity.this.tipPercent = progress / 100.0;
                MainActivity.this.calculateTipAndTotalAmount();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void calculateTipAndTotalAmount() {
        double tipAmount = this.billAmount * this.tipPercent;
        double totalAmount = this.billAmount + tipAmount;

        this.percentTextView.setText(MainActivity.percentFormat.format(this.tipPercent));
        this.tipTextView.setText(MainActivity.currencyFormat.format(tipAmount));
        this.totalTextView.setText(MainActivity.currencyFormat.format(totalAmount));
    }

    private String addWhiteSpaces(int length) {
        String whiteSpace = "";
        for (int i =0; i <= length; i++) {
            whiteSpace += "  ";
        }
        whiteSpace += "$";
        return whiteSpace;
    }
}

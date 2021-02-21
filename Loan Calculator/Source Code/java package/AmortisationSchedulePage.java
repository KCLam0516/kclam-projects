package com.example.assignment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.lang.Math.pow;


public class AmortisationSchedulePage extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener {
    String sName, sLoanAmount, sInterestRate, sLoanPeriod, sLoanDate, monthly_instalment, last_date, total_paid;
    int iLoanPeriod, iLoanAmount, iInterestRate, iMonth;
    float balance, totalAmount, interestRate, monthlyRepayment = 0, interestPaid, principalPaid;
    Spinner dropdown;
    TextView payment_number_box, beginning_balance_box, monthly_repayment_box, interest_paid_box, principal_paid_box, months_box, monthly_interest_paid;
    HorizontalScrollView scheduleView;
    TableLayout monthView;
    Button message;
    Date date;
    List<String> items;
    ArrayAdapter<String> adapter;
    
    StringBuilder interest = new StringBuilder();
    StringBuilder sBalance = new StringBuilder();
    StringBuilder repayment = new StringBuilder();
    StringBuilder principal = new StringBuilder();
    StringBuilder period = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        
        Intent intent = getIntent();
        sName=intent.getStringExtra("interestName");
        sInterestRate=intent.getStringExtra("interestRate");
        sLoanAmount=intent.getStringExtra("loanAmount");
        sLoanPeriod=intent.getStringExtra("loanPeriod");
        sLoanDate=intent.getStringExtra("loanStartDate");

        payment_number_box = findViewById(R.id.payment_number_box);
        beginning_balance_box = findViewById(R.id.beginning_balance_box);
        monthly_repayment_box = findViewById(R.id.monthly_repayment_box);
        interest_paid_box = findViewById(R.id.interest_paid_box);
        principal_paid_box = findViewById(R.id.principal_paid_box);
        months_box = findViewById(R.id.months_box);
        monthly_interest_paid = findViewById(R.id.monthly_interest_paid);
        dropdown = findViewById(R.id.dropdown);
        scheduleView = findViewById(R.id.scheduleView);
        monthView = findViewById(R.id.monthView);
        message = findViewById(R.id.message);

        iLoanPeriod = Integer.parseInt(sLoanPeriod);
        iInterestRate = Integer.parseInt(sInterestRate);
        iLoanAmount = Integer.parseInt(sLoanAmount);

        message.setOnClickListener(this);
        dropdown.setOnItemSelectedListener(this);
        startActivity();
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void startActivity() {
        scheduleView.setVisibility(View.GONE);
        monthView.setVisibility(View.GONE);
        calculation();
        monthly_instalment = " RM " + String.format("%.2f", monthlyRepayment);
        setDropdown();
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        interestPaid = 0;
        monthlyRepayment = 0;
        totalAmount = 0;
        balance = iLoanAmount;

        if (position == 0) {
            filter();
            scheduleView.setVisibility(View.VISIBLE);
            monthView.setVisibility(View.GONE);
            calculateMonthlyRepay();
        } else {
            filter();
            scheduleView.setVisibility(View.GONE);
            monthView.setVisibility(View.VISIBLE);

            for (int i = 0; i < position; i++) {
                calculation();
                iMonth = i+1;
                }
            }
            period.append(iMonth);
            interest.append(String.format("%.2f", interestPaid));
            months_box.setText(period);
            monthly_interest_paid.setText(interest);
        }

    @Override
    public void onClick(View v) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = format.parse(sLoanDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, iLoanPeriod);
            last_date = " " + format.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "Dear "+ sName +
                ", you need to repay " + monthly_instalment +
                " monthly, the last payment date is" +last_date +
                " and the total amount is"+ total_paid +".", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private void setDropdown() {
        items = new ArrayList<String>();
        items.add("SCHEDULE TABLE");
        for (int i = 1; i <= iLoanPeriod; i++) {
                items.add("Month " + i);
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void calculateMonthlyRepay() {
        for (int i = 1; i <= iLoanPeriod; i++) {
            calculation();

            if (i == iLoanPeriod) {
                sBalance.append(String.format("%.2f", balance));
                interest.append(String.format("%.2f", interestPaid));
                repayment.append(String.format("%.2f", monthlyRepayment));
                principal.append(String.format("%.2f", principalPaid));
                period.append(i);
            } else {
                sBalance.append(String.format("%.2f", balance)).append("\n");
                interest.append(String.format("%.2f", interestPaid)).append("\n");
                repayment.append(String.format("%.2f", monthlyRepayment)).append("\n");
                principal.append(String.format("%.2f", principalPaid)).append("\n");
                period.append(i).append("\n");
            }
        }
        payment_number_box.setText(period);
        beginning_balance_box.setText(sBalance);
        monthly_repayment_box.setText(repayment);
        interest_paid_box.setText(interest);
        principal_paid_box.setText(principal);
        total_paid = " RM " + String.format("%.2f", totalAmount);
    }

    private void filter() {
        interest.delete(0, interest.length());
        repayment.delete(0, repayment.length());
        sBalance.delete(0, sBalance.length());
        principal.delete(0, principal.length());
        period.delete(0, period.length());
    }

    private void calculation() {
        interestRate = (float) iInterestRate / 1200;
        balance = balance - monthlyRepayment + interestPaid;
        monthlyRepayment = (float) ((iLoanAmount * interestRate * pow(1 + interestRate, iLoanPeriod)) / (pow(1 + interestRate, iLoanPeriod) - 1));
        interestPaid = interestRate * balance;
        principalPaid = monthlyRepayment - interestPaid;
        totalAmount += monthlyRepayment;
    }
}

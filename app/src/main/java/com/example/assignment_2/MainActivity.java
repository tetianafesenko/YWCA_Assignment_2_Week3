package com.example.assignment_2;

import static java.lang.Double.parseDouble;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    Button one, two, three, four, five, six, seven, eight, nine, zero, clear, buy, managerBtn;
    TextView typeTV, quantityTV, totalTV;
    ListDataAdapter adapter;
    Double total = 0.0;
    ListView itemsListView;
    int index = -1;
    ArrayList<Product> stockList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup data source
        initViews();
        setClickListeners();
        setListData();

        itemsListView.setOnItemClickListener(this);
    }

    private void setClickListeners() {
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        zero.setOnClickListener(this);
        buy.setOnClickListener(this);
        clear.setOnClickListener(this);
        managerBtn.setOnClickListener(this);
    }

    private void setListData() {
        ((MyApp) getApplication()).setProductlistData();

        stockList = ((MyApp) getApplication()).productlist;
        adapter = new ListDataAdapter(((MyApp) getApplication()).productlist, MainActivity.this);
        itemsListView.setAdapter(adapter);
    }

    private void initViews() {
        typeTV = findViewById(R.id.product_textview);
        quantityTV = findViewById(R.id.quantity_tv);
        totalTV = findViewById(R.id.total_tv);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        seven = findViewById(R.id.seven);
        eight = findViewById(R.id.eight);
        nine = findViewById(R.id.nine);
        zero = findViewById(R.id.zero);
        clear = findViewById(R.id.clear_but);
        buy = findViewById(R.id.buttonBuy);
        managerBtn = findViewById(R.id.buttonManager);
        itemsListView = findViewById(R.id.listview_products);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        if (viewId == R.id.buttonBuy) {
            if (!totalTV.getText().toString().equals("")) {
                showAlert();
            } else {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            }
        } else if (viewId == R.id.clear_but) {
            quantityTV.setText("");
            quantityTV.setHint("Quantity");
            totalTV.setText("");
            totalTV.setHint("Total");
            typeTV.setText("");
            typeTV.setHint("Product Type");
        } else if (viewId == R.id.buttonManager) {
            Intent intentManager = new Intent(MainActivity.this, ManagerActivity.class);
            startActivity(intentManager);
        } else {
            Button b = (Button) view;
            String num1 = quantityTV.getText().toString();
            String num2 = b.getText().toString();
            String num = num1 + num2;

            if (index != -1) {
                Double price = stockList.get(index).getPrice();
                total = price * parseDouble(num);
                totalTV.setText(String.format("%.2f", total));
            }
            quantityTV.setText(String.format("%s%s", num1, num2));

            if (Integer.parseInt(quantityTV.getText().toString()) > stockList.get(index).getQuantity()) {
                Toast.makeText(this, "No enough quantity in the stock", Toast.LENGTH_SHORT).show();
                quantityTV.setText("");
                quantityTV.setHint("Quantity");
                totalTV.setText("");
                totalTV.setHint("Total");
                typeTV.setText("");
                typeTV.setHint("Product Type");
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        typeTV.setText(stockList.get(i).getType());
        index = i;
        if (!quantityTV.getText().toString().equals("")) {
            total = parseDouble(quantityTV.getText().toString()) * stockList.get(i).getPrice();
            totalTV.setText(String.format("%.2f", total));
        }
    }

    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your purchase is " + quantityTV.getText().toString() + " " + stockList.get(index).getType() + " for " + totalTV.getText().toString())
                .setTitle("Thank You for your purchase");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                quantityTV.setText("");
                quantityTV.setHint("Quantity");
                totalTV.setText("");
                totalTV.setHint("Total");
                typeTV.setText("");
                typeTV.setHint("Product Type");
            }
        });

        builder.create().show();
        Product obj = ((MyApp) getApplication()).productlist.get(index);
        obj.setQuantity(obj.getQuantity() - Integer.parseInt(quantityTV.getText().toString()));
        ((MyApp) getApplication()).productlist.set(index, obj);
        adapter.notifyDataSetChanged();

        Date d1 = new Date();
        History h1 = new History(obj.getType(), parseDouble(totalTV.getText().toString()), Integer.parseInt(quantityTV.getText().toString()), d1);
        ((MyApp) getApplication()).historyList.add(h1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}

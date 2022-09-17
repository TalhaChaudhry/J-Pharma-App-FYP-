package com.talhachaudhry.jpharmaappfyp.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.talhachaudhry.jpharmaappfyp.databinding.ActivitySalesReportBinding;
import com.talhachaudhry.jpharmaappfyp.view_models.ReportAnalysisViewModel;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SalesReportActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ActivitySalesReportBinding binding;
    ReportAnalysisViewModel viewModel;
    HashMap<String, DataEntry> data = new HashMap<>();
    int monthNumber = 0;
    String[] month = {"Jan", "Feb", "Mar", "Apr", "May",
            "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    String monthName;
    int year;
    Pie pie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySalesReportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        viewModel = new ViewModelProvider(this).get(ReportAnalysisViewModel.class);
        ArrayAdapter<String> colorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, month);
        binding.monthSpinner.setAdapter(colorAdapter);
        binding.monthSpinner.setOnItemSelectedListener(this);
        viewModel.getTotalPriceLivedata().observe(this, integer ->
                binding.totalTv.setText(MessageFormat.format("{0}", integer)));
        binding.filterTv.setOnClickListener(view -> {
            if (!binding.yearEt.getText().toString().trim().equals("")) {
                year = Integer.parseInt(binding.yearEt.getText().toString().trim());
                if (year != 0) {
                    viewModel.getAnalysisModelLivedata(monthNumber, year).observe(this,
                            this::updatePie
                    );
                } else {
                    Toast.makeText(this, "Year cannot be zero", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Year cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });
        pie = AnyChart.pie();
        pie.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
            @Override
            public void onClick(Event event) {
                Toast.makeText(SalesReportActivity.this, event.getData().get("x") + ":" + event.getData().get("value"),
                        Toast.LENGTH_SHORT).show();
            }
        });
        binding.salesReportRv.setProgressBar(binding.progressBar);

        pie.labels().position("outside");

        pie.legend().title().enabled(true);
        pie.legend().title()
                .text("Admin Analysis")
                .padding(0d, 0d, 10d, 0d);

        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        binding.salesReportRv.setChart(pie);

    }


    void updatePie(HashMap<String, Integer> hashMap) {
        for (Map.Entry<String, Integer> entrySet : hashMap.entrySet()) {
            data.put(entrySet.getKey(), new ValueDataEntry(entrySet.getKey(), entrySet.getValue()));
        }
        ArrayList<DataEntry> list = new ArrayList<>();
        for (Map.Entry<String, DataEntry> entrySet : data.entrySet()) {
            list.add(entrySet.getValue());
        }
        pie.title("Medicine Sales in " + " " + month[monthNumber] + " " + year);
        pie.animation();
        pie.data(list);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        monthNumber = i;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // do nothing
    }
}
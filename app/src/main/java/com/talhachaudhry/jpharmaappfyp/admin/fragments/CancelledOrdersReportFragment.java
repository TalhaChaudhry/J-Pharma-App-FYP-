package com.talhachaudhry.jpharmaappfyp.admin.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.talhachaudhry.jpharmaappfyp.R;
import com.talhachaudhry.jpharmaappfyp.databinding.FragmentCancelledOrdersReportBinding;
import com.talhachaudhry.jpharmaappfyp.view_models.ReportAnalysisViewModel;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CancelledOrdersReportFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    FragmentCancelledOrdersReportBinding binding;
    HashMap<String, DataEntry> data = new HashMap<>();
    ReportAnalysisViewModel viewModel;
    int monthNumber = 0;
    String[] month = {"Jan", "Feb", "Mar", "Apr", "May",
            "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    String monthName;
    int year;
    Pie pie;


    public static CancelledOrdersReportFragment newInstance() {
        return new CancelledOrdersReportFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCancelledOrdersReportBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);
        viewModel = new ViewModelProvider(this).get(ReportAnalysisViewModel.class);
        ArrayAdapter<String> colorAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_dropdown_item, month);
        binding.monthSpinner.setAdapter(colorAdapter);
        binding.monthSpinner.setOnItemSelectedListener(this);
//        viewModel.getDateModelMutableLiveData().observe(getViewLifecycleOwner(), dateModel -> {
//            this.monthName = dateModel.getMonthName();
//            this.year = dateModel.getYear();
//            viewModel.getCancelOrdersModelLivedata(dateModel.getMonth(), dateModel.getYear()).observe(getViewLifecycleOwner(),
//                    this::updatePie);
//        });
        viewModel.getCancelOrdersAmountLivedata().observe(getViewLifecycleOwner(),
                integer -> binding.totalTv.setText(MessageFormat.format("{0}", integer)));
        pie = AnyChart.pie();
        pie.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
            @Override
            public void onClick(Event event) {
                Toast.makeText(requireActivity(), event.getData().get("x") + ":" + event.getData().get("value"),
                        Toast.LENGTH_SHORT).show();
            }
        });
        binding.salesReportCancelRv.setProgressBar(binding.progressBar);

        pie.labels().position("outside");

        pie.legend().title().enabled(true);
        pie.legend().title()
                .text("Admin Analysis")
                .padding(0d, 0d, 10d, 0d);

        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        binding.salesReportCancelRv.setChart(pie);

        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.sales_report_tool_bar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.filter) {
            if (!binding.yearEt.getText().toString().trim().equals("")) {
                this.year = Integer.parseInt(binding.yearEt.getText().toString().trim());
                if (year != 0) {
                    viewModel.getCancelOrdersModelLivedata(monthNumber, year).observe(getViewLifecycleOwner(),
                            this::updatePie);
                } else {
                    Toast.makeText(requireActivity(), "Year cannot be zero", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(requireActivity(), "Year cannot be empty", Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }

    void updatePie(HashMap<String, Integer> hashMap) {
        for (Map.Entry<String, Integer> entrySet : hashMap.entrySet()) {
            data.put(entrySet.getKey(), new ValueDataEntry(entrySet.getKey(), entrySet.getValue()));
        }
        ArrayList<DataEntry> list = new ArrayList<>();
        for (Map.Entry<String, DataEntry> entrySet : data.entrySet()) {
            list.add(entrySet.getValue());
        }
        if (list.isEmpty()) {
            pie.title("No Medicine Cancelled in " + " " + monthName + " " + year);
        } else {
            pie.title("Medicine Cancelled in " + " " + monthName + " " + year);
            pie.animation();
            pie.data(list);
        }
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
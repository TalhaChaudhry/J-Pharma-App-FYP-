package com.talhachaudhry.jpharmaappfyp.view_models;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.talhachaudhry.jpharmaappfyp.models.CartModel;
import com.talhachaudhry.jpharmaappfyp.models.ManageMedicineModel;
import com.talhachaudhry.jpharmaappfyp.models.OrderModel;
import com.talhachaudhry.jpharmaappfyp.utils.NodesNames;

import java.util.HashMap;
import java.util.Objects;

public class ReportAnalysisViewModel extends ViewModel {

    MutableLiveData<HashMap<String, Integer>> completeOrderAnalysisModelLivedata;
    MutableLiveData<HashMap<String, Integer>> cancelOrdersModelLivedata;
    MutableLiveData<Integer> totalPriceLivedata;
    MutableLiveData<Integer> cancelOrdersAmountLivedata;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    int month;
    int year;


    public MutableLiveData<Integer> getTotalPriceLivedata() {
        if (totalPriceLivedata == null) {
            totalPriceLivedata = new MutableLiveData<>();
            totalPriceLivedata.setValue(0);
        }
        return totalPriceLivedata;
    }

    public MutableLiveData<Integer> getCancelOrdersAmountLivedata() {
        if (cancelOrdersAmountLivedata == null) {
            cancelOrdersAmountLivedata = new MutableLiveData<>();
            cancelOrdersAmountLivedata.setValue(0);
        }
        return cancelOrdersAmountLivedata;
    }

    public MutableLiveData<HashMap<String, Integer>> getCancelOrdersModelLivedata(int month, int year) {
        if (cancelOrdersModelLivedata == null || this.month != month || this.year != year) {
            cancelOrdersModelLivedata = new MutableLiveData<>();
            cancelOrdersModelLivedata.setValue(new HashMap<>());
            if (cancelOrdersAmountLivedata != null) {
                cancelOrdersAmountLivedata.setValue(0);
            }
            getAllMedicines(month, year,
                    NodesNames.CANCEL_NODE_NAME.getName(), cancelOrdersModelLivedata, cancelOrdersAmountLivedata);
        }
        return cancelOrdersModelLivedata;
    }

    public MutableLiveData<HashMap<String, Integer>> getAnalysisModelLivedata(int month, int year) {
        if (completeOrderAnalysisModelLivedata == null || this.month != month || this.year != year) {
            completeOrderAnalysisModelLivedata = new MutableLiveData<>();
            completeOrderAnalysisModelLivedata.setValue(new HashMap<>());
            if (totalPriceLivedata != null) {
                totalPriceLivedata.setValue(0);
            }
            getAllMedicines(month, year,
                    NodesNames.COMPLETE_NODE_NAME.getName(), completeOrderAnalysisModelLivedata, totalPriceLivedata);
        }
        return completeOrderAnalysisModelLivedata;
    }


    private void getAllMedicines(Integer month, int year, String nodeName,
                                 MutableLiveData<HashMap<String, Integer>> livedata, MutableLiveData<Integer> priceLiveData) {
        database.getReference()
                .child("Medicines").
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            ManageMedicineModel model = snapshot1.getValue(ManageMedicineModel.class);
                            updateInLiveData(model.getName(), 0, model.getPrice(), livedata, priceLiveData);
                        }
                        setMonthAndYear(month, year, nodeName, livedata, priceLiveData);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // do nothing
                    }
                });
    }

    private void setMonthAndYear(Integer month, int year, String nodeName,
                                 MutableLiveData<HashMap<String, Integer>> liveData, MutableLiveData<Integer> priceLivedData) {
        database.getReference()
                .child(NodesNames.MAIN_NODE_NAME.getName())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            try {
                                database.getReference().
                                        child(NodesNames.MAIN_NODE_NAME.getName()).
                                        child(snapshot1.getKey()).
                                        child(nodeName).
                                        addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                for (DataSnapshot snapshot2 : snapshot.getChildren()) {
                                                    OrderModel model = snapshot2.getValue(OrderModel.class);
                                                    actionOnDataSnap(model, month, year, liveData, priceLivedData);
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                // do nothing
                                            }
                                        });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // do nothing
                    }
                });
    }

    private void actionOnDataSnap(OrderModel model, int month, int year,
                                  MutableLiveData<HashMap<String, Integer>> liveData, MutableLiveData<Integer> priceLivedData) {
        if (model.getMonth() == month && year == model.getYear()) {
            for (CartModel data :
                    model.getOrdersList()) {
                updateInLiveData(data.getModel().getName(), data.getQuantity(),
                        data.getModel().getPrice(), liveData, priceLivedData);
            }
        }
    }

    private void updateInLiveData(String name, int quantity, int price,
                                  MutableLiveData<HashMap<String, Integer>> liveData, MutableLiveData<Integer> priceLivedData) {
        if (liveData != null) {
            HashMap<String, Integer> hashMap = Objects.requireNonNull(liveData.getValue());
            if (priceLivedData != null) {
                int total = priceLivedData.getValue();
                total += price * quantity;
                priceLivedData.setValue(total);
            }
            if (!hashMap.containsKey(name)) {
                hashMap.put(name, quantity);
            } else {
                hashMap.put(name, hashMap.get(name) + quantity);
            }
            liveData.setValue(hashMap);
        }
    }
}

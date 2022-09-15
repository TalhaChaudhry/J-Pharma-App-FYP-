package com.talhachaudhry.jpharmaappfyp.view_models;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.talhachaudhry.jpharmaappfyp.models.OrderModel;
import com.talhachaudhry.jpharmaappfyp.utils.NodesNames;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrdersDetailViewModel extends ViewModel {
    MutableLiveData<List<OrderModel>> proceedingOrdersLiveData;
    MutableLiveData<List<OrderModel>> dispatchOrdersLiveData;
    MutableLiveData<List<OrderModel>> pendingOrdersLiveData;
    MutableLiveData<List<OrderModel>> completeOrdersLiveData;
    MutableLiveData<List<OrderModel>> cancelledOrdersLiveData;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    public MutableLiveData<List<OrderModel>> getCompleteOrdersLiveData() {
        if (completeOrdersLiveData == null) {
            completeOrdersLiveData = new MutableLiveData<>();
            completeOrdersLiveData.setValue(new ArrayList<>());
            getOrders(NodesNames.COMPLETE_NODE_NAME.getName(), completeOrdersLiveData);
        }
        return completeOrdersLiveData;
    }

    public MutableLiveData<List<OrderModel>> getPendingOrdersLiveData() {
        if (pendingOrdersLiveData == null) {
            pendingOrdersLiveData = new MutableLiveData<>();
            pendingOrdersLiveData.setValue(new ArrayList<>());
            getOrders(NodesNames.PENDING_NODE_NAME.getName(), pendingOrdersLiveData);
        }
        return pendingOrdersLiveData;
    }

    public MutableLiveData<List<OrderModel>> getCancelledOrdersLiveData() {
        if (cancelledOrdersLiveData == null) {
            cancelledOrdersLiveData = new MutableLiveData<>();
            cancelledOrdersLiveData.setValue(new ArrayList<>());
            getOrders(NodesNames.CANCEL_NODE_NAME.getName(), cancelledOrdersLiveData);
        }
        return cancelledOrdersLiveData;
    }

    public MutableLiveData<List<OrderModel>> getDispatchOrdersLiveData() {
        if (dispatchOrdersLiveData == null) {
            dispatchOrdersLiveData = new MutableLiveData<>();
            dispatchOrdersLiveData.setValue(new ArrayList<>());
            getOrders(NodesNames.DISPATCH_NODE_NAME.getName(), dispatchOrdersLiveData);
        }
        return dispatchOrdersLiveData;
    }

    public MutableLiveData<List<OrderModel>> getProceedingOrdersLiveData() {
        if (proceedingOrdersLiveData == null) {
            proceedingOrdersLiveData = new MutableLiveData<>();
            proceedingOrdersLiveData.setValue(new ArrayList<>());
            getOrders(NodesNames.PROCEEDING_NODE_NAME.getName(), proceedingOrdersLiveData);
        }
        return proceedingOrdersLiveData;
    }

    private void getOrders(String nodeName, MutableLiveData<List<OrderModel>> listName) {

        try {
            database.getReference().
                    child(NodesNames.MAIN_NODE_NAME.getName()).
                    child(auth.getUid()).
                    child(nodeName).
                    addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot snapshot2 : snapshot.getChildren()) {
                                OrderModel model = snapshot2.getValue(OrderModel.class);
                                updateOrdersList(listName, model);
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

    public void editOrder(OrderModel orderModel) {
        //TODO
    }

    private void updateOrdersList(MutableLiveData<List<OrderModel>> listName, OrderModel orderModel) {
        if (listName != null) {
            List<OrderModel> list = new ArrayList<>(Objects.requireNonNull(listName.getValue()));
            list.add(orderModel);
            listName.setValue(list);
        }
    }
}

package com.talhachaudhry.jpharmaappfyp.view_models;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.talhachaudhry.jpharmaappfyp.models.ManageMedicineModel;
import com.talhachaudhry.jpharmaappfyp.models.OrderModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ManageOrdersViewModel extends ViewModel {

    MutableLiveData<List<OrderModel>> pendingOrdersListLiveData;
    MutableLiveData<List<OrderModel>> cancelOrdersListLiveData;
    MutableLiveData<List<OrderModel>> dispatchOrdersListLiveData;
    private static final String MAIN_NODE_NAME = "Orders";
    private static final String PENDING_NODE_NAME = "Pending";
    private static final String DISPATCH_NODE_NAME = "Dispatched";
    private static final String CANCEL_NODE_NAME = "Cancelled";
    private static final String COMPLETE_NODE_NAME = "Complete";
    private static final String PROCEEDING_NODE_NAME = "Proceeding";
    FirebaseDatabase database = FirebaseDatabase.getInstance();


    public MutableLiveData<List<OrderModel>> getCancelOrdersListLiveData() {
        if (cancelOrdersListLiveData == null) {
            cancelOrdersListLiveData = new MutableLiveData<>();
            cancelOrdersListLiveData.setValue(new ArrayList<>());
            getOrders(CANCEL_NODE_NAME, cancelOrdersListLiveData);
        }
        return cancelOrdersListLiveData;
    }

    public MutableLiveData<List<OrderModel>> getDispatchOrdersListLiveData() {
        if (dispatchOrdersListLiveData == null) {
            dispatchOrdersListLiveData = new MutableLiveData<>();
            dispatchOrdersListLiveData.setValue(new ArrayList<>());
            getOrders(DISPATCH_NODE_NAME, dispatchOrdersListLiveData);
        }
        return dispatchOrdersListLiveData;
    }

    public MutableLiveData<List<OrderModel>> getPendingOrdersListLiveData() {
        if (pendingOrdersListLiveData == null) {
            pendingOrdersListLiveData = new MutableLiveData<>();
            pendingOrdersListLiveData.setValue(new ArrayList<>());
            getOrders(PENDING_NODE_NAME, pendingOrdersListLiveData);
        }
        return pendingOrdersListLiveData;
    }

    private void getOrders(String nodeName, MutableLiveData<List<OrderModel>> listName) {
        database.getReference()
                .child(MAIN_NODE_NAME)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            try {
                                database.getReference().
                                        child(MAIN_NODE_NAME).
                                        child(snapshot1.getKey()).
                                        child(nodeName).addValueEventListener(new ValueEventListener() {
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
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // do nothing
                    }
                });
    }

    public void performOperation(OrderOperationsEnums operation,OrderModel model) {
        switch (operation) {
            case PROCEEDING_TO_CANCEL:
                // TODO
                break;
            case PROCEEDING_TO_DISPATCH:
                //TODO
                break;
            case DISPATCH_PROCEEDING:
                //TODO
                changeOrderStatus(model,DISPATCH_NODE_NAME,PROCEEDING_NODE_NAME,dispatchOrdersListLiveData,pendingOrdersListLiveData);
                break;
            case DISPATCH_TO_CANCEL:
                changeOrderStatus(model,DISPATCH_NODE_NAME,CANCEL_NODE_NAME,dispatchOrdersListLiveData,cancelOrdersListLiveData);
                break;
            case DISPATCH_TO_COMPLETE:
                changeOrderStatus(model,DISPATCH_NODE_NAME,COMPLETE_NODE_NAME,dispatchOrdersListLiveData,null);
                break;
            case PENDING_TO_PROCEEDING:
                //TODO
                break;
            case PENDING_TO_CANCEL:
                changeOrderStatus(model,PENDING_NODE_NAME,CANCEL_NODE_NAME,pendingOrdersListLiveData,cancelOrdersListLiveData);
                break;
            case PENDING_TO_DISPATCH:
                changeOrderStatus(model,PENDING_NODE_NAME,DISPATCH_NODE_NAME,pendingOrdersListLiveData,dispatchOrdersListLiveData);
                break;
            default:
                break;
        }
    }

    private void changeOrderStatus(OrderModel orderModel, String oldState, String newState,
                                   MutableLiveData<List<OrderModel>> oldDataList, MutableLiveData<List<OrderModel>> dataList) {
        database.getReference()
                .child(MAIN_NODE_NAME)
                .child(orderModel.getUserModel().getUserId())
                .child(oldState)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            OrderModel currentModel = snapshot1.getValue(OrderModel.class);
                            if (currentModel.getOrderId().equals(orderModel.getOrderId())) {
                                addInOrders(currentModel, newState, dataList);
                                deleteFromOrders(currentModel, oldState, oldDataList,snapshot1);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // do nothing
                    }
                });
    }

    private void deleteFromOrders(OrderModel orderModel, String oldState,
                                  MutableLiveData<List<OrderModel>> dataList,DataSnapshot dataSnapshot) {
        database.getReference().
                child(MAIN_NODE_NAME).
                child(orderModel.getUserModel().getUserId()).
                child(oldState).
                child(Objects.requireNonNull(dataSnapshot.getKey())).
                removeValue().addOnCompleteListener(runnable -> {
                    if (dataList != null) {
                        List<OrderModel> list =
                                new ArrayList<>(Objects.
                                        requireNonNull(dataList.
                                                getValue()));
                        list.remove(orderModel);
                        dataList.setValue(list);
                    }
                });
    }

    private void addInOrders(OrderModel model, String nodeName, MutableLiveData<List<OrderModel>> listMutableLiveData) {
        model.setStatus(nodeName);
        database.getReference()
                .child(MAIN_NODE_NAME)
                .child(model.getUserModel().getUserId())
                .child(nodeName)
                .push()
                .setValue(model)
                .addOnSuccessListener(aVoid ->
                        updateOrdersList(listMutableLiveData, model));
    }


    private void updateOrdersList(MutableLiveData<List<OrderModel>> listName, OrderModel orderModel) {
        if (listName != null) {
            List<OrderModel> list = new ArrayList<>(Objects.requireNonNull(listName.getValue()));
            list.add(orderModel);
            listName.setValue(list);
        }
    }

    public enum OrderOperationsEnums {
        PROCEEDING_TO_CANCEL,
        PROCEEDING_TO_DISPATCH,
        DISPATCH_PROCEEDING,
        DISPATCH_TO_CANCEL,
        DISPATCH_TO_COMPLETE,
        PENDING_TO_PROCEEDING,
        PENDING_TO_CANCEL,
        PENDING_TO_DISPATCH;
    }
}

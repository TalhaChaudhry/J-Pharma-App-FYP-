package com.talhachaudhry.jpharmaappfyp.view_models;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.talhachaudhry.jpharmaappfyp.models.OrderModel;
import com.talhachaudhry.jpharmaappfyp.utils.NodesNames;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ManageOrdersViewModel extends ViewModel {

    MutableLiveData<List<OrderModel>> pendingOrdersListLiveData;
    MutableLiveData<List<OrderModel>> cancelOrdersListLiveData;
    MutableLiveData<List<OrderModel>> dispatchOrdersListLiveData;
    MutableLiveData<List<OrderModel>> proceedingOrdersListLiveData;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static final String TAG = "Testing";

    public MutableLiveData<List<OrderModel>> getCancelOrdersListLiveData() {
        if (cancelOrdersListLiveData == null) {
            Log.e(TAG, "In  cancel order if condition");
            cancelOrdersListLiveData = new MutableLiveData<>();
            cancelOrdersListLiveData.setValue(new ArrayList<>());
            getOrders(NodesNames.CANCEL_NODE_NAME.getName(), cancelOrdersListLiveData);
        }
        Log.e(TAG, "In  cancel order out of  if condition");
        return cancelOrdersListLiveData;
    }

    public MutableLiveData<List<OrderModel>> getProceedingOrdersListLiveData() {
        if (proceedingOrdersListLiveData == null) {
            Log.e(TAG, "In  proceeding order if condition");
            proceedingOrdersListLiveData = new MutableLiveData<>();
            proceedingOrdersListLiveData.setValue(new ArrayList<>());
            getOrders(NodesNames.PROCEEDING_NODE_NAME.getName(), proceedingOrdersListLiveData);
        }
        Log.e(TAG, "In  proceeding order out of  if condition");
        return proceedingOrdersListLiveData;
    }

    public MutableLiveData<List<OrderModel>> getDispatchOrdersListLiveData() {
        if (dispatchOrdersListLiveData == null) {
            Log.e(TAG, "In  dispatch order  if condition");
            dispatchOrdersListLiveData = new MutableLiveData<>();
            dispatchOrdersListLiveData.setValue(new ArrayList<>());
            getOrders(NodesNames.DISPATCH_NODE_NAME.getName(), dispatchOrdersListLiveData);
        }
        Log.e(TAG, "In  dispatch order out of  if condition");
        return dispatchOrdersListLiveData;
    }

    public MutableLiveData<List<OrderModel>> getPendingOrdersListLiveData() {
        if (pendingOrdersListLiveData == null) {
            Log.e(TAG, "In  pending order  if condition");
            pendingOrdersListLiveData = new MutableLiveData<>();
            pendingOrdersListLiveData.setValue(new ArrayList<>());
            getOrders(NodesNames.PENDING_NODE_NAME.getName(), pendingOrdersListLiveData);
        }
        Log.e(TAG, "In  pending order out of  if condition");
        return pendingOrdersListLiveData;
    }

    private void getOrders(String nodeName, MutableLiveData<List<OrderModel>> listName) {
        Log.e(TAG, "In  get order for " + nodeName);
        database.getReference()
                .child(NodesNames.MAIN_NODE_NAME.getName())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            Log.e(TAG, "In  get order Loop");
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
                                                    Log.e(TAG, "In  get order inner Loop for " + model.getStatus());
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

    public void performOperation(OrderOperationsEnums operation, OrderModel model) {
        switch (operation) {
            case PROCEEDING_TO_CANCEL:
                changeOrderStatus(model, NodesNames.PROCEEDING_NODE_NAME.getName(),
                        NodesNames.CANCEL_NODE_NAME.getName(), proceedingOrdersListLiveData, cancelOrdersListLiveData);
                break;
            case PROCEEDING_TO_DISPATCH:
                changeOrderStatus(model, NodesNames.PROCEEDING_NODE_NAME.getName(),
                        NodesNames.DISPATCH_NODE_NAME.getName(), proceedingOrdersListLiveData, dispatchOrdersListLiveData);
                break;
            case PROCEEDING_TO_PENDING:
                changeOrderStatus(model, NodesNames.PROCEEDING_NODE_NAME.getName(),
                        NodesNames.PENDING_NODE_NAME.getName(), proceedingOrdersListLiveData, pendingOrdersListLiveData);
                break;
            case DISPATCH_PROCEEDING:
                changeOrderStatus(model, NodesNames.DISPATCH_NODE_NAME.getName(),
                        NodesNames.PROCEEDING_NODE_NAME.getName(), dispatchOrdersListLiveData, proceedingOrdersListLiveData);
                break;
            case DISPATCH_TO_CANCEL:
                changeOrderStatus(model, NodesNames.DISPATCH_NODE_NAME.getName(),
                        NodesNames.CANCEL_NODE_NAME.getName(), dispatchOrdersListLiveData, cancelOrdersListLiveData);
                break;
            case DISPATCH_TO_COMPLETE:
                changeOrderStatus(model, NodesNames.DISPATCH_NODE_NAME.getName(),
                        NodesNames.COMPLETE_NODE_NAME.getName(), dispatchOrdersListLiveData, null);
                break;
            case PENDING_TO_PROCEEDING:
                changeOrderStatus(model, NodesNames.PENDING_NODE_NAME.getName(),
                        NodesNames.PROCEEDING_NODE_NAME.getName(), pendingOrdersListLiveData, proceedingOrdersListLiveData);
                break;
            case PENDING_TO_CANCEL:
                changeOrderStatus(model, NodesNames.PENDING_NODE_NAME.getName(),
                        NodesNames.CANCEL_NODE_NAME.getName(), pendingOrdersListLiveData, cancelOrdersListLiveData);
                break;
            case PENDING_TO_DISPATCH:
                changeOrderStatus(model, NodesNames.PENDING_NODE_NAME.getName(),
                        NodesNames.DISPATCH_NODE_NAME.getName(), pendingOrdersListLiveData, dispatchOrdersListLiveData);
                break;
            default:
                break;
        }
    }

    private void changeOrderStatus(OrderModel orderModel, String oldState, String newState,
                                   MutableLiveData<List<OrderModel>> oldDataList, MutableLiveData<List<OrderModel>> dataList) {
        Log.e(TAG, "Operation old " + oldState + " to new " + newState);
        database.getReference()
                .child(NodesNames.MAIN_NODE_NAME.getName())
                .child(orderModel.getUserModel().getUserId())
                .child(oldState)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            OrderModel currentModel = snapshot1.getValue(OrderModel.class);
                            if (currentModel.getOrderId().equals(orderModel.getOrderId())) {
                                addInOrders(orderModel, newState, dataList);
                                deleteFromOrders(orderModel, oldState, oldDataList, snapshot1);
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
                                  MutableLiveData<List<OrderModel>> dataList, DataSnapshot dataSnapshot) {
        Log.e(TAG, "In delete order from " + oldState);
        database.getReference().
                child(NodesNames.MAIN_NODE_NAME.getName()).
                child(orderModel.getUserModel().getUserId()).
                child(oldState).
                child(Objects.requireNonNull(dataSnapshot.getKey())).
                removeValue().
                addOnCompleteListener(runnable -> {
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
        Log.e(TAG, "Change Status to " + nodeName);
        database.getReference()
                .child(NodesNames.MAIN_NODE_NAME.getName())
                .child(model.getUserModel().getUserId())
                .child(nodeName)
                .push()
                .setValue(model).
                addOnCompleteListener(runnable -> updateOrdersList(listMutableLiveData, model));
    }


    private void updateOrdersList(MutableLiveData<List<OrderModel>> listName, OrderModel orderModel) {
        if (listName != null) {
            Log.e(TAG, "In update order list for" + orderModel.getStatus());
            List<OrderModel> list = new ArrayList<>(Objects.requireNonNull(listName.getValue()));
            list.add(orderModel);
            listName.setValue(list);
        }
    }

    public enum OrderOperationsEnums {
        PROCEEDING_TO_CANCEL,
        PROCEEDING_TO_DISPATCH,
        PROCEEDING_TO_PENDING,
        DISPATCH_PROCEEDING,
        DISPATCH_TO_CANCEL,
        DISPATCH_TO_COMPLETE,
        PENDING_TO_PROCEEDING,
        PENDING_TO_CANCEL,
        PENDING_TO_DISPATCH;
    }
}

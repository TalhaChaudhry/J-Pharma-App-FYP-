package com.talhachaudhry.jpharmaappfyp.view_models;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.talhachaudhry.jpharmaappfyp.R;
import com.talhachaudhry.jpharmaappfyp.models.CartModel;
import com.talhachaudhry.jpharmaappfyp.models.ManageMedicineModel;
import com.talhachaudhry.jpharmaappfyp.models.OrderModel;
import com.talhachaudhry.jpharmaappfyp.models.UserModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PlaceOrderViewModel extends AndroidViewModel {
    FirebaseAuth auth;
    MutableLiveData<List<CartModel>> liveData;
    MutableLiveData<Boolean> isTaskPerformed;
    private static final String NODE_NAME = "Orders";
    private static final String MEDICINE_NODE = "Medicines";
    FirebaseDatabase database;
    Application application;

    public PlaceOrderViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }

    public LiveData<List<CartModel>> getCartList() {
        if (liveData == null) {
            liveData = new MutableLiveData<>();
            liveData.setValue(new ArrayList<>());
            auth = FirebaseAuth.getInstance();
            database = FirebaseDatabase.getInstance();
        }
        return liveData;
    }

    public MutableLiveData<Boolean> getIsTaskPerformed() {
        if (isTaskPerformed == null) {
            isTaskPerformed = new MutableLiveData<>();
        }
        return isTaskPerformed;
    }

    public void resetCart() {
        if (getCartList() != null) {
            liveData.setValue(new ArrayList<>());
        }
    }

    public void addToCart(CartModel model) {
        if (getCartList() != null) {
            List<CartModel> list = new ArrayList<>(Objects.requireNonNull(liveData.getValue()));
            list.add(model);
            liveData.setValue(list);
        }
    }

    public void removeFromCart(CartModel model) {
        if (getCartList() != null && model!=null) {
            List<CartModel> list = new ArrayList<>(Objects.requireNonNull(liveData.getValue()));
            list.remove(model);
            liveData.setValue(list);
        }
    }

    private void orderForCurrentUser() {
        database.getReference().child("Users").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                makeOrder(snapshot.getValue(UserModel.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Do Nothing
            }
        });
    }

    public void placeOrder() {
        orderForCurrentUser();
    }

    @SuppressLint("SimpleDateFormat")
    private void makeOrder(UserModel model) {
        SimpleDateFormat formatter =
                new SimpleDateFormat(application.getString(R.string.date_time_format));
        SimpleDateFormat formatterForModel =
                new SimpleDateFormat(application.getString(R.string.date_time_format_for_model));
        SimpleDateFormat yearFormatter =
                new SimpleDateFormat(application.getString(R.string.year_format_for_model));
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        String orderId = auth.getUid() + formatter.format(date);
        database.getReference()
                .child(NODE_NAME)
                .child(auth.getUid())
                .child("Pending")
                .push()
                .setValue(new OrderModel(liveData.getValue(), orderId, "Pending",
                        model, formatterForModel.format(date), cal.get(Calendar.MONTH),
                        Integer.parseInt(yearFormatter.format(date))));
        updateStock();
    }

    private void updateStock() {
        Log.e("testing", "out");
        for (CartModel model : liveData.getValue()) {
            Log.e("testing", "in");
            updateMedicine(model.getModel(), model.getQuantity());
        }
        resetCart();
        if (getIsTaskPerformed() != null) {
            isTaskPerformed.setValue(true);
        }
    }

    public void updateMedicine(ManageMedicineModel model, int quantity) {
        database.getReference()
                .child(MEDICINE_NODE)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            ManageMedicineModel currentModel = snapshot1.getValue(ManageMedicineModel.class);
                            if (currentModel.getName().equals(model.getName()) &&
                                    (currentModel.getStock() - quantity) >= 0) {
                                Map<String, Object> values = new HashMap<>();
                                values.put("stock", (currentModel.getStock() - quantity));
                                database.getReference()
                                        .child(MEDICINE_NODE).
                                        child(Objects.requireNonNull(snapshot1.getKey()))
                                        .updateChildren(values);
                                Log.e("testing", "changed");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // do nothing
                    }
                });
    }

}

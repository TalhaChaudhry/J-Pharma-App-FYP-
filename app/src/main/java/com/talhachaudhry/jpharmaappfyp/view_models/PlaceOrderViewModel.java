package com.talhachaudhry.jpharmaappfyp.view_models;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.talhachaudhry.jpharmaappfyp.R;
import com.talhachaudhry.jpharmaappfyp.models.CartModel;
import com.talhachaudhry.jpharmaappfyp.models.OrderModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class PlaceOrderViewModel extends AndroidViewModel {
    FirebaseAuth auth;
    MutableLiveData<List<CartModel>> liveData;
    private static final String NODE_NAME = "Orders";
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
            auth=FirebaseAuth.getInstance();
            database = FirebaseDatabase.getInstance();
        }
        return liveData;
    }

    public void addToCart(CartModel model) {
        if (liveData != null) {
            List<CartModel> list = new ArrayList<>(Objects.requireNonNull(liveData.getValue()));
            list.add(model);
            liveData.setValue(list);
        }
    }

    public void removeFromCart(CartModel model) {
        if (liveData != null) {
            List<CartModel> list = new ArrayList<>(Objects.requireNonNull(liveData.getValue()));
            list.remove(model);
            liveData.setValue(list);
        }
    }

    public void placeOrder() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter =
                new SimpleDateFormat(application.getString(R.string.date_time_format));
        Date date = new Date();
        String orderId = auth.getUid() + formatter.format(date);
        database.getReference()
                .child(NODE_NAME)
                .child(Objects.requireNonNull(auth.getUid()))
                .child("Pending")
                .push()
                .setValue(new OrderModel(liveData.getValue(), orderId, "Pending"));
    }
}

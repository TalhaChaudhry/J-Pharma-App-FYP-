package com.talhachaudhry.jpharmaappfyp.view_models;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.talhachaudhry.jpharmaappfyp.models.UserModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class EditUserProfileViewModel extends ViewModel {
    MutableLiveData<UserModel> userModelMutableLiveData;
    private static final String MAIN_NODE = "Users";
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    public MutableLiveData<UserModel> getUserModelMutableLiveData() {
        if (userModelMutableLiveData == null) {
            userModelMutableLiveData = new MutableLiveData<>();
            getUserData();
        }
        return userModelMutableLiveData;
    }

    public void upDateUserModel(UserModel model) {
        database.getReference()
                .child(MAIN_NODE)
                .child(auth.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Map<String, Object> values = new HashMap<>();
                        values.put("shopName", model.getShopName());
                        values.put("userName", model.getUserName());
                        values.put("city", model.getCity());
                        values.put("contact", model.getContact());
                        values.put("email", model.getEmail());
                        values.put("profilePic", model.getProfilePic());
                        values.put("address", model.getAddress());
                        database.getReference()
                                .child(MAIN_NODE)
                                .child(auth.getUid())
                                .child(Objects.requireNonNull(snapshot.getKey()))
                                .updateChildren(values);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // do nothing
                    }
                });
    }

    private void getUserData() {
        try {
            database.getReference().
                    child(MAIN_NODE).
                    child(auth.getUid()).
                    addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            UserModel model = snapshot.getValue(UserModel.class);
                            setUserModelMutableLiveData(model);
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

    private void setUserModelMutableLiveData(UserModel model) {
        if (userModelMutableLiveData != null) {
            userModelMutableLiveData.setValue(model);
        }
    }
}

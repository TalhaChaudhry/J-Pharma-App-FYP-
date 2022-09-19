package com.talhachaudhry.jpharmaappfyp.view_models;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.talhachaudhry.jpharmaappfyp.models.UserModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ViewWholesalersViewModel extends ViewModel {

    MutableLiveData<List<UserModel>> userModelList;
    MutableLiveData<byte[]> profileImage;
    FirebaseDatabase database;
    private static final String NODE_NAME = "Users";

    public LiveData<List<UserModel>> getUsersModel() {
        if (userModelList == null) {
            userModelList = new MutableLiveData<>();
            userModelList.setValue(new ArrayList<>());
            database = FirebaseDatabase.getInstance();
            getFromDB();
        }
        return userModelList;
    }

    public MutableLiveData<byte[]> getProfileImage() {
        if (profileImage == null) {
            profileImage = new MutableLiveData<>();
        }
        return profileImage;
    }

    public void getProfilePic(UserModel model) {
        StorageReference ref
                = FirebaseStorage.getInstance().getReference()
                .child(model.getProfilePic());
        final long ONE_MEGABYTE = 1024 * 1024;
        ref.getBytes(ONE_MEGABYTE).addOnSuccessListener(bytes -> {
            if (profileImage != null) {
                profileImage.setValue(bytes);
            }
        }).addOnFailureListener(exception -> {
            // do nothing
        });
    }

    private void getFromDB() {
        database.getReference()
                .child(NODE_NAME)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            try {
                                UserModel model = snapshot1.getValue(UserModel.class);
                                updateList(model);
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

    private void updateList(UserModel model) {
        if (getUsersModel() != null) {
            List<UserModel> list = new ArrayList<>
                    (Objects.requireNonNull(userModelList.getValue()));
            list.add(model);
            userModelList.setValue(list);
        }
    }

}

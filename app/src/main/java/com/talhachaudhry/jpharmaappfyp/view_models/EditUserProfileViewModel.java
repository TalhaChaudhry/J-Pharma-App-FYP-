package com.talhachaudhry.jpharmaappfyp.view_models;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.talhachaudhry.jpharmaappfyp.models.UserModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;


public class EditUserProfileViewModel extends ViewModel {
    MutableLiveData<UserModel> userModelMutableLiveData;
    MutableLiveData<byte[]> profileImage;
    MutableLiveData<Boolean> notifyUser;
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

    public MutableLiveData<Boolean> getNotifyUser() {
        if (notifyUser == null) {
            notifyUser = new MutableLiveData<>();
        }
        return notifyUser;
    }
    public MutableLiveData<byte[]> getProfileImage() {
        if (profileImage == null) {
            profileImage = new MutableLiveData<>();
        }
        return profileImage;
    }
    public void upDateUserModel(String shopName, String userName, String city, String contact, String address) {
        database.getReference()
                .child(MAIN_NODE)
                .child(auth.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Map<String, Object> values = new HashMap<>();
                        values.put("shopName", shopName);
                        values.put("userName", userName);
                        values.put("city", city);
                        values.put("contact", contact);
                        values.put("address", address);
                        database.getReference()
                                .child(MAIN_NODE)
                                .child(Objects.requireNonNull(snapshot.getKey()))
                                .updateChildren(values).
                                addOnCompleteListener(runnable -> getUserData());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // do nothing
                    }
                });
    }

    public void updateProfile(String image) {
        database.getReference()
                .child(MAIN_NODE)
                .child(auth.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Map<String, Object> values = new HashMap<>();
                        values.put("profilePic", image);
                        values.put("isInStorage", 1);
                        database.getReference()
                                .child(MAIN_NODE)
                                .child(Objects.requireNonNull(snapshot.getKey()))
                                .updateChildren(values).
                                addOnCompleteListener(runnable -> getUserData());
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        if (getNotifyUser() != null) {
                            notifyUser.setValue(false);
                        }
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
                            if (model.getIsInStorage() == 1) {
                                getProfilePic(model);
                            }
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

    public void uploadImage(byte[] data) {
        StorageReference ref
                = FirebaseStorage.getInstance().getReference()
                .child("images/" + UUID.randomUUID().toString());
        UploadTask uploadTask = ref.putBytes(data);
        uploadTask.addOnFailureListener(exception -> {
            if (getNotifyUser() != null) {
                notifyUser.setValue(true);
            }
        }).addOnSuccessListener(taskSnapshot -> updateProfile(taskSnapshot.getMetadata().getPath()));
    }
}

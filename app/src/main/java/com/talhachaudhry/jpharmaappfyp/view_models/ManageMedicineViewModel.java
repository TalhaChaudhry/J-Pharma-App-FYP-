package com.talhachaudhry.jpharmaappfyp.view_models;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.talhachaudhry.jpharmaappfyp.models.ManageMedicineModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ManageMedicineViewModel extends ViewModel {
    MutableLiveData<List<ManageMedicineModel>> manageMedicineModelMutableLiveData;
    FirebaseDatabase database;
    private static final String NODE_NAME = "Medicines";

    public LiveData<List<ManageMedicineModel>> getManageMedicineViewModelMutableLiveData() {
        if (manageMedicineModelMutableLiveData == null) {
            manageMedicineModelMutableLiveData = new MutableLiveData<>();
            manageMedicineModelMutableLiveData.setValue(new ArrayList<>());
            database = FirebaseDatabase.getInstance();
            getFromDB();
        }
        return manageMedicineModelMutableLiveData;
    }

    public void addMedicine(ManageMedicineModel model) {
        addToDB(model);
    }

    public void updateMedicine(ManageMedicineModel model) {
        database.getReference()
                .child(NODE_NAME)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            ManageMedicineModel currentModel = snapshot1.getValue(ManageMedicineModel.class);
                            if (currentModel.getName().equals(model.getName())) {
                                Map<String, Object> values = new HashMap<>();
                                values.put("imagePath", model.getImagePath());
                                values.put("detail", model.getDetail());
                                values.put("mg", model.getMg());
                                values.put("price", model.getPrice());
                                database.getReference()
                                        .child(NODE_NAME).
                                        child(Objects.requireNonNull(snapshot1.getKey()))
                                        .updateChildren(values);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // do nothing
                    }
                });
    }


    public void deleteMedicine(ManageMedicineModel model) {
        deleteFromDB(model);
    }

    private void addToDB(ManageMedicineModel model) {
        database.getReference()
                .child(NODE_NAME)
                .push()
                .setValue(model)
                .addOnSuccessListener(aVoid ->
                        updateList(model));
    }

    private void updateList(ManageMedicineModel model) {
        if (getManageMedicineViewModelMutableLiveData() != null) {
            List<ManageMedicineModel> list = new ArrayList<>
                    (Objects.requireNonNull(manageMedicineModelMutableLiveData.getValue()));
            list.add(model);
            manageMedicineModelMutableLiveData.setValue(list);
        }
    }

    private void deleteFromDB(ManageMedicineModel model) {
        database.getReference()
                .child(NODE_NAME)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            ManageMedicineModel currentModel = snapshot1.getValue(ManageMedicineModel.class);
                            if (currentModel.getName().equals(model.getName())) {
                                database.getReference().
                                        child(NODE_NAME).
                                        child(Objects.requireNonNull(snapshot1.getKey())).
                                        removeValue().addOnCompleteListener(runnable -> {
                                            if (getManageMedicineViewModelMutableLiveData() != null) {
                                                List<ManageMedicineModel> list =
                                                        new ArrayList<>(Objects.
                                                                requireNonNull(manageMedicineModelMutableLiveData.
                                                                        getValue()));
                                                list.remove(model);
                                                manageMedicineModelMutableLiveData.setValue(list);
                                            }
                                        });
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // do nothing
                    }
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
                                ManageMedicineModel model = snapshot1.getValue(ManageMedicineModel.class);
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
}

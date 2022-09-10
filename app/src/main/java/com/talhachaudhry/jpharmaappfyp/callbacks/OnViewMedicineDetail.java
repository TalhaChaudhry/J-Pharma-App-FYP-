package com.talhachaudhry.jpharmaappfyp.callbacks;

import com.talhachaudhry.jpharmaappfyp.models.ManageMedicineModel;

public interface OnViewMedicineDetail {
    void onViewMedicineDetailClicked(ManageMedicineModel model);

    void deleteMedicine(ManageMedicineModel model);

    void updateMedicine(ManageMedicineModel model);
}

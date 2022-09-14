package com.talhachaudhry.jpharmaappfyp.callbacks;

import com.talhachaudhry.jpharmaappfyp.models.OrderModel;

public interface AdminProceedingCallbacks {
    void onCancelClicked(OrderModel model);

    void onDispatchClicked(OrderModel model);

    void onPendingClicked(OrderModel model);

    void onClickedToView(OrderModel model);
}

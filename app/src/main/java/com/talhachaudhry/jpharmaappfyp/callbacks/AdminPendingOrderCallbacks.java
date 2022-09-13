package com.talhachaudhry.jpharmaappfyp.callbacks;

import com.talhachaudhry.jpharmaappfyp.models.OrderModel;

public interface AdminPendingOrderCallbacks {
    void onProceedOrderClicker(OrderModel model);

    void onDispatchOrderClicker(OrderModel model);

    void onCancelOrderClicker(OrderModel model);

    void onClickedToView(OrderModel orderModel);
}

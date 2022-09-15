package com.talhachaudhry.jpharmaappfyp.callbacks;

import com.talhachaudhry.jpharmaappfyp.models.OrderModel;

public interface EditOrderCallbacks {

    void onViewOrderClicked(OrderModel model);

    void onDeleteOrderClicked(OrderModel model);

    void onUpdateOrderClicked(OrderModel model);
}

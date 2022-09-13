package com.talhachaudhry.jpharmaappfyp.callbacks;

import com.talhachaudhry.jpharmaappfyp.models.OrderModel;

public interface CancelledOrdersCallback {
    void onItemClicked(OrderModel model);
}

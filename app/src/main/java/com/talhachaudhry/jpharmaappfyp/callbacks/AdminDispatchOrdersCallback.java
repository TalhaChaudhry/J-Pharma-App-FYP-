package com.talhachaudhry.jpharmaappfyp.callbacks;

import com.talhachaudhry.jpharmaappfyp.models.OrderModel;

public interface AdminDispatchOrdersCallback {
    void onCancelClicked(OrderModel model);

    void onCompleteClicked(OrderModel model);

    void onClickedToView(OrderModel model);

    void putInProceeding(OrderModel orderModel);
}

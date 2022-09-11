package com.talhachaudhry.jpharmaappfyp.callbacks;

import com.talhachaudhry.jpharmaappfyp.models.CartModel;

public interface CartCallback {
    void onQuantityUpdated(CartModel model);

    void onDeleteClicked(CartModel model);
}

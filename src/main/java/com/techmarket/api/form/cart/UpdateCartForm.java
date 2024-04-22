package com.techmarket.api.form.cart;

import com.techmarket.api.form.cart.cartDetail.UpdateCartDetailForm;
import lombok.Data;

import java.util.List;

@Data
public class UpdateCartForm {

    private List<UpdateCartDetailForm> cartDetails;
}

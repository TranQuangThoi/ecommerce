package com.techmarket.api.form.productVariant;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class CreateProductVariantForm {
    @NotNull(message = "price cant not be empty")
    @ApiModelProperty(name = "price",required = true)
    private Double price;
    @NotEmpty(message = "color cant not be empty")
    @ApiModelProperty(name = "color", required = true)
    private String color;
    @ApiModelProperty(name = "image")
    private String image;
    @NotNull(message = "amount cant not be empty")
    @ApiModelProperty(name = "amount",required = true)
    private Integer totalStock;
    @ApiModelProperty(name = "status")
    private Integer status;
    @ApiModelProperty(name = "productId",required = true)
    private Long productId;

}

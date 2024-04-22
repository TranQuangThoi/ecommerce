package com.techmarket.api.form.product;

import com.techmarket.api.form.productVariant.CreateProductVariantForm;
import com.techmarket.api.model.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel
public class CreateProductForm {

    @NotEmpty(message = "name cant not be null")
    @ApiModelProperty(name = "name", required = true)
    private String name;

    @NotNull(message = "price cant not be null")
    @ApiModelProperty(name = "price", required = true)
    private Double price;

    @ApiModelProperty(name = "description")
    private String description;

    @ApiModelProperty(name = "image")
    private String image;

    @ApiModelProperty(name = "saleOff")
    private Double saleOff;

    @ApiModelProperty(name = "brandId")
    private Long brandId;

    @NotNull(message = "categoryId cant not be null")
    @ApiModelProperty(name = "categoryId", required = true)
    private Long categoryId;

    @ApiModelProperty(name = "status")
    private Integer status;

    @ApiModelProperty(name = "List product varriant")
    private List<@Valid CreateProductVariantForm> listDetails;

    @ApiModelProperty(name = "List product related",required = false)
    Long[] relatedProducts;

}

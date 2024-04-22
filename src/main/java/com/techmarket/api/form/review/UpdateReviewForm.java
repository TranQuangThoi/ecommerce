package com.techmarket.api.form.review;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
public class UpdateReviewForm {

    @NotNull(message = "Id can not empty")
    @ApiModelProperty(name = "Id", required = true)
    private Long id;

    @NotNull(message = "status cannot be null")
    @ApiModelProperty(name = "status", required = true)
    private Integer status;

}

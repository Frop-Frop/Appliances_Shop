package com.foxminded.appliancesshop.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

	private Long id;
	@ApiModelProperty(required = true)
	private String name;
	@ApiModelProperty(required = true)
	private Long categoryId;
	@ApiModelProperty(required = true)
	private Integer price;
	@ApiModelProperty(required = true)
	private String brand;
	@ApiModelProperty(required = true)
	private String description;
	@ApiModelProperty(required = true)
	private int unitsLeftInWarehouse;

}

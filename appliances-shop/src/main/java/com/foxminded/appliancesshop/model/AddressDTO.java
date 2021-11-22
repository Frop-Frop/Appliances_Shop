package com.foxminded.appliancesshop.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

	private Long id;
	@ApiModelProperty(value = "Customer's id", required = true)
	private Long customerId;
	@ApiModelProperty(required = true)
	private String country;
	@ApiModelProperty(required = true)
	private String region;
	@ApiModelProperty(required = true)
	private String city;
	@ApiModelProperty(required = true)
	private String street;
	@ApiModelProperty(required = true)
	private String houseNumber;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id.intValue();
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((region == null) ? 0 : region.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((street == null) ? 0 : street.hashCode());
		result = prime * result + ((houseNumber == null) ? 0 : houseNumber.hashCode());
		return result;
	}

}

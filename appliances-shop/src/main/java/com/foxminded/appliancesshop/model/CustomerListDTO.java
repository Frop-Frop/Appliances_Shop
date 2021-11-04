package com.foxminded.appliancesshop.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerListDTO {

	/*
	 * @Autowired private CustomerMapperImpl mapper;
	 */

	private List<CustomerDTO> customers;

	public CustomerListDTO(List<CustomerDTO> customerList) {
		this.customers = customerList/* .stream().map(mapper::customerToCustomerDTO).collect(Collectors.toList()) */;
	}

}

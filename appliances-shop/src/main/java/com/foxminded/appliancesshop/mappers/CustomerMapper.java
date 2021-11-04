package com.foxminded.appliancesshop.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.foxminded.appliancesshop.domain.Customer;
import com.foxminded.appliancesshop.model.CustomerDTO;

@Mapper
public interface CustomerMapper {
	CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

	@Mapping(source = "id", target = "id")
	CustomerDTO customerToCustomerDTO(Customer customer);

	Customer customerDTOtoCustomer(CustomerDTO customerDTO);

}

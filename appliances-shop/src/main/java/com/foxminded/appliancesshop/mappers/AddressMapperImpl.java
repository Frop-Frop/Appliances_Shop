package com.foxminded.appliancesshop.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.foxminded.appliancesshop.domain.Address;
import com.foxminded.appliancesshop.domain.Customer;
import com.foxminded.appliancesshop.model.AddressDTO;
import com.foxminded.appliancesshop.model.CustomerDTO;

@Component
public class AddressMapperImpl implements AddressMapper {

	@Autowired
	private CustomerMapperImpl customerMapper;

	@Override
	public AddressDTO addressToAddressDTO(Address address) {
		if (address == null) {
			return null;
		}
		CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(address.getCustomer());
		AddressDTO addressDTO = new AddressDTO(address.getId(), customerDTO, address.getCountry(), address.getRegion(),
				address.getCity(), address.getStreet(), address.getHouseNumber());
		return addressDTO;
	}

	@Override
	public Address addressDTOtoAddress(AddressDTO addressDTO) {
		if (addressDTO == null) {
			return null;
		}
		Customer customer = customerMapper.customerDTOtoCustomer(addressDTO.getCustomer());
		Address address = new Address(addressDTO.getId(), customer, addressDTO.getCountry(), addressDTO.getRegion(),
				addressDTO.getCity(), addressDTO.getStreet(), addressDTO.getHouseNumber());
		return address;
	}

}

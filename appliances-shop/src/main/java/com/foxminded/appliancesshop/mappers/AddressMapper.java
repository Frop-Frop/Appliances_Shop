package com.foxminded.appliancesshop.mappers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.foxminded.appliancesshop.domain.Address;
import com.foxminded.appliancesshop.domain.Customer;
import com.foxminded.appliancesshop.model.AddressDTO;
import com.foxminded.appliancesshop.repositories.CustomerRepository;
import com.foxminded.appliancesshop.services.ResourseNotFoundException;

@Component
public class AddressMapper {

	@Autowired
	private CustomerRepository customerRepository;

	public AddressDTO addressToAddressDTO(Address address) {
		if (address == null) {
			return null;
		}
		AddressDTO addressDTO = new AddressDTO(address.getId(), address.getCustomer().getId(), address.getCountry(),
				address.getRegion(), address.getCity(), address.getStreet(), address.getHouseNumber());
		return addressDTO;
	}

	public Address addressDTOtoAddress(AddressDTO addressDTO) {
		if (addressDTO == null) {
			return null;
		}
		Optional<Customer> customer = customerRepository.findById(addressDTO.getCustomerId());
		if (customer.isEmpty()) {
			throw new ResourseNotFoundException("Customer with id: " + addressDTO.getCustomerId() + " not found");
		}
		Address address = new Address(addressDTO.getId(), customer.get(), addressDTO.getCountry(),
				addressDTO.getRegion(), addressDTO.getCity(), addressDTO.getStreet(), addressDTO.getHouseNumber());
		return address;
	}

	public AddressDTO addressToAddressDTOWithoutCustomer(Address address) {
		if (address == null) {
			return null;
		}
		AddressDTO addressDTO = new AddressDTO(address.getId(), address.getCustomer().getId(), address.getCountry(),
				address.getRegion(), address.getCity(), address.getStreet(), address.getHouseNumber());
		return addressDTO;
	}

	public Address addressDTOtoAddressWithoutCustomer(AddressDTO addressDTO) {
		if (addressDTO == null) {
			return null;
		}
		Address address = new Address(addressDTO.getId(), addressDTO.getCountry(), addressDTO.getRegion(),
				addressDTO.getCity(), addressDTO.getStreet(), addressDTO.getHouseNumber());
		return address;
	}

}

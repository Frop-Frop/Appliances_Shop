package com.foxminded.appliancesshop.model;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.foxminded.appliancesshop.domain.Address;
import com.foxminded.appliancesshop.mappers.AddressMapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddressListDTO {

	@Autowired
	private AddressMapper mapper;

	private List<AddressDTO> addresses;

	public AddressListDTO(List<Address> addressList) {

		this.addresses = addressList.stream().map(mapper::addressToAddressDTO).collect(Collectors.toList());

	}

}

package com.foxminded.appliancesshop.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddressListDTO {

	private List<AddressDTO> addresses;

	public AddressListDTO(List<AddressDTO> addressList) {

		this.addresses = addressList;

	}

}

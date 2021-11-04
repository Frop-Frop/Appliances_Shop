package com.foxminded.appliancesshop.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.foxminded.appliancesshop.domain.Address;
import com.foxminded.appliancesshop.model.AddressDTO;

@Mapper
public interface AddressMapper {

	AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

	@Mapping(source = "id", target = "id")
	AddressDTO addressToAddressDTO(Address address);

	Address addressDTOtoAddress(AddressDTO addressDTO);

}

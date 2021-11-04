package com.foxminded.appliancesshop.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.foxminded.appliancesshop.domain.Category;
import com.foxminded.appliancesshop.model.CategoryDTO;

@Mapper
public interface CategoryMapper {

	CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

	@Mapping(source = "id", target = "id")
	CategoryDTO categoryToCategoryDTO(Category category);

	Category categoryDTOtoCategory(CategoryDTO categoryDTO);

}

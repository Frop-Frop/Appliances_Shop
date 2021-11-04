package com.foxminded.appliancesshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.appliancesshop.domain.Item;
import com.foxminded.appliancesshop.mappers.CartMapper;
import com.foxminded.appliancesshop.mappers.CustomerMapperImpl;
import com.foxminded.appliancesshop.mappers.ItemMapper;
import com.foxminded.appliancesshop.mappers.ProductMapper;
import com.foxminded.appliancesshop.model.ItemDTO;
import com.foxminded.appliancesshop.model.ItemListDTO;
import com.foxminded.appliancesshop.repositories.ItemRepository;

@Service
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private ItemMapper itemMapper;

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private CartMapper cartMapper;

	@Autowired
	private CustomerMapperImpl customerMapper;

	public ItemListDTO getAllItemsInCart(Long id) {
		return new ItemListDTO(itemRepository.findAll());
	}

	public ItemDTO getItemById(Long id) {
		return itemRepository.findById(id).map(itemMapper::itemToItemDTO).orElseThrow(ResourseNotFoundException::new);
	}

	public ItemDTO createNewItem(ItemDTO itemDTO) {
		Item item = itemMapper.itemDTOtoItem(itemDTO);
		Item savedItem = itemRepository.save(item);
		return itemMapper.itemToItemDTO(savedItem);
	}

	public ItemDTO saveItemByDTO(Long id, ItemDTO itemDTO) {
		Item item = itemRepository.findById(id).get();
		if (item == null) {
			throw new ResourseNotFoundException();
		}
		item = itemMapper.itemDTOtoItem(itemDTO);
		item.setId(id);
		return itemMapper.itemToItemDTO(itemRepository.save(item));
	}

	public ItemDTO patchItem(Long id, ItemDTO itemDTO) {
		Item item = itemRepository.getById(id);
		if (item == null) {
			throw new ResourseNotFoundException();
		}
		if (item.getQuantity() == null) {
			item.setQuantity(itemDTO.getQuantity());
		}
		if (item.getProduct() == null) {
			item.setProduct(productMapper.productDTOtoProduct(itemDTO.getProduct()));
		}
		if (item.getCart() == null) {
			item.setCart(cartMapper.cartDTOtoCart(itemDTO.getCart()));
		}
		if (item.getCustomer() == null) {
			item.setCustomer(customerMapper.customerDTOtoCustomer(itemDTO.getCustomer()));
		}
		return itemMapper.itemToItemDTO(itemRepository.save(item));
	}

	public void deleteById(Long id) {
		Item item = itemRepository.getById(id);
		itemRepository.delete(item);
	}

}

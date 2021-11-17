package com.foxminded.appliancesshop.services;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.appliancesshop.domain.Item;
import com.foxminded.appliancesshop.mappers.ItemMapper;
import com.foxminded.appliancesshop.model.ItemDTO;
import com.foxminded.appliancesshop.model.ItemListDTO;
import com.foxminded.appliancesshop.repositories.CartRepository;
import com.foxminded.appliancesshop.repositories.CustomerRepository;
import com.foxminded.appliancesshop.repositories.ItemRepository;
import com.foxminded.appliancesshop.repositories.ProductRepository;

@Service
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private ItemMapper itemMapper;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CustomerRepository customerRepository;

	public ItemListDTO getAllItemsInCart(Long id) {
		return new ItemListDTO(itemRepository.findAllItemsInCart(id).stream().map(itemMapper::itemToItemDTO)
				.collect(Collectors.toList()));
	}

	public ItemListDTO getAllItemsInOrder(Long id) {
		return new ItemListDTO(itemRepository.findAllItemsInOrder(id).stream().map(itemMapper::itemToItemDTO)
				.collect(Collectors.toList()));
	}

	public ItemListDTO getCustomerDeferreds(Long id) {
		return new ItemListDTO(itemRepository.findCustomerDeferreds(id).stream().map(itemMapper::itemToItemDTO)
				.collect(Collectors.toList()));
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
			item.setProduct(productRepository.getById(itemDTO.getProduct().getId()));
		}
		if (item.getCart() == null) {
			item.setCart(cartRepository.getById(itemDTO.getCartId()));
		}
		if (item.getCustomer() == null) {
			item.setCustomer(customerRepository.getById(itemDTO.getCustomerId()));
		}
		return itemMapper.itemToItemDTO(itemRepository.save(item));
	}

	public void deleteById(Long id) {
		Item item = itemRepository.getById(id);
		itemRepository.delete(item);
	}

}

package com.foxminded.appliancesshop.services;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger log = LoggerFactory.getLogger(ItemService.class);

	public ItemListDTO getAllItemsInCart(Long id) {
		log.debug("getAllItemsInCart() called in ItemService with cart id: " + id);
		return new ItemListDTO(
				itemRepository.findByCartId(id).stream().map(itemMapper::itemToItemDTO).collect(Collectors.toList()));
	}

	public ItemListDTO getAllItemsInOrder(Long id) {
		log.debug("getAllItemsInOrder() called in ItemService with order id: " + id);
		return new ItemListDTO(
				itemRepository.findByOrderId(id).stream().map(itemMapper::itemToItemDTO).collect(Collectors.toList()));
	}

	public ItemListDTO getCustomerDeferreds(Long id) {
		log.debug("getAllItemsInOrder() called in ItemService with order id: " + id);
		return new ItemListDTO(itemRepository.findDistinctByCustomerId(id).stream().map(itemMapper::itemToItemDTO)
				.collect(Collectors.toList()));
	}

	public ItemDTO getItemById(Long id) {
		log.debug("getItemById() called in ItemService with item id: " + id);
		return itemRepository.findById(id).map(itemMapper::itemToItemDTO).orElseThrow(ResourseNotFoundException::new);
	}

	public ItemDTO createNewItem(ItemDTO itemDTO) {
		log.debug("getItemById() called in ItemService with itemDTO: " + itemDTO);
		Item item = itemMapper.itemDTOtoItem(itemDTO);
		Item savedItem = itemRepository.save(item);
		return itemMapper.itemToItemDTO(savedItem);
	}

	public ItemDTO saveItemByDTO(Long id, ItemDTO itemDTO) {
		log.debug("saveItemByDTO() called in ItemService with itemDTO: " + itemDTO + " and item id: " + id);
		Item item = itemRepository.findById(id).get();
		if (item == null) {
			throw new ResourseNotFoundException();
		}
		item = itemMapper.itemDTOtoItem(itemDTO);
		item.setId(id);
		return itemMapper.itemToItemDTO(itemRepository.save(item));
	}

	public ItemDTO patchItem(Long id, ItemDTO itemDTO) {
		log.debug("patchItem() called in ItemService with itemDTO: " + itemDTO + " and item id: " + id);
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
		log.debug("deleteById() called in ItemService with item id: " + id);
		Item item = itemRepository.getById(id);
		itemRepository.delete(item);
	}

}

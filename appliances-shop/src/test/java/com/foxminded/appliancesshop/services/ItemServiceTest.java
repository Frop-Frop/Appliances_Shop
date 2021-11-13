package com.foxminded.appliancesshop.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.foxminded.appliancesshop.domain.Cart;
import com.foxminded.appliancesshop.domain.Customer;
import com.foxminded.appliancesshop.domain.Item;
import com.foxminded.appliancesshop.domain.Product;
import com.foxminded.appliancesshop.mappers.ItemMapper;
import com.foxminded.appliancesshop.model.ItemDTO;
import com.foxminded.appliancesshop.model.ProductDTO;
import com.foxminded.appliancesshop.repositories.CartRepository;
import com.foxminded.appliancesshop.repositories.CustomerRepository;
import com.foxminded.appliancesshop.repositories.ItemRepository;
import com.foxminded.appliancesshop.repositories.ProductRepository;

@SpringBootTest(classes = { ItemService.class, ItemMapper.class })
class ItemServiceTest {

	@MockBean
	private ItemRepository itemRepository;
	@MockBean
	private ProductRepository productRepository;
	@MockBean
	private CartRepository cartRepository;
	@MockBean
	private CustomerRepository customerRepository;
	@MockBean
	private ItemMapper itemMapper;

	@Autowired
	private ItemService itemService;

	@Test
	void getAllItemsInCartTest() {
		Cart cart = new Cart();
		cart.setId(1L);
		Item item = new Item();
		item.setId(1L);
		item.setCart(cart);
		Item Item1 = new Item();
		Item1.setId(2L);
		item.setCart(cart);
		ItemDTO itemDTO = new ItemDTO();
		itemDTO.setId(1L);
		itemDTO.setCartId(1L);
		ItemDTO itemDTO1 = new ItemDTO();
		itemDTO1.setId(2L);
		itemDTO1.setCartId(1L);
		when(itemMapper.itemToItemDTO(item)).thenReturn(itemDTO);
		when(itemMapper.itemToItemDTO(Item1)).thenReturn(itemDTO1);
		when(itemRepository.findAllItemsInCart(1L)).thenReturn(Arrays.asList(item, Item1));
		itemService.getAllItemsInCart(1L);
		verify(itemRepository, times(1)).findAllItemsInCart(1L);
		verify(itemMapper, times(1)).itemToItemDTO(item);
		verify(itemMapper, times(1)).itemToItemDTO(Item1);

	}

	@Test
	void getCustomerDeferredsTest() {
		Customer customer = new Customer();
		customer.setId(1L);
		Item item = new Item();
		item.setId(1L);
		item.setCustomer(customer);
		Item Item1 = new Item();
		Item1.setId(2L);
		item.setCustomer(customer);
		ItemDTO itemDTO = new ItemDTO();
		itemDTO.setId(1L);
		itemDTO.setCartId(1L);
		ItemDTO itemDTO1 = new ItemDTO();
		itemDTO1.setId(2L);
		itemDTO1.setCartId(1L);
		when(itemMapper.itemToItemDTO(item)).thenReturn(itemDTO);
		when(itemMapper.itemToItemDTO(Item1)).thenReturn(itemDTO1);
		when(itemRepository.findCustomerDeferreds(1L)).thenReturn(Arrays.asList(item, Item1));
		itemService.getCustomerDeferreds(1L);
		verify(itemRepository, times(1)).findCustomerDeferreds(1L);
		verify(itemMapper, times(1)).itemToItemDTO(item);
		verify(itemMapper, times(1)).itemToItemDTO(Item1);

	}

	@Test
	void getItemByIdTest() {
		Item category = new Item();
		category.setId(1L);
		Optional<Item> optionalCategory = Optional.of(category);
		ItemDTO categoryDTO = new ItemDTO();
		categoryDTO.setId(1L);
		when(itemRepository.findById(1L)).thenReturn(optionalCategory);
		when(itemMapper.itemToItemDTO(category)).thenReturn(categoryDTO);
		itemService.getItemById(1L);
		verify(itemRepository, times(1)).findById(1L);
	}

	@Test
	void createNewItemTest() {
		Item item = new Item();
		item.setId(1L);
		Item itemWithoutId = new Item();
		ItemDTO itemDTO = new ItemDTO();
		when(itemMapper.itemDTOtoItem(itemDTO)).thenReturn(itemWithoutId);
		when(itemRepository.save(itemWithoutId)).thenReturn(item);
		ItemDTO itemDTO1 = new ItemDTO();
		itemDTO1.setId(1L);
		when(itemMapper.itemToItemDTO(item)).thenReturn(itemDTO1);
		itemService.createNewItem(itemDTO);
		verify(itemRepository, times(1)).save(itemWithoutId);
		verify(itemMapper, times(1)).itemDTOtoItem(itemDTO);
		verify(itemMapper, times(1)).itemToItemDTO(item);
	}

	@Test
	void saveItemByDTOTest() {
		Item item = new Item();
		item.setId(1L);
		Optional<Item> optionalItem = Optional.of(item);
		ItemDTO itemDTO = new ItemDTO();
		ItemDTO itemDTO1 = new ItemDTO();
		itemDTO1.setId(1l);
		Item itemWithoutId = new Item();
		when(itemMapper.itemDTOtoItem(itemDTO)).thenReturn(itemWithoutId);
		when(itemRepository.findById(1L)).thenReturn(optionalItem);
		when(itemRepository.save(itemWithoutId)).thenReturn(item);
		when(itemMapper.itemToItemDTO(item)).thenReturn(itemDTO1);
		itemService.saveItemByDTO(1L, itemDTO);
		verify(itemRepository, times(1)).findById(1L);
		verify(itemRepository, times(1)).save(itemWithoutId);
		verify(itemMapper, times(1)).itemDTOtoItem(itemDTO);
		verify(itemMapper, times(1)).itemToItemDTO(item);
	}

	@Test
	void patchItem() {
		Customer customer = new Customer();
		customer.setId(1L);
		Cart cart = new Cart();
		cart.setId(1L);
		Item item = new Item();
		item.setId(1L);
		when(itemRepository.getById(1L)).thenReturn(item);
		Item itemWithId = new Item();
		ProductDTO productDTO = new ProductDTO(1L, "Product", 1L, 123, "Brand", "Description");
		Product product = new Product(1L, "Product", null, 123, "Brand", "Description");
		ItemDTO itemDTO = new ItemDTO();
		itemDTO.setCartId(1L);
		itemDTO.setCost(246);
		itemDTO.setCustomerId(1L);
		itemDTO.setProduct(productDTO);
		itemDTO.setQuantity(2);
		when(productRepository.getById(1L)).thenReturn(product);
		when(cartRepository.getById(1L)).thenReturn(cart);
		when(customerRepository.getById(1L)).thenReturn(customer);
		when(itemMapper.itemToItemDTO(item)).thenReturn(itemDTO);
		when(itemRepository.save(item)).thenReturn(itemWithId);
		itemService.patchItem(1L, itemDTO);
		verify(itemRepository, times(1)).getById(1L);
		verify(itemRepository, times(1)).save(item);
	}

}

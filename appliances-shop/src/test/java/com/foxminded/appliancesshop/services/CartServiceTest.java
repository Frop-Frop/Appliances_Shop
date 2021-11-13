package com.foxminded.appliancesshop.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
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
import com.foxminded.appliancesshop.mappers.CartMapper;
import com.foxminded.appliancesshop.mappers.ItemMapper;
import com.foxminded.appliancesshop.mappers.ProductMapper;
import com.foxminded.appliancesshop.model.CartDTO;
import com.foxminded.appliancesshop.model.ItemDTO;
import com.foxminded.appliancesshop.model.ItemListDTO;
import com.foxminded.appliancesshop.repositories.CartRepository;
import com.foxminded.appliancesshop.repositories.CustomerRepository;

@SpringBootTest(classes = { CartService.class, CartMapper.class, ItemMapper.class })
class CartServiceTest {

	@MockBean
	private CartRepository cartRepository;
	@MockBean
	private CustomerRepository customerRepository;

	@MockBean
	private ProductMapper productMapper;
	@MockBean
	private CartMapper cartMapper;
	@Autowired
	private CartService cartService;

	@Test
	void getCartByIdTest() {
		Cart cart = new Cart();
		cart.setCustomer(customerRepository.getById(1L));
		cart.setId(10L);
		Optional<Cart> cartOptional = Optional.of(cart);
		CartDTO cartDTO = new CartDTO();
		cartDTO.setId(10L);
		cartDTO.setCustomerId(1L);

		when(cartRepository.findById(anyLong())).thenReturn(cartOptional);
		when(cartMapper.cartToCartDTO(cart)).thenReturn(cartDTO);

		CartDTO cartReturned = cartService.getCartById(50l);
		assertNotNull(cartReturned);
		verify(cartRepository, times(1)).findById(50L);
		verify(cartRepository, never()).findAll();
	}

	@Test
	void saveCartByDTOTest() {

		Cart cart = new Cart();
		cart.setCustomer(customerRepository.getById(1L));
		cart.setId(1L);
		Optional<Cart> optionalCart = Optional.of(cart);
		CartDTO cartDTO = new CartDTO();
		cartDTO.setCustomerId(1L);

		ItemDTO itemDTO = new ItemDTO();
		itemDTO.setId(1L);
		itemDTO.setQuantity(5);
		ItemDTO itemDTO1 = new ItemDTO();
		itemDTO1.setId(2L);
		itemDTO1.setQuantity(6);
		ItemListDTO itemListDTO = new ItemListDTO(Arrays.asList(itemDTO, itemDTO1));
		cartDTO.setItems(itemListDTO);

		Customer customer = new Customer();
		customer.setFirstName("first");
		customer.setLastName("last");
		customer.setId(1L);
		Optional<Customer> optionalCustomer = Optional.of(customer);
		when(cartRepository.findById(anyLong())).thenReturn(optionalCart);
		when(cartMapper.cartToCartDTO(cart)).thenReturn(cartDTO);
		when(customerRepository.findById(1L)).thenReturn(optionalCustomer);
		cartService.patchCart(cart.getId(), cartDTO);
		verify(cartRepository, times(1)).findById(cart.getId());
		verify(cartRepository, times(1)).save(cart);
	}

	@Test
	void saveCartByDTOtest() {

		Cart cart = new Cart();
		cart.setCustomer(customerRepository.getById(1L));
		cart.setId(1L);
		Optional<Cart> optionalCart = Optional.of(cart);
		when(cartRepository.findById(anyLong())).thenReturn(optionalCart);
		CartDTO cartDTO = new CartDTO();
		cartDTO.setCustomerId(1L);
		when(cartMapper.cartToCartDTO(cart)).thenReturn(cartDTO);
		when(cartMapper.cartDTOtoCart(cartDTO)).thenReturn(cart);
		cartService.saveCartByDTO(cart.getId(), cartDTO);
		verify(cartRepository, times(1)).findById(cart.getId());
		verify(cartRepository, times(1)).save(cart);
	}

}

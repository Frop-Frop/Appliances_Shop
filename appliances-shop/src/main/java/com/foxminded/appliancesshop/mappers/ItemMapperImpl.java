package com.foxminded.appliancesshop.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.foxminded.appliancesshop.domain.Cart;
import com.foxminded.appliancesshop.domain.Customer;
import com.foxminded.appliancesshop.domain.Item;
import com.foxminded.appliancesshop.domain.Product;
import com.foxminded.appliancesshop.model.CartDTO;
import com.foxminded.appliancesshop.model.CustomerDTO;
import com.foxminded.appliancesshop.model.ItemDTO;
import com.foxminded.appliancesshop.model.ProductDTO;

@Component
public class ItemMapperImpl implements ItemMapper {

	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private CartMapper cartMapper;
	@Autowired
	private CustomerMapperImpl customerMapper;

	@Override
	public ItemDTO itemToItemDTO(Item item) {
		if (item == null) {
			return null;
		}
		ProductDTO productDTO = productMapper.productToProductDTO(item.getProduct());
		CartDTO cartDTO = cartMapper.cartToCartDTO(item.getCart());
		CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(item.getCustomer());
		ItemDTO itemDTO = new ItemDTO(item.getId(), item.getQuantity(), productDTO, cartDTO, customerDTO,
				item.getCost());
		return itemDTO;
	}

	@Override
	public Item itemDTOtoItem(ItemDTO itemDTO) {
		if (itemDTO == null) {
			return null;
		}
		Product product = productMapper.productDTOtoProduct(itemDTO.getProduct());
		Cart cart = cartMapper.cartDTOtoCart(itemDTO.getCart());
		Customer customer = customerMapper.customerDTOtoCustomer(itemDTO.getCustomer());
		Item item = new Item(itemDTO.getId(), itemDTO.getQuantity(), product, cart, customer);
		return item;
	}
}

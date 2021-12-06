package com.foxminded.appliancesshop.domain.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.foxminded.appliancesshop.domain.Administrator;
import com.foxminded.appliancesshop.domain.Customer;

import lombok.Data;

@Data
public class SecurityUser implements UserDetails {
	private static final long serialVersionUID = -537670333331362983L;
	private final String email;
	private final String password;
	private final List<SimpleGrantedAuthority> authorities;
	private final boolean isActive;

	public SecurityUser(String email, String password, List<SimpleGrantedAuthority> authorities, boolean isActive) {
		this.email = email;
		this.password = password;
		this.authorities = authorities;
		this.isActive = isActive;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return isActive;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isActive;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return isActive;
	}

	@Override
	public boolean isEnabled() {
		return isActive;
	}

	@Override
	public String getUsername() {
		return email;
	}

	public static UserDetails fromCustomer(Customer customer) {
		return new org.springframework.security.core.userdetails.User(customer.getEmail(), customer.getPassword(),
				customer.getStatus().equals(Status.ACTIVE), customer.getStatus().equals(Status.ACTIVE),
				customer.getStatus().equals(Status.ACTIVE), customer.getStatus().equals(Status.ACTIVE),
				customer.getRole().getAuthorities());
	}

	public static UserDetails fromAdministrator(Administrator administrator) {
		return new org.springframework.security.core.userdetails.User(administrator.getEmail(),
				administrator.getPassword(), administrator.getStatus().equals(Status.ACTIVE),
				administrator.getStatus().equals(Status.ACTIVE), administrator.getStatus().equals(Status.ACTIVE),
				administrator.getStatus().equals(Status.ACTIVE), administrator.getRole().getAuthorities());
	}

}

package com.fpj.spring.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fpj.client.dtos.IUserDto;
import com.fpj.spring.dtos.UserDto;
import com.fpj.spring.dtos.UserPagingLoadResultBean;
import com.fpj.spring.entities.EntUser;
import com.fpj.spring.entities.EntUser.EntUserRole;
import com.fpj.spring.repository.UserRepository;

@Service
public class UserService extends GenericService<IUserDto, UserPagingLoadResultBean, EntUser>implements IUserService {
	@Autowired
	UserRepository repository;
	
	@SuppressWarnings("serial")
	public class GrantedAuthorityImpl implements GrantedAuthority{

		private String authority;

		@Override
		public String getAuthority() {
			return authority;
		}
		
		public void setAuthority(String authority){
			this.authority = authority;
		}
	}
	
	@SuppressWarnings("serial")
	public class UserDetailsImpl implements UserDetails{

		private String username;
		private String password;
		private Boolean isEnabled;
		private Collection<GrantedAuthorityImpl> authorities = new ArrayList<GrantedAuthorityImpl>();

		@Override
		public Collection<GrantedAuthorityImpl> getAuthorities() {
			return authorities ;
		}

		@Override
		public String getPassword() {
			return password;
		}

		@Override
		public String getUsername() {
			return username;
		}

		@Override
		public boolean isAccountNonExpired() {
			return true;
		}

		@Override
		public boolean isAccountNonLocked() {
			return true;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}

		@Override
		public boolean isEnabled() {
			return isEnabled!=null? isEnabled : false;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public void setEnabled(Boolean isEnabled) {
			this.isEnabled = isEnabled;
		}
		
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		EntUser user = getRepository().findByUsername(username);
		UserDetailsImpl userDetails = new UserDetailsImpl();
		userDetails.setEnabled(user.getEnabled());
		userDetails.setUsername(user.getUsername());
		userDetails.setPassword(user.getPassword());
		for (EntUserRole role  : user.getRoles()) {
			GrantedAuthorityImpl gaImpl= new GrantedAuthorityImpl();
			gaImpl.setAuthority(role.toString());
			userDetails.getAuthorities().add(gaImpl);
		}
		return userDetails;
		
	}

	@Override
	protected void fillEntity(EntUser entity, IUserDto dto) {
		entity.setEmail(dto.getEmail());
		if (entity.getPassword() != null && !entity.getPassword().isEmpty()){
			entity.setPassword(new ShaPasswordEncoder().encodePassword(dto.getPassword(), "QX"));
		}
		entity.setUsername(dto.getUsername());
		entity.setEnabled(dto.getEnabled());
		for (String role : dto.getRoles()) {
			entity.getRoles().add(EntUser.EntUserRole.valueOf(role.toUpperCase()));
		}
	}

	@Override
	protected EntUser createEntity() {
		return new EntUser();
	}

	@Override
	protected IUserDto getDto(EntUser entity) {
		IUserDto result = new UserDto();
		result.setEmail(entity.getEmail());
		result.setId(entity.getId());
		result.setUsername(entity.getUsername());
		for (EntUser.EntUserRole role : entity.getRoles()) {
			result.getRoles().add(role.toString());
		}
		return result ;
	}

	@Override
	protected UserPagingLoadResultBean createResult() {
		return new UserPagingLoadResultBean();
	}

	@Override
	protected UserRepository getRepository() {
		return repository;
	}

}

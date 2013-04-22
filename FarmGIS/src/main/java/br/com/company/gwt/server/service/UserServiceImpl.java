package br.com.company.gwt.server.service;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.company.gwt.client.UserService;
import br.com.company.gwt.server.InputServletImpl;
import br.com.company.gwt.server.dao.DaoUser;
import br.com.company.gwt.server.entities.User;
import br.com.company.gwt.shared.dto.DTOUser;

@Named("userService")
public class UserServiceImpl extends InputServletImpl implements UserService {

	@Inject DaoUser daoUser;
	
	@Override
	public Boolean login(DTOUser user) {
		User u = daoUser.login(user.getUserName(), user.getPassword());
		return (u != null);
	}
		
}
package br.com.company.gwt.server.dao;

import javax.inject.Named;

import org.hibernate.Query;

import br.com.company.gwt.server.entities.User;

@Named(value="daoUser")
public class DaoUser extends DaoAbstract<User, Integer> {
	
	@Override
	protected Integer getId(User user) {
		return user.getId();
	}
	
	public User login(String userName, String password) {
		User user = null;
		try {
			String hql = " from User u " +
					     " where u.userName = :userName " +
					     " and u.password = :password" +
					     " and u.enabled = :enabled";
			Query query = createQuery(hql);
			query.setParameter("userName", userName);
			query.setParameter("password", password);
			query.setParameter("enabled", Boolean.TRUE);
			query.setMaxResults(1);
			
			user = (User)query.uniqueResult();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return user;
		
	}

}
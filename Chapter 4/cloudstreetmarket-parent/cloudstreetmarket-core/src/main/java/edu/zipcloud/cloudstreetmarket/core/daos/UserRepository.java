package edu.zipcloud.cloudstreetmarket.core.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.zipcloud.cloudstreetmarket.core.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{

	User findByLoginName(String username);
	
	User findByLoginNameAndPassword(String username, String password);

}

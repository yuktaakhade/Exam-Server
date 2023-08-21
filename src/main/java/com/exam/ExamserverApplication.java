package com.exam;

import com.exam.exception.UserFoundExcpetion;
import com.exam.model.Role;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class ExamserverApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public static void main(String[] args) {

		SpringApplication.run(ExamserverApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Starting code!!!");

		try{
			User user = new User();
			user.setFirstName("ash");
			user.setLastName("say");
			user.setUsername("ash10");
			user.setPassword(bCryptPasswordEncoder.encode("123"));
			user.setEmail("abc@gmail.com");
			user.setProfile("deafult.png");

			Role role1 = new Role();
			role1.setRoleId(32L);
			role1.setRoleName("ADMIN");

			Set<UserRole> userRolesSet = new HashSet<>();
			UserRole userRole = new UserRole();
			userRole.setRole(role1);
			userRole.setUser(user);

			userRolesSet.add(userRole);

			User user1 = this.userService.createUser(user, userRolesSet);
			System.out.println(user1.getUsername());

		}catch (UserFoundExcpetion e){
			e.printStackTrace();
			System.out.println("User with this username already Present!!");
		}



	}
}

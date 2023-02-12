package com.hoa.shopbanhang;

import com.hoa.shopbanhang.application.constants.AuthenticationProvider;
import com.hoa.shopbanhang.application.constants.RoleConstant;
import com.hoa.shopbanhang.application.repositories.IRoleRepository;
import com.hoa.shopbanhang.application.repositories.IUserRepository;
import com.hoa.shopbanhang.domain.entities.Role;
import com.hoa.shopbanhang.domain.entities.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ShopBanHangApplication {

	private final IRoleRepository roleRepository;
	private final IUserRepository userRepository;

	public ShopBanHangApplication(IRoleRepository roleRepository, IUserRepository userRepository) {
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(ShopBanHangApplication.class, args);
	}

	@Bean
	CommandLineRunner init() {
		return args -> {
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

			if(roleRepository.count() == 0) {
				roleRepository.save(new Role(RoleConstant.ROLE_ADMIN, RoleConstant.DES_ROLE_ADMIN, null));
				roleRepository.save(new Role(RoleConstant.ROLE_USER, RoleConstant.DES_ROLE_USER, null));
			}

			if (userRepository.count() == 0) {
				User user = new User("admin", passwordEncoder.encode("admin"), "Admin", roleRepository.findByName(RoleConstant.ROLE_ADMIN), AuthenticationProvider.LOCAL, true);
				userRepository.save(user);
			}
		};
	}
}

package com.vitweb.vitwebapi;

import com.vitweb.vitwebapi.application.constants.AuthenticationProvider;
import com.vitweb.vitwebapi.application.constants.RoleConstant;
import com.vitweb.vitwebapi.application.repositories.IRoleRepository;
import com.vitweb.vitwebapi.application.repositories.IUserRepository;
import com.vitweb.vitwebapi.domain.entities.Role;
import com.vitweb.vitwebapi.domain.entities.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class VitwebapiApplication {

	private final IRoleRepository roleRepository;
	private final IUserRepository userRepository;

	public VitwebapiApplication(IRoleRepository roleRepository, IUserRepository userRepository) {
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(VitwebapiApplication.class, args);
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

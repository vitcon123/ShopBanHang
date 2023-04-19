package com.hoa.shopbanhang;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoa.shopbanhang.application.constants.AuthenticationProvider;
import com.hoa.shopbanhang.application.constants.RoleConstant;
import com.hoa.shopbanhang.application.repositories.ICategoryRepository;
import com.hoa.shopbanhang.application.repositories.IProductRepository;
import com.hoa.shopbanhang.application.repositories.IRoleRepository;
import com.hoa.shopbanhang.application.repositories.IUserRepository;
import com.hoa.shopbanhang.application.services.impl.CategoryServiceImpl;
import com.hoa.shopbanhang.configs.ProductInitJSON;
import com.hoa.shopbanhang.domain.entities.Category;
import com.hoa.shopbanhang.domain.entities.Product;
import com.hoa.shopbanhang.domain.entities.Role;
import com.hoa.shopbanhang.domain.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class ShopBanHangApplication {

	private final IRoleRepository roleRepository;
	private final IUserRepository userRepository;
	private final IProductRepository productRepository;
	private final ICategoryRepository categoryRepository;
	private final ModelMapper modelMapper;

	public ShopBanHangApplication(IRoleRepository roleRepository, IUserRepository userRepository,
																IProductRepository productRepository, ICategoryRepository categoryRepository, ModelMapper modelMapper) {
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
		this.modelMapper = modelMapper;
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

			if(productRepository.count() == 0) {
				ObjectMapper objectMapper = new ObjectMapper();
				File file = new File("products.json");
				try {
					List<ProductInitJSON> products = objectMapper.readValue(file, new TypeReference<List<ProductInitJSON>>() {});

					for (ProductInitJSON productInit : products) {
						Product product = modelMapper.map(productInit, Product.class);
						Optional<Category> category = categoryRepository.findById(productInit.getCategory());
						CategoryServiceImpl.checkCategoryExists(category);
						product.setCategory(category.get());
						productRepository.save(product);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
	}
}

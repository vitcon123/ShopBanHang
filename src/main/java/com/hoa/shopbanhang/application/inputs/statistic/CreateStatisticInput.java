package com.hoa.shopbanhang.application.inputs.statistic;

import com.hoa.shopbanhang.domain.entities.Product;
import com.hoa.shopbanhang.domain.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateStatisticInput {

  private Integer ageOfUser;

  private User user;

  private Product product;

}

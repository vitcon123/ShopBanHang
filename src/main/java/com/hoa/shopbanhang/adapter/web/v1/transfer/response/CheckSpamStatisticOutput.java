package com.hoa.shopbanhang.adapter.web.v1.transfer.response;

import com.hoa.shopbanhang.domain.entities.Product;
import com.hoa.shopbanhang.domain.entities.User;

public interface CheckSpamStatisticOutput {

  User getUser();

  Product getProduct();

  Long getTimes();

}

package com.hoa.shopbanhang.application.outputs.product;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.ProductOutput;
import com.hoa.shopbanhang.application.outputs.common.PagingMeta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetListProductOutput {

  private List<ProductOutput> productOutputs;

  private PagingMeta meta;

}

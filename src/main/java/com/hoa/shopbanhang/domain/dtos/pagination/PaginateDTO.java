package com.hoa.shopbanhang.domain.dtos.pagination;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaginateDTO<T> {
    private Page<T> pageData;
    private PaginationDTO.Pagination pagination;
}

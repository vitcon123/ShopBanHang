package com.hoa.shopbanhang.application.outputs.common;

    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagingMeta {

  private Integer total;

  private Integer page;

  private Integer size;

  private Integer totalPage;


  public PagingMeta(Integer total, Integer page, Integer size) {
    this.total = total;
    this.page = page;
    this.size = size;
    this.totalPage = getTotalPage();
  }

  public Integer getTotalPage() {
    if(size != null && size > 0) {
      return (int) Math.ceil(total * 1.0 / size);
    }
    return 1;
  }

}
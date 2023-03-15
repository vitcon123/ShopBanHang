package com.hoa.shopbanhang.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hoa.shopbanhang.application.constants.AuthenticationProvider;
import com.hoa.shopbanhang.application.constants.TableNameConstant;
import com.hoa.shopbanhang.domain.entities.base.AbstractAuditingEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = TableNameConstant.TBL_USER)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User extends AbstractAuditingEntity {

  private String fullName;

  private String email;

  private String password;

  private String birthday;

  private String phone;

  private String address;

  private String gender;

  private String avatar;

  private Double coin = 0.0;

  @Enumerated(EnumType.STRING)
  private AuthenticationProvider authProvider;

  private Boolean status = Boolean.FALSE;

  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  @JoinColumn(name = "role_id")
  private Role role;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
  @JsonIgnore
  private Cart cart;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
  @JsonIgnore
  private List<Order> orders;

  @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER, mappedBy = "users")
  @JsonIgnore
  private List<Notification> notifications;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
  @JsonIgnore
  private List<Rate> rates;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
  @JsonIgnore
  private List<Statistic> statistics;

  public User(String email, String password, String fullName,
              Role role, AuthenticationProvider authProvider, Boolean status) {
    this.email = email;
    this.password = password;
    this.fullName = fullName;
    this.role = role;
    this.authProvider = authProvider;
    this.status = status;
  }

}

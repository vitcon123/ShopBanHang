package com.vitweb.vitwebapi.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vitweb.vitwebapi.application.constants.AuthenticationProvider;
import com.vitweb.vitwebapi.application.constants.TableNameConstant;
import com.vitweb.vitwebapi.domain.entities.base.AbstractAuditingEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

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

  private Double money = 0.0;

  @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
  @JoinTable(name = "user_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  @JsonIgnore
  private List<Role> roles;

  // list courses
  @JoinTable(name = "user_notification",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "notification_id"),
      foreignKey = @ForeignKey(name = "FK_USER_NOTIFICATION"))
  @JsonIgnore
  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Notification> notifications;

  @Enumerated(EnumType.STRING)
  private AuthenticationProvider authProvider;

  private Boolean status = Boolean.FALSE;


  public User(String email, String password, String fullName,
              List<Role> roles, AuthenticationProvider authProvider, Boolean status) {
    this.email = email;
    this.password = password;
    this.fullName = fullName;
    this.roles = roles;
    this.authProvider = authProvider;
    this.status = status;
  }

}

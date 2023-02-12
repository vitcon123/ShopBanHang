package com.vitweb.vitwebapi.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vitweb.vitwebapi.application.constants.TableNameConstant;
import com.vitweb.vitwebapi.domain.entities.base.AbstractAuditingEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = TableNameConstant.TBL_NOTIFICATION)
public class Notification extends AbstractAuditingEntity {

  private String content;

  private String path;

  private Integer type;

  private Boolean isRead = Boolean.FALSE;

  @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  @JoinTable(name = "account_notification",
      joinColumns = @JoinColumn(name = "notification_id",
          referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "account_id",
          referencedColumnName = "id"))
  @JsonIgnore
  private Set<User> users = new HashSet<>();

}

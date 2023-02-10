package com.vitweb.vitwebapi.domain.entities;

import com.vitweb.vitwebapi.application.constants.TableNameConstant;
import com.vitweb.vitwebapi.domain.entities.base.AbstractAuditingEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

}

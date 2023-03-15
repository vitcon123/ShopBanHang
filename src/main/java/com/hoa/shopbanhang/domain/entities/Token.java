package com.hoa.shopbanhang.domain.entities;

import com.hoa.shopbanhang.application.constants.TableNameConstant;
import com.hoa.shopbanhang.domain.entities.base.AbstractAuditingEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = TableNameConstant.TBL_TOKEN)
public class Token extends AbstractAuditingEntity {

  // Expiration time 10 minutes
  private static final int EXPIRATION_TIME = 24 * 60;

  private String token;

  private Date expirationTime;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id",
      nullable = false,
      foreignKey = @ForeignKey(name = "FK_USER_TOKEN"))
  private User user;

  public Token(String token) {
    super();
    this.token = token;
    this.expirationTime = calculateExpirationDate(Token.EXPIRATION_TIME);
  }

  public Token(String token, User user) {
    super();
    this.token = token;
    this.user = user;
    this.expirationTime = calculateExpirationDate(Token.EXPIRATION_TIME);
  }

  public Token(String token, User user, int expirationTime) {
    super();
    this.token = token;
    this.user = user;
    this.expirationTime = calculateExpirationDate(expirationTime);
  }

  private Date calculateExpirationDate(Integer time) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(new Date().getTime());
    calendar.add(Calendar.MINUTE, time);
    return new Date(calendar.getTime().getTime());
  }
}

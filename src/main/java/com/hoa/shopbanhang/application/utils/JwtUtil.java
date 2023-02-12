package com.hoa.shopbanhang.application.utils;

import com.hoa.shopbanhang.application.constants.AuthorityConstant;
import com.hoa.shopbanhang.domain.entities.User;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtUtil {

  private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

  @Value("${jwt.secret_key}")
  private String SECRET_KEY;

  @Value("${jwt.time_token_expiration}")
  private Integer TIME_TOKEN_EXPIRATION;

  @Value("${jwt.time_refresh_token_expiration}")
  private Integer TIME_REFRESH_TOKEN_EXPIRATION;

  public String extractUUID(String token) {
    return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
  }

  public Date extractExpiration(String token) {
    return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getExpiration();
  }

  private boolean isTokenExpired(Claims claims) {
    return claims.getExpiration().after(new Date());
  }

  public Claims getClaimsFromJwtToken(String token) {
    return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
  }

  // Verifier and decode token
  public String getEmailFromJwtToken(String token) {
    Claims claims = getClaimsFromJwtToken(token);
    if (claims != null && isTokenExpired(claims)) {
      return claims.getSubject();
    }
    return null;
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
      logger.error("Invalid JWT signature: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}", e.getMessage());
    }
    return false;
  }

  public String generateToken(User user, boolean isRefreshToken) {
    Map<String, Object> claim = new HashMap<>();
    claim.put(AuthorityConstant.CLAIM_TYPE, isRefreshToken ? AuthorityConstant.REFRESH_TOKEN : AuthorityConstant.TOKEN);
    if (isRefreshToken) {
      return Jwts.builder().setClaims(claim).setSubject(user.getEmail())
          .setIssuedAt(new Date(System.currentTimeMillis()))
          .setExpiration(new Date(System.currentTimeMillis() + TIME_REFRESH_TOKEN_EXPIRATION))
          .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }
    return Jwts.builder().setClaims(claim).setSubject(user.getEmail())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + TIME_TOKEN_EXPIRATION))
        .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
  }

//  public String generateTokenByRefreshToken(String refreshToken, User user) {
//    if (isTokenExpired(refreshToken)) {
//      throw new VsException(UserMessageConstant.ERR_EXCEPTION_GENERAL, DevMessageConstant.Token.TOKEN_IS_EXPIRED);
//    }
//    try {
//      SignedJWT decodedJWT = SignedJWT.parse(refreshToken);
//      Map<String, Object> payload = decodedJWT.getPayload().toJSONObject();
//      if (!payload.get(AuthorityConstant.CLAIM_TYPE).equals(AuthorityConstant.REFRESH_TOKEN)) {
//        throw new VsException(UserMessageConstant.ERR_EXCEPTION_GENERAL,
//            DevMessageConstant.Token.CAN_NOT_GENERATE_TOKEN);
//      }
//      return generateToken(user, CommonConstant.FALSE);
//    } catch (ParseException e) {
//      throw new VsException(UserMessageConstant.ERR_EXCEPTION_GENERAL, DevMessageConstant.Token.TOKEN_IS_INVALID);
//    }
//  }

}

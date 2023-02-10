package com.vitweb.vitwebapi.application.repositories;

import com.vitweb.vitwebapi.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

  @Query("select u from User u where u.email = ?1")
  Optional<User> findByEmail(String email);

  @Query("select u from User u where u.idName = ?1")
  Optional<User> findByIdName(String idName);

  @Query("select u.followings from User u where u.id = ?1")
  List<User> getFollowingById(Long id);

  @Query(value = ("select u.* from users u " +
      "inner join followings f " +
      "on u.id = f.user_id " +
      "where f.follow_id = ?1"),
      nativeQuery = true)
  List<User> getFollowersById(Long id);


}

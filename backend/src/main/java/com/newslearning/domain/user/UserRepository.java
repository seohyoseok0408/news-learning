package com.newslearning.domain.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	@Query("SELECT u FROM User u WHERE u.provider = :provider AND u.provider_id = :provider_id")
	Optional<User> findByProviderAndProvider_id(@Param("provider") String provider, @Param("provider_id") String provider_id);

	@Query("SELECT u FROM User u WHERE u.provider_id = :providerId")
    Optional<User> findByProviderId(@Param("providerId") String providerId);



}

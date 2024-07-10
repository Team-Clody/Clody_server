package com.donkeys_today.server.domain.user;

public interface UserRepository {

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByPlatformAndPlatformID(Platform platform, String platformID);

    Optional<User> findByPlatformAndPlatformID(Platform platform, String platformID);
}

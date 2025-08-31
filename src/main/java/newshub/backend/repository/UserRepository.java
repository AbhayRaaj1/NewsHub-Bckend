package newshub.backend.repository;

import newshub.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // email ya username se user search karne ke liye
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
}



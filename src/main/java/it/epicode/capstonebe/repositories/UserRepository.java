package it.epicode.capstonebe.repositories;

import it.epicode.capstonebe.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>, PagingAndSortingRepository<User, UUID> {

    public Optional<User>   findByUsername(String username);
    @Query("SELECT u.email FROM User u")
    public List<String>     getAllEmails();
    @Query("SELECT u.username FROM User u")
    public List<String>     getAllUsernames();
    @Query("SELECT u.phoneNumber FROM User u")
    public List<String>     getAllPhoneNumbers();

}

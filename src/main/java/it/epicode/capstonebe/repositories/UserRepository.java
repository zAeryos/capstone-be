package it.epicode.capstonebe.repositories;

import it.epicode.capstonebe.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>, PagingAndSortingRepository<User, UUID> {

    @Query("SELECT u.email FROM Utente u")
    public List<String> getAllEmails();
    @Query("SELECT u.username FROM Utente u")
    public List<String> getAllUsernames();
    @Query("SELECT u.phone_number FROM Utente u")
    public List<String> getAllPhoneNumbers();

}

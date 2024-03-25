package it.epicode.capstonebe.repositories;

import it.epicode.capstonebe.models.entities.NewsletterSubscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NewsletterSubscriberRepository extends JpaRepository<NewsletterSubscriber, Long>, PagingAndSortingRepository<NewsletterSubscriber, Long> {
}

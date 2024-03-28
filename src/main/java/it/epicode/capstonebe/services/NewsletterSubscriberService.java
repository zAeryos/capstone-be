package it.epicode.capstonebe.services;

import it.epicode.capstonebe.exceptions.BadRequestException;
import it.epicode.capstonebe.models.entities.NewsletterSubscriber;
import it.epicode.capstonebe.models.requestDTO.NewsletterSubscriberDTO;
import it.epicode.capstonebe.repositories.NewsletterSubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class NewsletterSubscriberService {

    @Autowired
    NewsletterSubscriberRepository newsletterRepository;

    public Page<NewsletterSubscriber> getAllEmails (Pageable pageable) {

        return newsletterRepository.findAll(pageable);

    }

    public NewsletterSubscriber saveEmail(NewsletterSubscriberDTO newsletterDTO) throws BadRequestException {

        NewsletterSubscriber email = new NewsletterSubscriber();

        email.setEmail(newsletterDTO.email());

        return newsletterRepository.save(email);

    }

    public void removeEmail(Long id) {

        newsletterRepository.deleteById(id);

    }

}

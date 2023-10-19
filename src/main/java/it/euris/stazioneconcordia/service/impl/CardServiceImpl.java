package it.euris.stazioneconcordia.service.impl;

import it.euris.stazioneconcordia.data.enums.Priority;
import it.euris.stazioneconcordia.data.model.Card;
import it.euris.stazioneconcordia.exception.IdMustBeNullException;
import it.euris.stazioneconcordia.exception.IdMustNotBeNullException;
import it.euris.stazioneconcordia.repository.CardRepository;
import it.euris.stazioneconcordia.service.CardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
@Service
public class CardServiceImpl implements CardService {

    CardRepository cardRepository;

    @Override
    public List<Card> findAll() {
        return cardRepository.findAll();
    }

    @Override
    public List<Card> findByPriority(Priority priority) {
        List<Card> cards = cardRepository.findAll();
        List<Card> cardsWithPriority = new ArrayList<>();
        for (Card card : cards) {
            if (card.getPriority() == priority) {
                cardsWithPriority.add(card);
            }
        }
        return cardsWithPriority;
    }

    @Override
    public List<Card> findAllCardsWithExpirationDateInLast5Days() {
        List<Card> cards = cardRepository.findAll();
        List<Card> cardsNearExpiration = new ArrayList<>();
        for (Card card : cards) {
            if (card.getExpirationDate().isAfter(LocalDateTime.now().minusDays(5L))) {
                cardsNearExpiration.add(card);
            }
        }
        if (cardsNearExpiration.size()>1) {
            for (int j = 0; j < cardsNearExpiration.size(); j++) {

                for (int i = 0; i < cardsNearExpiration.size()-1; i++) {
                    Card change;
                    if ((cardsNearExpiration.get(i).getExpirationDate()).isBefore(cardsNearExpiration.get(i + 1).getExpirationDate())) {
                        change = cardsNearExpiration.get(i + 1);
                        cardsNearExpiration.set(i + 1, cardsNearExpiration.get(i));
                        cardsNearExpiration.set(i, change);
                    }
                }
            }
        }
        return cardsNearExpiration;
    }

    @Override
    public Card insert(Card card) {
        if (card.getId() != null) {
            throw new IdMustBeNullException();
        }
        return cardRepository.save(card);
    }

    @Override
    public Card update(Card card) {
        if (card.getId() == null) {
            throw new IdMustNotBeNullException();
        }
        return cardRepository.save(card);
    }

    @Override
    public Boolean deleteById(Long idCard) {
        cardRepository.deleteById(idCard);
        return cardRepository.findById(idCard).isEmpty();
    }

    @Override
    public Card findById(Long idCard) {
        return cardRepository.findById(idCard).orElse(Card.builder().build());
    }
}

package com.dharaneesh.cards.service.impl;

import com.dharaneesh.cards.constants.CardsConstants;
import com.dharaneesh.cards.dto.CardsDto;
import com.dharaneesh.cards.entity.Cards;
import com.dharaneesh.cards.exception.CardAlreadyExistException;
import com.dharaneesh.cards.exception.ResourceNotFoundException;
import com.dharaneesh.cards.mapper.CardsMapper;
import com.dharaneesh.cards.repository.CardsRepository;
import com.dharaneesh.cards.service.ICardsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CardsServiceImpl implements ICardsService {

    private final CardsRepository cardsRepository;

    /**
     *
     * @param mobileNumber
     */
    @Override
    public void createCard(String mobileNumber) {

        Optional<Cards> optionalCards =cardsRepository.findByMobileNumber(mobileNumber);

        if (optionalCards.isPresent()) {

            throw new CardAlreadyExistException("Card already registered with given mobileNumber "+mobileNumber);
        }

        cardsRepository.save(createNewCard(mobileNumber));


    }

    /**
     *
     * @param mobileNumber
     * @return
     */
    @Override
    public CardsDto fetchCards(String mobileNumber) {

        Cards cards=cardsRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(()->new ResourceNotFoundException("Cards","MobileNumber",mobileNumber));

        return CardsMapper.mapToCardsDto(cards,new CardsDto());
    }

    /**
     *
     * @param cardsDto
     * @return
     */
    @Override
    public boolean updateCard(CardsDto cardsDto) {

        Cards cards = cardsRepository.findByCardNumber(cardsDto.getCardNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Card", "CardNumber", cardsDto.getCardNumber()));
        CardsMapper.mapToCards(cardsDto, cards);
        cardsRepository.save(cards);
        return  true;
    }

    /**
     *
     * @param mobileNumber
     * @return
     */
    @Override
    public boolean deleteCard(String mobileNumber) {

        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
        cardsRepository.deleteById(cards.getCardId());
        return true;
    }

    private Cards createNewCard(String mobileNumber) {

        Cards newCard = new Cards();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        return newCard;

    }
}

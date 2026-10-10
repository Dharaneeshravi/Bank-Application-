package com.dharaneesh.cards.service;

import com.dharaneesh.cards.dto.CardsDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

public interface ICardsService {

    /**
     *
     * @param mobileNumber
     */
    void createCard( String mobileNumber);

    /**
     *
     * @param mobileNumber
     * @return
     */
    CardsDto fetchCards(String mobileNumber);

    /**
     *
     * @param cardsDto
     * @return
     */
    boolean updateCard( CardsDto cardsDto);

    /**
     *
     * @param mobileNumber
     * @return
     */
    boolean deleteCard(String mobileNumber);
}

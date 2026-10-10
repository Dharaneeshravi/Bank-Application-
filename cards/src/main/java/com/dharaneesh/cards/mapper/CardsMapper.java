package com.dharaneesh.cards.mapper;

import com.dharaneesh.cards.dto.CardsDto;
import com.dharaneesh.cards.entity.Cards;

public class CardsMapper {


    public static Cards mapToCards(CardsDto cardsDto,Cards cards) {


        cards.setMobileNumber(cardsDto.getMobileNumber());
        cards.setCardNumber(cardsDto.getCardNumber());
        cards.setCardType(cardsDto.getCardType());
        cards.setAmountUsed(cardsDto.getAmountUsed());
        cards.setAvailableAmount(cardsDto.getAvailableAmount());
        cards.setTotalLimit(cardsDto.getTotalLimit());
        return cards;
    }


    public static CardsDto mapToCardsDto(Cards cards,CardsDto cardsDto) {

        cardsDto.setMobileNumber(cards.getMobileNumber());
        cardsDto.setCardNumber(cards.getCardNumber());
        cardsDto.setCardType(cards.getCardType());
        cardsDto.setAmountUsed(cards.getAmountUsed());
        cardsDto.setAvailableAmount(cards.getAvailableAmount());
        cardsDto.setTotalLimit(cards.getTotalLimit());
        return cardsDto;
    }
}

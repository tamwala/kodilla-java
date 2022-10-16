package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrelloMapperTest {

    TrelloMapper mapper;

    @BeforeEach
    void beforeEach() {
        mapper = new TrelloMapper();
    }

    @Test
    public void mapToBoardsTest() {
        //Given
        List<TrelloListDto> emptyList = new ArrayList<>();
        List<TrelloListDto> listDtos = new ArrayList<>();
        TrelloListDto listDto1 = new TrelloListDto("10", "Test list 1", false);
        TrelloListDto listDto2 = new TrelloListDto("20", "Test list 2", false);
        listDtos.add(listDto1);
        listDtos.add(listDto2);

        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        TrelloBoardDto boardDto1 = new TrelloBoardDto("Test Board 1", "1", emptyList);
        TrelloBoardDto boardDto2 = new TrelloBoardDto("Test Board 2", "2", listDtos);
        trelloBoardDtos.add(boardDto1);
        trelloBoardDtos.add(boardDto2);

        //When
        List<TrelloBoard> mappedToBoards = mapper.mapToBoards(trelloBoardDtos);

        //Then
        assertEquals(2, mappedToBoards.size());
        assertEquals("Test Board 1", mappedToBoards.get(0).getName());
        assertEquals("Test Board 2", mappedToBoards.get(1).getName());
        assertEquals(0, mappedToBoards.get(0).getLists().size());
        assertEquals(2, mappedToBoards.get(1).getLists().size());
        assertEquals("Test list 2", mappedToBoards.get(1).getLists().get(1).getName());
    }

    @Test
    public void mapToBoardsDtoTest() {
        //Given
        List<TrelloList> emptyList = new ArrayList<>();
        List<TrelloList> listDtos = new ArrayList<>();
        TrelloList list1 = new TrelloList("10", "Test list 1", false);
        TrelloList list2 = new TrelloList("20", "Test list 2", false);
        listDtos.add(list1);
        listDtos.add(list2);

        List<TrelloBoard> trelloBoard = new ArrayList<>();
        TrelloBoard boardDto1 = new TrelloBoard("Test Board 1", "1", emptyList);
        TrelloBoard boardDto2 = new TrelloBoard("Test Board 2", "2", listDtos);
        trelloBoard.add(boardDto1);
        trelloBoard.add(boardDto2);

        //When
        List<TrelloBoardDto> mappedToBoardsDto = mapper.mapToBoardsDto(trelloBoard);

        //Then
        assertEquals(2, mappedToBoardsDto.size());
        assertEquals("Test Board 1", mappedToBoardsDto.get(0).getName());
        assertEquals("Test Board 2", mappedToBoardsDto.get(1).getName());
        assertEquals(0, mappedToBoardsDto.get(0).getLists().size());
        assertEquals(2, mappedToBoardsDto.get(1).getLists().size());
        assertEquals("Test list 2", mappedToBoardsDto.get(1).getLists().get(1).getName());
    }

    @Test
    public void mapToCardDto() {
        //Given
        TrelloCard card1 = new TrelloCard("Test card", "Test description", "Test pos", "10");

        //When
        TrelloCardDto mappedToCardDto = mapper.mapToCardDto(card1);

        //Then
        assertEquals("Test card", mappedToCardDto.getName());
        assertEquals("Test description", mappedToCardDto.getDescription());
        assertEquals("Test pos", mappedToCardDto.getPos());
        assertEquals("10", mappedToCardDto.getListId());
    }

    @Test
    public void mapToCard() {
        //Given
        TrelloCardDto card1 = new TrelloCardDto("Test card", "Test description", "Test pos", "10");

        //When
        TrelloCard mappedToCard = mapper.mapToCard(card1);

        //Then
        assertEquals("Test card", mappedToCard.getName());
        assertEquals("Test description", mappedToCard.getDescription());
        assertEquals("Test pos", mappedToCard.getPos());
        assertEquals("10", mappedToCard.getListId());
    }
}

package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.domain.TrelloListDto;
import com.crud.tasks.trello.facade.TrelloFacade;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(TrelloController.class)
public class TrelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrelloFacade trelloFacade;

    @Test
    public void shouldFetchEmptyTrelloBoards() throws Exception {
        //Given
        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        when(trelloFacade.fetchTrelloBoards()).thenReturn(trelloBoards);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/trello/boards").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200)) // or isOk()
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldFetchTrelloBoards() throws Exception {
        //Given
        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloListDto("1", "Test List", false));

        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("1", "Test Task", trelloLists));

        when(trelloFacade.fetchTrelloBoards()).thenReturn(trelloBoards);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/trello/boards").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", is("1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", is("Test Task")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lists", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lists[0].id", is("1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lists[0].name", is("Test List")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lists[0].closed", is(false)));
    }

    @Test
    public void shouldCreateTrelloCard() throws Exception {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "Test",
                "Test description",
                "top",
                "1");

        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto(
                "323",
                "Test",
                "http://test.com");

        when(trelloFacade.createCard(ArgumentMatchers.any(TrelloCardDto.class))).thenReturn(createdTrelloCardDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(trelloCardDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/trello/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is("323")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("Test")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.shortUrl", is("http://test.com")));
    }
}
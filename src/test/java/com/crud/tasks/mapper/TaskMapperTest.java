package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskMapperTest {

    TaskMapper mapper;
    Task task;

    @BeforeEach
    void beforeEach() {
        mapper = new TaskMapper();
        task = new Task(1L, "Test", "Content");
    }

    @Test
    public void mapToTaskTest() {
        //Given
        TaskDto taskDto = new TaskDto(1L, "Test", "Content");

        //When
        Task mappedTask = mapper.mapToTask(taskDto);

        //Then
        assertTrue(mappedTask.getId().equals(1L));
        assertEquals("Test", mappedTask.getTitle());
        assertEquals("Content", mappedTask.getContent());
    }

    @Test
    public void mapToTaskDtoTest() {
        //Given

        //When
        TaskDto mappedToTaskDto = mapper.mapToTaskDto(task);

        //Then
        assertTrue(mappedToTaskDto.getId().equals(1L));
        assertEquals("Test", mappedToTaskDto.getTitle());
        assertEquals("Content", mappedToTaskDto.getContent());
    }

    @Test
    public void mapToTaskDtoListTest() {
        //Given
        List<Task> taskList = new ArrayList<>();
        taskList.add(task);

        //When
        List<TaskDto> mappedToTaskDtoList = mapper.mapToTaskDtoList(taskList);

        //Then
        assertNotNull(mappedToTaskDtoList);
        assertEquals(1, mappedToTaskDtoList.size());
        assertEquals("Test", mappedToTaskDtoList.get(0).getTitle());
    }
}
package com.cs544.project.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class FacultyDto extends PersonDto {
    List<String> hobbies;
    private String salutation;
}

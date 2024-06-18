package com.cs544.project.adapter;

import com.cs544.project.domain.Course;
import com.cs544.project.domain.LocationType;
import com.cs544.project.domain.Student;
import com.cs544.project.dto.request.LocationTypeCreateRequest;
import com.cs544.project.dto.response.StudentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.Collection;

@Mapper
public interface StudentAdapter {
    StudentAdapter INSTANCE = Mappers.getMapper(StudentAdapter.class);

    StudentDto toDto(Student location);

    Student toEntity(StudentDto locationDto);

    @Mapping(source = "courses", target = "registeredCourses")
    StudentDto toDto(Student student, Collection<Course> courses);
}


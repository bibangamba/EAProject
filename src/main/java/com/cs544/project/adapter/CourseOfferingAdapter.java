package com.cs544.project.adapter;

import com.cs544.project.domain.CourseOffering;
import com.cs544.project.dto.response.CourseOfferingDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CourseOfferingAdapter {
    CourseOfferingAdapter INSTANCE = Mappers.getMapper(CourseOfferingAdapter.class);

    CourseOfferingDto toDto(CourseOffering courseOffering);

    CourseOffering toEntity(CourseOfferingDto courseOfferingDto);
}


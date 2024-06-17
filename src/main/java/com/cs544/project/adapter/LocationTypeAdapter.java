package com.cs544.project.adapter;

import com.cs544.project.domain.LocationType;
import com.cs544.project.dto.response.LocationTypeDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LocationTypeAdapter {
    LocationTypeAdapter INSTANCE = Mappers.getMapper(LocationTypeAdapter.class);

    LocationTypeDto toDto(LocationType location);

    LocationType toEntity(LocationTypeDto locationDto);
}

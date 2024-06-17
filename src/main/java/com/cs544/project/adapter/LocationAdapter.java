package com.cs544.project.adapter;

import com.cs544.project.domain.Location;
import com.cs544.project.dto.response.LocationDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LocationAdapter {
    LocationAdapter INSTANCE = Mappers.getMapper(LocationAdapter.class);

    LocationDto toDto(Location location);

    Location toEntity(LocationDto locationDto);
}

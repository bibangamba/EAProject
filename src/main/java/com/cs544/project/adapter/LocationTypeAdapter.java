package com.cs544.project.adapter;

import com.cs544.project.domain.Location;
import com.cs544.project.domain.LocationType;
import com.cs544.project.dto.request.LocationPatchRequest;
import com.cs544.project.dto.request.LocationTypeCreateRequest;
import com.cs544.project.dto.response.LocationTypeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LocationTypeAdapter {
    LocationTypeAdapter INSTANCE = Mappers.getMapper(LocationTypeAdapter.class);

    LocationTypeDto toDto(LocationType location);

    LocationType toEntity(LocationTypeDto locationDto);

    LocationType toEntity(LocationTypeCreateRequest locationTypeRequest);

    void updateEntityWithRequest(LocationTypeCreateRequest locationTypeRequest, @MappingTarget LocationType entity);
}

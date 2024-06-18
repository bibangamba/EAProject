package com.cs544.project.adapter;

import com.cs544.project.domain.Location;
import com.cs544.project.domain.LocationType;
import com.cs544.project.dto.request.LocationCreateRequest;
import com.cs544.project.dto.request.LocationPatchRequest;
import com.cs544.project.dto.request.LocationTypeCreateRequest;
import com.cs544.project.dto.response.LocationDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LocationAdapter {
    LocationAdapter INSTANCE = Mappers.getMapper(LocationAdapter.class);

    LocationDto toDto(Location location);

    Location toEntity(LocationDto locationDto);


    @Mapping(target = "auditData", ignore = true)
    Location toEntity(LocationCreateRequest locationCreateRequest);

    void updateEntityWithPatchRequest(LocationPatchRequest locationPatchRequest, @MappingTarget Location entity);

    void updateEntityWithPatchRequest(LocationCreateRequest locationCreateRequest, @MappingTarget Location entity);
}

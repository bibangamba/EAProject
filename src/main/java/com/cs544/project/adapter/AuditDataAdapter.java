package com.cs544.project.adapter;

import com.cs544.project.domain.AuditData;
import com.cs544.project.dto.response.AuditDataDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuditDataAdapter {
    AuditDataAdapter INSTANCE = Mappers.getMapper(AuditDataAdapter.class);

    AuditDataDto toDto(AuditData auditData);

    AuditData toEntity(AuditDataDto auditDataDto);

}

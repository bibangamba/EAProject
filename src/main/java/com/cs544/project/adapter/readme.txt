This package is responsible for conversion of Entity into Dto and vice-versa.
This reduces the boiler code for the conversion.
It uses MapStruct Dependency from pom.xml which looks like the following:
    <dependency>
        <groupId>org.mapstruct</groupId>
        <artifactId>mapstruct</artifactId>
        <version>1.5.5.Final</version>
    </dependency>

@Mapper -> The annotation to denote this is a mapper class
public interface AuditDataAdapter {
    AuditDataAdapter INSTANCE = Mappers.getMapper(AuditDataAdapter.class);

    // Convert AuditData Entity into AuditDataDto
    AuditDataDto toDto(AuditData auditData);

    // Convert AuditDataDto into AuditData Entity
    AuditData toEntity(AuditDataDto auditDataDto);

}
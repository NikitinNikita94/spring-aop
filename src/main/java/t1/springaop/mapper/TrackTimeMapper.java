package t1.springaop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import t1.springaop.model.dto.TrackTimeDto;
import t1.springaop.model.entity.TrackTimeEntity;

@Mapper(componentModel = "spring")
public interface TrackTimeMapper {

    TrackTimeMapper INSTANCE = Mappers.getMapper(TrackTimeMapper.class);

    TrackTimeDto toDto(TrackTimeEntity trackTime);

    TrackTimeEntity toEntity(TrackTimeDto trackTimeDto);
}

package ps.demo.commupload.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import ps.demo.commupload.dto.UploadMetaDto;
import ps.demo.commupload.entity.UploadMeta;

@Mapper(uses = MappingUtil.class)
public interface UploadMetaMapper {

    UploadMetaMapper INSTANCE = Mappers.getMapper(UploadMetaMapper.class);

    @Mappings({
            @Mapping(source = "extraParams", target = "extraParams", qualifiedBy = MappingUtil.ExtraParamsMap.class)
    })
    UploadMetaDto toDto(UploadMeta uploadMeta);

    @Mappings({
            @Mapping(source = "extraParams", target = "extraParams", qualifiedBy = MappingUtil.ExtraParamsString.class)
    })
    UploadMeta toEntity(UploadMetaDto uploadMetaDto);


}

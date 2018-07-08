package media.mediastreamer.mapper;

import io.minio.messages.Item;
import media.mediastreamer.dto.FileResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * It maps {@link Item} to {@link FileResult}
 *
 * @author Mateusz Kozłowski <matikz1110@gmail.com>
 */
@Mapper
public interface FileResultMapper {
    FileResultMapper INSTANCE = Mappers.getMapper(FileResultMapper.class);

    @Mappings({
            @Mapping(target = "etag", expression = "java(item.etag())"),
            @Mapping(target = "objectName", expression = "java(item.objectName())"),
            @Mapping(target = "lastModified", expression = "java(item.lastModified())"),
            @Mapping(target = "size", expression = "java(item.objectSize())"),
            @Mapping(target = "dir", expression = "java(item.isDir())"),
            @Mapping(target = "storageClass", expression = "java(item.storageClass())"),
            @Mapping(target = "owner", expression = "java(new FileResult.Owner(item.owner().id(), item.owner().displayName()))")
    })
    FileResult itemToFileResult(Item item);
}

package ps.demo.gateway.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ps.demo.gateway.dto.BookDto;
import ps.demo.gateway.entity.Book;

@Mapper
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper( BookMapper.class );

    BookDto toDto(Book book);

    Book toEntity(BookDto bookDto);


}

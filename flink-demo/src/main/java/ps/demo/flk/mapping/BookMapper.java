package ps.demo.flk.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ps.demo.flk.dto.BookDto;
import ps.demo.flk.entity.Book;

@Mapper
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper( BookMapper.class );

    BookDto toDto(Book book);

    Book toEntity(BookDto bookDto);


}

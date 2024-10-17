package ps.demo.user.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ps.demo.user.dto.BookDto;
import ps.demo.user.entity.Book;

@Mapper
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper( BookMapper.class );

    BookDto toDto(Book book);

    Book toEntity(BookDto bookDto);


}

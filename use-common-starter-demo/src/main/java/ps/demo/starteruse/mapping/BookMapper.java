package ps.demo.starteruse.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ps.demo.starteruse.dto.BookDto;
import ps.demo.starteruse.entity.Book;

@Mapper
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper( BookMapper.class );

    BookDto toDto(Book book);

    Book toEntity(BookDto bookDto);


}
package ps.demo.jpademo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import ps.demo.jpademo.entity.Book;
import ps.demo.jpademo.entity.Role;
import ps.demo.jpademo.repository.BookRepository;
import ps.demo.jpademo.repository.RoleRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@SpringBootApplication
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    // Spring runs CommandLineRunner bean when Spring Boot App starts
    @Profile("dev")
    @Bean
    public CommandLineRunner demo(BookRepository bookRepository, RoleRepository roleRepository) {
        return (args) -> {

            bookRepository.save(new Book("A Guide to the Bodhisattva Way of Life", "Santideva", new BigDecimal("15.41"), LocalDate.of(2023, 8, 31)));
            bookRepository.save(new Book("The Life-Changing Magic of Tidying Up", "Marie Kondo", new BigDecimal("9.69"), LocalDate.of(2023, 7, 31)));
            bookRepository.save(new Book("Refactoring: Improving the Design of Existing Code", "Martin Fowler", new BigDecimal("47.99"), LocalDate.of(2023, 6, 10)));


            // find book by ID
            Optional<Book> optionalBook = bookRepository.findById(1L);
            optionalBook.ifPresent(obj -> {
                log.info("Book found with findById(1L):");
                log.info("--------------------------------");
                log.info(obj.toString());
                log.info("\n");
            });

            // find book by published date after
            log.info("Book found with findByPublishedDateAfter(), after 2023/7/1");
            log.info("--------------------------------------------");
            bookRepository.findByPublishedDateAfter(LocalDate.of(2023, 7, 1)).forEach(b -> {
                log.info(b.toString());
                log.info("\n");
            });

            // initiate role data

            roleRepository.save(Role.builder().roleName("root").description("root").createdTime(LocalDateTime.now()).createdBy("sys").build());
            roleRepository.save(Role.builder().roleName("a").parentId(1L).description("a").createdTime(LocalDateTime.now()).createdBy("sys").build());
            roleRepository.save(Role.builder().roleName("b").parentId(1L).description("b").createdTime(LocalDateTime.now()).createdBy("sys").build());
            roleRepository.save(Role.builder().roleName("a1").parentId(2L).description("a1").createdTime(LocalDateTime.now()).createdBy("sys").build());
            roleRepository.save(Role.builder().roleName("a2").parentId(2L).description("a2").createdTime(LocalDateTime.now()).createdBy("sys").build());
            roleRepository.save(Role.builder().roleName("b1").parentId(3L).description("b1").createdTime(LocalDateTime.now()).createdBy("sys").build());


            log.info("Print out role list1: ");
            List<Role> list1 = roleRepository.findAllChildRoles(1L);
            list1.forEach((e) -> {
                log.info("role: {}", e);
            });

            log.info("Print out role list2: ");
            List<Role> list2 = roleRepository.findAllChildRoles(2L);
            list2.forEach((e) -> {
                log.info("role: {}", e);
            });

        };
    }

}

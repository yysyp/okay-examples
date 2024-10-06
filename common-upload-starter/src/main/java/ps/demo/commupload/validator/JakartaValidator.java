package ps.demo.commupload.validator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class JakartaValidator<T> {


    public List<String> validate(T dto) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> violationSet = validator.validate(dto);
        List<String> result = new ArrayList<>();
        violationSet.forEach( v -> {
            result.add(v.getMessage());
        });
        return result;

    }

}

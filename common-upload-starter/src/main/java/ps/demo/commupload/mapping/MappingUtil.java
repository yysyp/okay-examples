package ps.demo.commupload.mapping;


import com.google.gson.Gson;
import org.mapstruct.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import java.util.HashMap;

public class MappingUtil {

    private static final Gson gson = new Gson();


    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface ExtraParamsString {
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface ExtraParamsMap {
    }

    @ExtraParamsString
    public String extraParamsString(Map<String, Object> in) {
        return gson.toJson(in);

    }

    @ExtraParamsMap
    public Map<String, Object> extraParamsMap(String in) {
        return gson.fromJson(in, Map.class);
    }

}

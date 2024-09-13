package ps.demo.mybatchupload.common;

import java.util.function.Predicate;

public class RecordFailurePredicate implements Predicate<Throwable> {
    @Override
    public boolean test(Throwable throwable) {
        return !(throwable instanceof ServerErrorException);
    }
}
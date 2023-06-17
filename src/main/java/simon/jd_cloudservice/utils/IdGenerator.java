package simon.jd_cloudservice.utils;

import java.util.UUID;

public class IdGenerator {
    public static Integer generateId() {
        return Math.abs(
                UUID.randomUUID()
                        .toString()
                        .hashCode()
        );
    }
}
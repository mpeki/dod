package dk.dodgame.util;

import io.hypersistence.tsid.TSID;

public class DoDTsidGenerator {

    private DoDTsidGenerator() {
        throw new IllegalStateException("Utility class");
    }

    public static String createTsid() {
        return TSID.Factory.getTsid().toString();
    }

    public static String createReferenceNo(String prefix) {
        return "%s_%s".formatted(prefix, TSID.fast().toLowerCase());
    }

}

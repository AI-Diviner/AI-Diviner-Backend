package com.letfate.aidiviner.util;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class Auxiliary {
    public static long localDateTimeToTimestamp(LocalDateTime time) {
        ZoneId zone = ZoneId.systemDefault();
        return time.atZone(zone).toInstant().getEpochSecond();
    }
}
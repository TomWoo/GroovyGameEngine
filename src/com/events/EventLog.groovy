package com.events

/**
 * Created by Tom on 6/15/2017.
 */
class EventLog {
    private static List<String> log = new ArrayList<>(); // TODO: make non-static?

    public static void append(String entry) {
        log.add(entry)
    }

    public static List<String> getEntries() {
        return new ArrayList<>(log)
    }

    public static void clear() {
        log.clear()
    }
}

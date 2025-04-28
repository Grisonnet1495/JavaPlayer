package com.javaPlayer.project.utils;

import java.time.Duration;

public class DurationFormatter {
    public static String formatDuration(Duration duration) {
        if (duration == null) {
            return "00:00";
        }

        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();

        if (hours <= 0) {
            // Without hours
            return String.format("%02d:%02d", minutes, seconds);
        } else {
            // With hours
            return String.format("%d:%02d:%02d", hours, minutes, seconds);
        }
    }
}

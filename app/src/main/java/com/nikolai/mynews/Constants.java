package com.nikolai.mynews;

/**
 * some constants for the articleworker class and notifications
 */
    public final class Constants {

        // Notification Channel constants

        // Name of Notification Channel for verbose notifications of background work
        public static final CharSequence VERBOSE_NOTIFICATION_CHANNEL_NAME =
                "Verbose WorkManager Notifications";
        public static String VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION =
                "Shows notifications whenever work starts";
        public static final CharSequence NOTIFICATION_TITLE = "WorkRequest Starting";
        public static final String CHANNEL_ID = "VERBOSE_NOTIFICATION" ;
        public static final int NOTIFICATION_ID = 1;

        public static final long DELAY_TIME_MILLIS = 3000;

        // Ensures this class is never instantiated
        private Constants() {}

    }

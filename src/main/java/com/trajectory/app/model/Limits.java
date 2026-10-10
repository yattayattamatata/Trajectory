package com.trajectory.app.model;

/**
 * Fixed Limits used across the app.
 */
public final class Limits {

    /**
     * Maximum number of years a projection can run.
     */
    public static final int MAX_PROJECTION_YEARS = 100;

    /**
     * Maximum number of months a projection can run.
     */
    public static final int MAX_PROJECTION_MONTHS = MAX_PROJECTION_YEARS * 12;

    /**
     * Lowest acceptable value for a month.
     */
    public static final int MINIMUM_MONTH_VALUE = 1;

    /**
     * Highest acceptable value for a month.
     */
    public static final int MAXIMUM_MONTH_VALUE = 12;



// Constructor
    /**
     * Empty private constructor to prevent instantiation.
     */
    private Limits(){}
}

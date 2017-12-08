package edu.jabs.cinema.domain;

/**
 * Represents a seat of the cinema
 */
public class Seat
{
    // -----------------------------------------------------------------
    // Constants
    // -----------------------------------------------------------------

    /**
     * Type of seat LOWER_SEAT (front)
     */
    public static final String LOWER_SEAT = "LOWER_SEAT ";

    /**
     * Type of seat UPPER_SEAT (preferential)
     */
    public static final String UPPER_SEAT = "UPPER_SEAT";

    /**
     * Cost of a seat in LOWER_SEAT section (front)
     */
    private static final int LOWER_SEAT_COST = 8000;

    /**
     * Cost of a seat in UPPER_SEAT section(preferential)
     */
    private static final int UPPER_SEAT_COST = 11000;

    /**
     * Status of the seat available
     */
    private static final String STATUS_AVAILABLE = "AVAILABLE";

    /**
     * Status of the seat booked
     */
    private static final String STATUS_BOOKED = "BOOKED";

    /**
     * Status of the seat sold
     */
    private static final String STATUS_SOLD = "SOLD";

    // -----------------------------------------------------------------
    // Attributes
    // -----------------------------------------------------------------

    /**
     * Location of the seat in the cinema
     */
    private char row;

    /**
     * Type of seat
     */
    private String type;

    /**
     * Number of the seat within the row
     */
    private int number;

    /**
     * Status of the seat
     */
    private String status;

    // -----------------------------------------------------------------
    // Constructor Methods
    // -----------------------------------------------------------------

    /**
     * Constructor of the seat
     * @param rowSeat Location of the seat
     * @param numbeSeat Number of the seat within the row
     * @param typeSeatS Type of the seat. typeSeatS belongs to {LOWER_SEAT,UPPER_SEAT}
     */
    public Seat( char rowSeat, int numbeSeat, String typeSeatS )
    {
        row = rowSeat;
        number = numbeSeat;
        type = typeSeatS;
        status = STATUS_AVAILABLE;
    }

    // -----------------------------------------------------------------
    // Methods
    // -----------------------------------------------------------------

    /**
     * Returns the row of the seat
     * @return Row of the seat
     */
    public char getRow( )
    {
        return row;
    }

    /**
     * Returns the number of the seat within the row
     * @return Number of the seat within the row
     */
    public int getNumber( )
    {
        return number;
    }

    /**
     * Returns the cost of the seat
     * @return Cost of the seat
     */
    public int getCost( )
    {
        if( type.equals( LOWER_SEAT ) )
        {
            return LOWER_SEAT_COST;
        }
        else
        {
            return UPPER_SEAT_COST;
        }
    }

    /**
     * Indicates whether the seat is available
     * @return True if the seat is available, false to the contrary
     */
    public boolean isAvailable( )
    {
        return status == STATUS_AVAILABLE;
    }

    /**
     * Sets  the status of the seat as available
     */
    public void setAvailable( )
    {
        status = STATUS_AVAILABLE;
    }

    /**
     * Indicates if the seat is sold
     * @return True if the seat is sold, false to the contrary
     */
    public boolean estaVendida( )
    {
        return status == STATUS_SOLD;
    }

    /**
     * Sets the status of the seat as sold
     */
    public void sellSeat( )
    {
        status = STATUS_SOLD;
    }

    /**
     * Indicates if the seat is booked
     * @return True if the seat is booked, false to  the contrary
     */
    public boolean isBooked( )
    {
        return status == STATUS_BOOKED;
    }

    /**
     * Sets the status of the seat as booked
     */
    public void bookSeat( )
    {
        status = STATUS_BOOKED;
    }

    /**
     * Indicates if the seat is LOWER_SEAT type (front)
     * @return True if the seat is LOWER_SEAT type (front), false to the contrary
     */
    public boolean isLowerSeat( )
    {
        return type == LOWER_SEAT;
    }

    /**
     * Indicates if the seat is UPPER_SEAT type(preferential)
     * @return True if the seat is UPPER_SEAT type (preferential), false to the contrary
     */
    public boolean isUpperSeat( )
    {
        return type == UPPER_SEAT;
    }
}
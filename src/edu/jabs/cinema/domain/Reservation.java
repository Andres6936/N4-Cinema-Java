package edu.jabs.cinema.domain;

import java.util.ArrayList;

/**
 * This class represents a reservation
 */
public class Reservation
{
    // -----------------------------------------------------------------
    // Attributes
    // -----------------------------------------------------------------

    /**
     * Id of the customer that made this reservation
     */
    private int id;

    /**
     * Seats booked by the customer
     */
    private ArrayList bookedSeats;

    /**
     * Indicates whether the seats have already been paid off
     */
    private boolean paidOff;

    // -----------------------------------------------------------------
    // Constructor Methods
    // -----------------------------------------------------------------

    /**
     * Creates a new reservation
     */
    public Reservation( )
    {
        id = 0;
        paidOff = false;
        bookedSeats = new ArrayList( );
    }

    /**
     * Returns the id of the customer who made a reservation
     * @return Id of the customer who made this reservation
     */
    public int getId( )
    {
        return id;
    }

    /**
     * Changes the id of the reservation
     * @param setId Id of the customer
     */
    public void changeId( int setId )
    {
        id = setId;
    }

    /**
     * Adds a new seat to the reservation. <br>
     * <b>pre: </b> Status of the seat is available <br>
     * <b>post: </b> Status of the seat is booked and is on the reservation.
     * @param seat Seat to add. seat != null.
     * @throws Exception Seat is not available
     */
    public void addSeat( Seat seat ) throws Exception
    {
        if( !seat.isAvailable( ) )
        {
            throw new Exception( "Cannot book the seat " + seat.getRow( ) + seat.getNumber( ) + ":\r\nSeat is not available." );
        }
        seat.bookSeat( );
        bookedSeats.add( seat );
    }

    /**
     * Returns an ArrayList with the seats included in the reservation.
     * @return ArrayList with the seats included in the reservation.
     */
    public ArrayList getSeats( )
    {
        return bookedSeats;
    }

    /**
     * Cancels the reservation <br>
     * <b>post: </b> All seats of the reservation are available now.
     */
    public void cancel( )
    {
        for( int i = 0; i < bookedSeats.size( ); i++ )
        {
            Seat seat = ( Seat )bookedSeats.get( i );
            seat.setAvailable( );
        }
        bookedSeats.clear( );
    }

    /**
     * Returns the current cost of the reservation
     * @return Current cost of the reservation
     */
    public int getSumReservation( )
    {
        int total = 0;
        for( int i = 0; i < bookedSeats.size( ); i++ )
        {
            Seat seat = ( Seat )bookedSeats.get( i );
            total += seat.getCost( );
        }
        return total;
    }

    /**
     * Indicates if the reservation was already paid off
     * @return true if is already paid, false to the contrary
     */
    public boolean isPaidOff( )
    {
        return paidOff;
    }

    /**
     * Establishes that the reservation was already paid off. <br>
     * <b>post: </b> Reservation is paid and all seats remain sold.
     * @throws Exception Error generated when the reservation was already paid
     */
    public void returnPaid( ) throws Exception
    {
        if( paidOff )
        {
            throw new Exception( "REservation was already paid off" );
        }
        paidOff = true;
        for( int i = 0; i < bookedSeats.size( ); i++ )
        {
            Seat seat = ( Seat )bookedSeats.get( i );
            seat.sellSeat( );
        }
    }
}
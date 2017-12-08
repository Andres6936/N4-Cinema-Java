package edu.jabs.cinema.test;

import junit.framework.TestCase;

import edu.jabs.cinema.domain.Cinema;
import edu.jabs.cinema.domain.Reservation;
import edu.jabs.cinema.domain.Seat;

/**
 * This class is used to verify the methods of the class Reservation
 */
public class ReservationTest extends TestCase
{

    // -----------------------------------------------------------------
    // Attributes
    // -----------------------------------------------------------------

    /**
     * This is the class where the tests will be ran
     */
    private Reservation reservation;

    /**
     * Cinema to get the seats
     */
    private Cinema cinema;

    // -----------------------------------------------------------------
    // Methods
    // -----------------------------------------------------------------

    /**
     * Constructs a new cinema empty
     */
    private void setupScenario1( )
    {
        reservation = new Reservation( );
        cinema = new Cinema( );
    }

    /**
     * Test booked seat
     */
    public void testBookedSeat( )
    {
        setupScenario1( );

        try
        {
            // Finds the seat A1 and adds it to the reservation
            Seat seat = cinema.getSeat( 'A', 1 );
            reservation.addSeat( seat );
            assertEquals( "Seat is not booked.", true, seat.isBooked( ) );
            assertEquals( "reservation does not have a correct value", seat.getCost( ), reservation.getSumReservation( ) );
        }
        catch( Exception e )
        {
            fail( "Error when adding the seat to the reservation" );
        }
    }

    /**
     * Test double reservation
     */
    public void testDoubleReservation( )
    {
        setupScenario1( );
        Seat seat = null;
        try
        {
            // Finds the seat A1 and adds it to the reservation
            seat = cinema.getSeat( 'A', 1 );
            reservation.addSeat( seat );
            assertEquals( "Seat is not booked.", true, seat.isBooked( ) );

            try
            {
                // Books the seat again
                reservation.addSeat( seat );
                fail( "Seat cannot be booked twice" );
            }
            catch( Exception e1 )
            {
                assertTrue( "This exception was expected", true );
            }
        }
        catch( Exception e )
        {
            fail( "Error when adding the seat to the reservation" );
        }
    }

    /**
     * Test cancel reservation
     */
    public void testCancel( )
    {
        setupScenario1( );
        try
        {
            // Finds the seat A1 and adds it to the reservation
            Seat seat = cinema.getSeat( 'A', 1 );
            reservation.addSeat( seat );

            // cancels the reservation
            reservation.cancel( );
            assertEquals( "Seat is booked.", true, seat.isAvailable( ) );
            assertEquals( "Reservation does not have a correct value", 0, reservation.getSumReservation( ) );
        }
        catch( Exception e )
        {
            fail( "Error when adding the seat to the reservation" );
        }
    }
}
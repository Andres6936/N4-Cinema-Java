package edu.jabs.cinema.test;

import junit.framework.TestCase;
import edu.jabs.cinema.domain.Cinema;
import edu.jabs.cinema.domain.Reservation;
import edu.jabs.cinema.domain.Seat;
import edu.jabs.cinema.domain.Card;

/**
 * This class verifies whether the methods of the class Cinema are implemented properly
 */
public class CinemaTest extends TestCase
{

    // -----------------------------------------------------------------
    // Attributes
    // -----------------------------------------------------------------

    /**
     * This is the class where the tests will be ran
     */
    private Cinema cinema;

    /**
     * Reservations for the tests
     */
    private Reservation reservation;

    // -----------------------------------------------------------------
    // Methods
    // -----------------------------------------------------------------

    /**
     * Constructs a new cinema with a card for the id 25 and one reservation
     */
    private void setupScenario1( )
    {
        cinema = new Cinema( );
        reservation = new Reservation( );
        try
        {
            cinema.createCard( 25 );
        }
        catch( Exception e )
        {
            fail( "Error while creating the card" );
        }
    }

    /**
     * Constructs a new cinema empty and one reservation
     */
    private void setupScenario2( )
    {
        cinema = new Cinema( );
        reservation = new Reservation( );
    }

    /**
     * Test 1 - Create card
     */
    public void testCreateCard( )
    {
        setupScenario1( );

        try
        {
            assertEquals( "Card has a wrong value", Card.INITIAL_BALANCE, cinema.getCardBalance( 25 ) );
            assertEquals( "Cinema should have the balance of the sold card", Card.INITIAL_BALANCE, cinema.getTotalMoney( ) );
            try
            {
                cinema.createCard( 25 );
                fail( "Cannot create the same card twice" );
            }
            catch( Exception e )
            {
                assertTrue( "This exception was expected", true );
            }

        }
        catch( Exception e )
        {
            fail( "Error while creating the card" );
        }

    }

    /**
     * Test 2 - Payment reservatino in cash
     */
    public void testPaymentReservationCash( )
    {
        setupScenario2( );

        try
        {
            // Finds the seat A1 and adds it to the reservation
            Seat seat = cinema.getSeat( 'A', 1 );
            reservation.addSeat( seat );
            cinema.payReservationCash( reservation );
            assertEquals( "Seat has not been sold yet.", true, seat.estaVendida( ) );
            assertEquals( "Cinema should have the  sum of the sold reservation", reservation.getSumReservation( ), cinema.getTotalMoney( ) );
        }
        catch( Exception e )
        {
            fail( "Error when ading the seat to the reservation" );
        }
    }

    /**
     * Test 3 - Payment with card
     */
    public void testPaymentReservationCard( )
    {
        setupScenario1( );

        try
        {
            // Finds the seat A1 and adds it to the reservation
            Seat seat = cinema.getSeat( 'A', 1 );
            reservation.addSeat( seat );
            cinema.payCardReservation( reservation, 25 );
            int newBalance = ( int ) ( Card.INITIAL_BALANCE - ( reservation.getSumReservation( ) * ( 1 - Card.DISCOUNT ) ) );
            assertEquals( "Seat has not been sold yet.", true, seat.estaVendida( ) );
            assertEquals( "Balance of the card is not valid", newBalance, cinema.getCardBalance( 25 ) );
        }
        catch( Exception e )
        {
            fail( "Error when adding the seat to the reservation" );
        }
    }

    /**
     * Test 4 - Store Reservation
     */
    public void testStoreReservation( )
    {
        setupScenario2( );

        try
        {
            // Finds the seat A1 and adds it to the reservation
            Seat seat = cinema.getSeat( 'A', 1 );
            reservation.addSeat( seat );
            cinema.createCard( 25 );
            cinema.saveReservation( 25, reservation );
            assertEquals( "Id of the reservation is not valid", 25, reservation.getId( ) );
            assertEquals( "Reservation was not stored properly", reservation, cinema.getReservation( 25 ) );
            assertEquals( "Reservation does not report to be stored", true, cinema.isSaved( reservation ) );
        }
        catch( Exception e )
        {
            fail( "Error when adding the seat to the reservation" );
        }
    }

    /**
     * Test 5 - Test of initialization
     */
    public void testInitialization( )
    {
        setupScenario2( );
        int totalSeats = ( Cinema.LOWER_ROWS + Cinema.UPPER_ROWS ) * Cinema.SEATS_PER_ROW;
        assertEquals( "Number  of seat is not correct", totalSeats, cinema.getSeats( ).length );

        char lastLowerRows = 'A' + Cinema.LOWER_ROWS - 1;
        char upperRow = 'A' + Cinema.LOWER_ROWS;

        try
        {
            Seat seat = cinema.getSeat( lastLowerRows, 1 );
            assertEquals( "Seat should be from LOWER_ROWS (front)", true, seat.isLowerSeat( ) );
            assertEquals( "Seat should cost as LOWER_SEAT costs", 8000, seat.getCost( ) );

            seat = cinema.getSeat( upperRow, 1 );
            assertEquals( "Seat should be from UPPER_ROWS (preferential)", true, seat.isUpperSeat( ) );
            assertEquals( "Seat should cost as UPPER_SEAT costs", 11000, seat.getCost( ) );
        }
        catch( Exception e )
        {
            fail( "Error when getting the seat" );
        }

    }

    /**
     * Test 6 - Test for getSeat()
     */
    public void testGetSeat( )
    {
        setupScenario2( );

        int notValidNumber = Cinema.SEATS_PER_ROW + 1;
        char notValidRow = 'A' + Cinema.LOWER_ROWS + Cinema.UPPER_ROWS;

        try
        {
            // Finds a valid seat
            cinema.getSeat( 'A', 1 );
        }
        catch( Exception e )
        {
            fail( "Seat could not be obtained" );
        }

        try
        {
            // Finds a seat with an invalid number
            cinema.getSeat( 'A', notValidNumber );
            fail( "Number of the seat is not valid" );
        }
        catch( Exception e1 )
        {
            assertTrue( "This exception was expected", true );
        }

        try
        {
            // Finds a seat within an invalid row
            cinema.getSeat( notValidRow, 1 );
            fail( "Row of the seat is not valid" );
        }
        catch( Exception e1 )
        {
            assertTrue( "This exception was expected", true );
        }
    }
}
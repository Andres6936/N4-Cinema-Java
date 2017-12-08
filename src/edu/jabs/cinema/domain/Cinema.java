package edu.jabs.cinema.domain;

import java.util.*;

/**
 * This class represents a cinema
 */
public class Cinema
{
    // -----------------------------------------------------------------
    // Constants
    // -----------------------------------------------------------------

    /**
     * Number of lower(front) rows
     */
    public static final int LOWER_ROWS = 8;

    /**
     * Number of upper(preferential) rows.
     */
    public static final int UPPER_ROWS = 3;

    /**
     * Number of seat per row
     */
    public static final int SEATS_PER_ROW = 20;

    // -----------------------------------------------------------------
    // Attributes
    // -----------------------------------------------------------------

    /**
     * Array of seats of the cinema
     */
    private Seat[] seats;

    /**
     * Vector of pending reservations of the cinema
     */
    private ArrayList reservations;

    /**
     * Vector if cards handled by the cinema
     */
    private ArrayList cards;

    /**
     * Total amount of money collected
     */
    private int totalMoney;

    // -----------------------------------------------------------------
    // Constructor Methods
    // -----------------------------------------------------------------

    /**
     * Constructs the cinema. <br>
     * <b>post: </b> A new cinema is created with all seats available, and without any reservations and any cards.
     */
    public Cinema( )
    {
        totalMoney = 0;
        seats = new Seat[ ( LOWER_ROWS + UPPER_ROWS ) * SEATS_PER_ROW];
        reservations = new ArrayList( );
        cards = new ArrayList( );

        createSeats( );
    }

    // -----------------------------------------------------------------
    // Methods
    // -----------------------------------------------------------------

    /**
     * Returns the total amount of money collected. <br>
     * <b>post: </b> result = ({sold tickets} + {sold cards}).
     * @return total amount of money collected.
     */
    public int getTotalMoney( )
    {
        return totalMoney;
    }

    /**
     * A new card is created in the cinema. <br>
     * <b>pre: </b> There does not exist a card emitted to that Id. <br>
     * <b>post: </b> A new card was created with an initial balance.
     * @param id Id of the customer.
     * @throws Exception Customer already has a card.
     */
    public void createCard( int id ) throws Exception
    {
        // Verifies if the customer has a card or not
        if( cardExists( id ) )
        {
            throw new Exception( "User with id '" + id + "' already has a card." );
        }

        // Creates a card for the customer
        Card card = new Card( id );
        cards.add( card );
        totalMoney += Card.INITIAL_BALANCE;
    }

    /**
     * Recharges a new card with the top-up sum. <br>
     * <b>pre: </b> Exists a card emitted to that id. <br>
     * <b>post: </b> Card BalanceSaldo Card = Card Balance + top-up.
     * @param id Id of the customer
     * @throws Exception Customer does not have any card.
     */
    public void topUpCard( int id ) throws Exception
    {
        Card card = getCard( id );
        card.topUp( );
        totalMoney += Card.TOP_UP;
    }

    /**
     * Returns the current balance of a customer's card. <br>
     * <b>pre: </b> Exists a card emitted to that Id. <br>
     * <b>post: </b> Returns the balance. Domain was not modified.
     * @param id Customer's Id.
     * @return balance available in the card.
     * @throws Exception Throws exception when there does not exist a card corresponding to the specified id.
     */
    public int getCardBalance( int id ) throws Exception
    {
        Card card = getCard( id );
        return card.getAvailableSum( );
    }

    /**
     * Finds if a reservation was already saved in the system <br>
     * <b>pre: </b> reservation!= null. <br>
     * @param reservation Reservation that will be verified
     * @return True if it has already been saved, False if not
     */
    public boolean isSaved( Reservation reservation )
    {
        return reservations.contains( reservation );
    }

    /**
     * Saves a reservation in the system <br>
     * <b>pre: </b> reservation != null.
     * @param id Customer's id
     * @param reservation Reservation that will be saved
     * @throws Exception
     */
    public void saveReservation( int id, Reservation reservation ) throws Exception
    {
        // Validates if the customer has a card
        if( !cardExists( id ) )
        {
            throw new Exception( "Customer does not have a card. System could not save the reservation." );
        }
        reservation.changeId( id );
        reservations.add( reservation );
    }

    /**
     * Cancels a reservation and removes it from the cinema
     * @param reservation Reservation that will be deleted
     */
    public void cancelReservation( Reservation reservation )
    {
        reservation.cancel( );
        reservations.remove( reservation );
    }

    /**
     * Finds a reservation in the cinema <br/>
     * <b>pre: </b> Exists a reservation associated with the specified id <br />
     * <b>post: </b> Returns the reservation
     * @param id Customer's id
     * @return Reservation of the customer
     * @throws Exception Customer does not have a reservation in the cinema.
     */
    public Reservation getReservation( int id ) throws Exception
    {
        for( int i = 0; i < reservations.size( ); i++ )
        {
            Reservation reservation = ( Reservation )reservations.get( i );
            if( reservation.getId( ) == id )
            {
                return reservation;
            }
        }

        // If the reservation is not found, throws an exception
        throw new Exception( "User with id '" + id + "' does not have any reservation registered." );
    }

    /**
     * Pays a reservation with a CARDCINEMA card <br/>
     * <b>pre: </b> Exists a reservation associated with the specified id && reservation != null <br />
     * <b>post: </b> All seats are sold && card Balance -= (Reservation.Total - 10%)
     * @param reservation Reservation that will be paid off
     * @param id Id of the card's owner
     * @throws Exception throws an error if the customer does not have a card, does not have enough balance or the reservation was already paid
     * @throws Exception If the reservation does not have a sum to be paid off.
     */
    public void payCardReservation( Reservation reservation, int id ) throws Exception
    {
        if( reservation.isPaidOff( ) )
        {
            throw new Exception( "Reservation was already paid." );
        }
        if( reservation.getSumReservation( ) == 0 )
        {
            throw new Exception( "Reservation does not have a sum to be paid off." );
        }
        // Calculates the total sum with the discount
        int total = ( int ) ( reservation.getSumReservation( ) * ( 1 - Card.DISCOUNT ) );
        Card card = getCard( id );
        card.subtractMoney( total );
        reservation.returnPaid( );

        // Deletes the reservation if this one exists on the array
        reservations.remove( reservation );
    }

    /**
     * Pays a reservation in cash <br/>
     * <b>pre: </b> reservation != null <br />
     * <b>post: </b> All seats are sold && totalMoney += Reservation.Total;
     * @param reservation Reservation to be paid off
     * @throws Exception Reservation was already paid.
     * @throws Exception If the reservation does not have a sum to be paid off.
     */
    public void payReservationCash( Reservation reservation ) throws Exception
    {
        if( reservation.isPaidOff( ) )
        {
            throw new Exception( "Reservation was already paid." );
        }
        if( reservation.getSumReservation( ) == 0 )
        {
            throw new Exception( "Reservation does not have a sum to be paid off." );
        }
        int total = reservation.getSumReservation( );
        totalMoney += total;
        reservation.returnPaid( );

        reservations.remove( reservation );
    }

    /**
     * Returns available rows to the specified type of seat <br/>
     * <b>pre: </b> seatType = Seat.UPPER_SEAT|| seatType = Seat.LOWER_SEAT <br />
     * <b>post: </b> Returns all rows with having the specified type of seat
     * @param seatType
     * @return Array of chars with the corresponding characters of the rows
     */
    public char[] getRows( String seatType )
    {
        char[] rows;
        if( seatType.equals( Seat.LOWER_SEAT ) )
        {
            rows = new char[LOWER_ROWS];
            char currentChar = 'A';
            for( int i = 0; i < LOWER_ROWS; i++ )
            {
                rows[ i ] = currentChar;
                currentChar = getNextChar( currentChar );
            }
            return rows;
        }
        else if( seatType.equals( Seat.UPPER_SEAT ) )
        {
            rows = new char[UPPER_ROWS];
            char currentChar = 'A' + LOWER_ROWS;
            for( int i = 0; i < UPPER_ROWS; i++ )
            {
                rows[ i ] = currentChar;
                currentChar = getNextChar( currentChar );
            }
            return rows;
        }
        else
        {
            return new char[0];
        }
    }

    /**
     * Returns all seats of the cinema(movie theater)
     * @return all seat of the theater
     */
    public Seat[] getSeats( )
    {
        return seats;
    }

    /**
     * Returns all available seats in the specified row <br>
     * <b>pre: </b> row exists in the cinema <br>
     * <b>post: </b> Returns all available seats in the specified row
     * @param row Row
     * @return Available seats
     * @throws Exception Row does not exist in the cinema
     */
    public ArrayList getAvailableSeats( char row ) throws Exception
    {
        if( getCharFactor( row ) < 0 || getCharFactor( row ) > ( ( LOWER_ROWS + UPPER_ROWS ) * SEATS_PER_ROW ) - 1 )
        {
            throw new Exception( "Row does not exist in the cinema" );
        }
        int InitialSeat = getCharFactor( row ) * SEATS_PER_ROW;
        int EndingSeat = InitialSeat + SEATS_PER_ROW;
        ArrayList answer = new ArrayList( );
        for( int i = InitialSeat; i < EndingSeat; i++ )
        {
            if( seats[ i ].isAvailable( ) )
            {
                answer.add( seats[ i ] );
            }
        }
        return answer;

    }

    /**
     * Returns the seat located in the specified row and number <br>
     * <b>pre: </b> Row exists in the cinema, 0 < number < SEATS_PER_ROW. <br>
     * <b>post: </b> Returns the seat in the specified position.
     * @param row Row of the seat.
     * @param number Number of the seat within the row.
     * @return Seat located in the specified position
     * @throws Exception Seat does not exist in the cinema
     */
    public Seat getSeat( char row, int number ) throws Exception
    {
        if( number < 0 || number > SEATS_PER_ROW )
        {
            throw new Exception( "Such seat does not exist in the cinema" );
        }
        int position = ( getCharFactor( row ) * SEATS_PER_ROW ) + number - 1;
        if( position < 0 || position >= seats.length )
        {
            throw new Exception( "Such seat does not exist in the cinema" );
        }
        return seats[ position ];
    }

    /**
     * Initializes the cinema creating the seats. <br>
     * <b>pre: </b> seats[] has a size ( LOWER_ROWS + UPPER_ROWS ) * SEATS_PER_ROW. <br>
     * <b>post: </b> Seats initialized with the its values.
     */
    private void createSeats( )
    {
        char currentRow = 'A';
        int currenNumber = 1;
        // Creates the rows of the LOWER_ROWS(front) part
        for( int i = 0; i < ( LOWER_ROWS * SEATS_PER_ROW ); i++ )
        {
            seats[ i ] = new Seat( currentRow, currenNumber, Seat.LOWER_SEAT );
            if( currenNumber == SEATS_PER_ROW )
            {
                currentRow = getNextChar( currentRow );
                currenNumber = 1;
            }
            else
            {
                currenNumber++;
            }
        }

        // Creates the rows of the UPPER_ROWS (preferential) part
        for( int i = 0; i < ( UPPER_ROWS * SEATS_PER_ROW ); i++ )
        {
            seats[ i + ( LOWER_ROWS * SEATS_PER_ROW ) ] = new Seat( currentRow, currenNumber, Seat.UPPER_SEAT );
            if( currenNumber == SEATS_PER_ROW )
            {
                currentRow = getNextChar( currentRow );
                currenNumber = 1;
            }
            else
            {
                currenNumber++;
            }
        }
    }

    /**
     * Given a character, returns the one which follows, according to ASCII code
     * @param character Initial char
     * @return Next char.
     */
    private char getNextChar( char character )
    {
        return ( char ) ( character + 1 );
    }

    /**
     * Returns the factor of the character. For example: 'A' = 0, 'B' = 1, ...
     * @param character Char being found.
     * @return Factor of the char.
     */
    private int getCharFactor( char character )
    {
        return character - 'A';
    }

    /**
     * Finds a card given an id number.
     * @param id Id of the user.
     * @return User's card.
     * @throws Exception Exception thrown when there is not a card having the specified id number.
     */
    private Card getCard( int id ) throws Exception
    {
        // Finds the card on the cards list
        for( int i = 0; i < cards.size( ); i++ )
        {
            Card card = ( Card )cards.get( i );
            if( card.getId( ) == id )
            {
                return card;
            }
        }

        // An exception is thrown if the card is not found
        throw new Exception( "User with id '" + id + "' does not have any registered card." );
    }

    /**
     * Finds whether a card exists, given an id number
     * @param id Id number
     * @return True if exists, False to the contrary.
     */
    private boolean cardExists( int id )
    {
        // Finds the card on the cards list
        for( int i = 0; i < cards.size( ); i++ )
        {
            Card card = ( Card )cards.get( i );
            if( card.getId( ) == id )
            {
                return true;
            }
        }
        return false;
    }


    // -----------------------------------------------------------------
    // Extension Points
    // -----------------------------------------------------------------

    /**
     * Extension point 1
     * @return answer 1
     */
    public String method1( )
    {
        return "answer 1";
    }

    /**
     * Extension point 2
     * @return answer 2
     */
    public String method2( )
    {
        return "answer 2";
    }
}
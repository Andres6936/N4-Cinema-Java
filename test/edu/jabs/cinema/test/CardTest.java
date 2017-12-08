package edu.jabs.cinema.test;

import junit.framework.TestCase;
import edu.jabs.cinema.domain.Card;

/**
 * This class verifies the methods of the class Card
 */
public class CardTest extends TestCase
{
    // -----------------------------------------------------------------
    // Attributes
    // -----------------------------------------------------------------

    /**
     * This is the class where the tests will be ran
     */
    private Card card;

    // -----------------------------------------------------------------
    // Methods
    // -----------------------------------------------------------------

    /**
     * Constructs a new card empty
     */
    private void setupScenario1( )
    {
        card = new Card( 1 );
    }

    /**
     * Test for the initial balance
     */
    public void testInitialBalance( )
    {
        setupScenario1( );
        assertEquals( "Initial balance is not valid.", Card.INITIAL_BALANCE, card.getAvailableSum( ) );
    }

    /**
     * Test to top up a card
     */
    public void testTopUp( )
    {
        setupScenario1( );
        int cardBalance = card.getAvailableSum( ) + Card.TOP_UP;
        card.topUp( );
        assertEquals( "Balance after topping up the card is not valid", cardBalance, card.getAvailableSum( ) );
    }

    /**
     * Test deducting amount of money
     */
    public void testSubtract( )
    {
        setupScenario1( );
        int cardBalance = card.getAvailableSum( ) - 5230;
        try
        {
            card.subtractMoney( 5230 );
        }
        catch( Exception e )
        {
            fail( "Error while subtracting money from the card." );
        }
        assertEquals( "Balance after subtracting money is not valid.", cardBalance, card.getAvailableSum( ) );
    }

    /**
     * Test deducting greater amount of money
     */
    public void testSubtractGreaterAmount( )
    {
        setupScenario1( );
        try
        {
            card.subtractMoney( Card.INITIAL_BALANCE + 1 );
            fail( "It should not be possible to subtract a greater amount than the card's balance" );
        }
        catch( Exception e )
        {
            assertTrue( "This exception was expected", true );
        }
    }
}

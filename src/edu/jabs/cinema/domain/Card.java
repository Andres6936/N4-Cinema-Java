package edu.jabs.cinema.domain;

/**
 * This class represents a CARDCINEMA CARD
 */
public class Card
{
    // -----------------------------------------------------------------
    // Constants
    // -----------------------------------------------------------------

    /**
     * Sum of initial balance
     */
    public static final int INITIAL_BALANCE = 70000;

    /**
     * Sum of the top-up
     */
    public static final int TOP_UP = 50000;

    /**
     * Discount for purchasing with card
     */
    public static final double DISCOUNT = 0.1;

    // -----------------------------------------------------------------
    // Attributes
    // -----------------------------------------------------------------

    /**
     * Money available in the card
     */
    private int availableMoney;

    /**
     * Id of the card's user
     */
    private int id;

    // -----------------------------------------------------------------
    // Constructor Methods
    // -----------------------------------------------------------------

    /**
     * Constructor of the card
     * @param idD ID of the card's owner
     */
    public Card( int idD )
    {
        id = idD;
        availableMoney = INITIAL_BALANCE;
    }

    // -----------------------------------------------------------------
    // Methods
    // -----------------------------------------------------------------

    /**
     * Deducts a specified amount from the card. <br>
     * <b>pre: </b> amount > 0 && availableMoney > amount. <br>
     * <b>post: </b>availableMoney = availableMoney - amount.
     * @param amount Amount of money to deduct.
     * @throws Exception There are not sufficient funds in the card.
     */
    public void subtractMoney( int amount ) throws Exception
    {
        if( amount > availableMoney )
        {
            throw new Exception( "Card does not have sufficient funds" );
        }
        availableMoney -= amount;
    }

    /**
     * Tops up the card with the predefined TOP_UP amount. <br>
     * <b>post: </b> availableMoney = availableMoney + TOP_UP.
     */
    public void topUp( )
    {
        availableMoney += TOP_UP;
    }

    /**
     * Returns the amount of money available in the card.
     * @return Amount of money available in the card
     */
    public int getAvailableSum( )
    {
        return availableMoney;
    }

    /**
     * Returns the id of the card's owner
     * @return Id of the card's owner
     */
    public int getId( )
    {
        return id;
    }
}
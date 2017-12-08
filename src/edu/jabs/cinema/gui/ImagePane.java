package edu.jabs.cinema.gui;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 * Pane with the image of the title
 */
public class ImagePane extends JPanel
{
    // -----------------------------------------------------------------
    // Attributes of the GUI
    // -----------------------------------------------------------------

    /**
     * Image of the title
     */
    private JLabel image;

    // -----------------------------------------------------------------
    // Constructor Methods
    // -----------------------------------------------------------------

    /**
     * Constructor with no parameters
     */
    public ImagePane( )
    {
        FlowLayout layout = new FlowLayout( );
        layout.setHgap( 0 );
        layout.setVgap( 0 );
        setLayout( layout );

        // Loads the image
        ImageIcon icon = new ImageIcon( "data/title.jpg" );

        // Adds it to the label
        image = new JLabel( "" );
        image.setIcon( icon );
        add( image );

        // Background color white
        setBackground( Color.WHITE );
        setBorder( new LineBorder( Color.GRAY ) );
    }
}

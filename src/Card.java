import java.awt.*;

public class Card {

    /*
    R - RED
    G - GREEN
    B - BLUE
    Y - YELLOW

    0 (4 of each, 1 per colour) ****************
    1->9 - NORMAL CARDS (8 of each, 2 per colour)
    10 - CANCEL (8 total, 2 of each colour)
    11 - REVERSE (8 total, 2 of each colour)
    12 - PLUS 2 (8 total, 2 of each colour)
    13 - CHANGE COLOUR (4 total) ******************
    14 - PLUS 4 (4 total) **********************

     */

    int type;
    Color colour;

    public Card(int type, Color colour)
    {
        this.type = type;
        this.colour = colour;
    }

}

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    ArrayList<Card> cards = new ArrayList<>(108);

    private Color[] Cols = new Color[]
            {
                    Color.RED,
                    Color.BLUE,
                    Color.GREEN,
                    Color.YELLOW,
                    Color.BLACK
            };

    public Deck(boolean shuffled)
    {
        cards = genCards(shuffled);
    }

    private ArrayList<Card> genCards(boolean shuffle)
    {
        ArrayList<Card> tempDeck = new ArrayList<>(108);

        for(int colLoop = 0; colLoop < 4; colLoop++)
        {
            for(int typeLoop = 0; typeLoop <= 12; typeLoop++)
            {
                tempDeck.add(new Card(typeLoop, Cols[colLoop]));
                if(typeLoop != 0)//not a 0
                {
                    tempDeck.add(new Card(typeLoop, Cols[colLoop]));
                }
            }
        }

        for(int typeLoop = 0; typeLoop < 2; typeLoop++)
        {
            for(int blackLoop = 0; blackLoop < 4; blackLoop++)
            {
                tempDeck.add(new Card(typeLoop+13, Cols[4]));
            }
        }

        if(shuffle)
        {
            Collections.shuffle(tempDeck);
        }

        return tempDeck;
    }

}

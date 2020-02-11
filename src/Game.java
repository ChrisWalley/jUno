import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import static java.awt.event.KeyEvent.*;

public class Game extends JFrame implements MouseListener, MouseMotionListener, KeyListener, ActionListener{

    private Deck deck;
    private ArrayList<Player> players;
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private int cardWidth = 75;
    private int cardHeight = 100;
    private double xOffset = 0.0;
    private double yOffset = 0.0;

    Dimension offDimension;
    Image offImage;
    Graphics2D offGraphics;
    Polygon deckArea = null;
    Polygon [] playerAreas;
    int numPlayers;
    Image topCard = null;
    Point cursor = null;

    Card movingCard = null;

    public Game(Deck deck, int numPlayers)
    {
        this.numPlayers = numPlayers;
        this.deck = deck;
        players = new ArrayList<>(numPlayers);
    }




    public void run()
    {
        setBounds(0, 0, screenSize.width, screenSize.height);
        setLocationRelativeTo(null);
        addMouseMotionListener(this);
        addMouseListener(this);
        addKeyListener(this);
        setUndecorated(true);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //createBufferStrategy(2);
        //strategy = getBufferStrategy();
        new javax.swing.Timer(10, this).start();
        init();

    }

    public void init()
    {
        //topCard = createCard(deck.cards.get(0));
        deckArea = new Polygon();
        deckArea.addPoint((screenSize.width - cardWidth)/2,(screenSize.height - cardHeight)/2);
        deckArea.addPoint((screenSize.width - cardWidth)/2,(screenSize.height + cardHeight)/2);
        deckArea.addPoint((screenSize.width + cardWidth)/2,(screenSize.height + cardHeight)/2);
        deckArea.addPoint((screenSize.width + cardWidth)/2,(screenSize.height - cardHeight)/2);

        playerAreas = genPlayerAreas(numPlayers);
    }

    public Polygon [] genPlayerAreas(int numPlayers)
    {
        int areaWidth = 100;
        int areaHeight = 50;
       Polygon [] areas = new Polygon[numPlayers];

       Polygon modelTopAndBottom = new Polygon();
        modelTopAndBottom.addPoint(0,0);
        modelTopAndBottom.addPoint(0,areaHeight);
        modelTopAndBottom.addPoint(areaWidth,areaHeight);
        modelTopAndBottom.addPoint(areaWidth,0);

        Polygon modelSides = new Polygon();
        modelSides.addPoint(0,0);
        modelSides.addPoint(0,areaWidth);
        modelSides.addPoint(areaHeight,areaWidth);
        modelSides.addPoint(areaHeight,0);

       for(int loop = 0; loop < numPlayers; loop++)
       {

       }

       return areas;
    }


    @Override
    public void paint(Graphics g)
    {
        paintComponents(g);
    }

    @Override
    public void paintComponents(Graphics g)
    {
        if ((offGraphics == null)
                || (screenSize.width != offDimension.width)
                || (screenSize.height != offDimension.height))
        {
            offDimension = screenSize;
            offImage = createImage(screenSize.width, screenSize.height);
            offGraphics = (Graphics2D)offImage.getGraphics();
        }

        offGraphics.setColor(getBackground());
        offGraphics.fillRect(0, 0, screenSize.width, screenSize.height);
        offGraphics.setColor(Color.black);

        //draw

        drawDeck(offGraphics);
        drawMovingCard(offGraphics);
        //offGraphics.setColor(Color.MAGENTA);
        //offGraphics.drawPolygon(deckArea);

        g.drawImage(offImage, 0, 0, this);
    }

    private Image createCard(Card card)
    {
        int buffer = 5;
        Image i = createImage(cardWidth+2, cardHeight+2);
        Graphics2D g = (Graphics2D) i.getGraphics();

        g.setColor(card.colour);
        g.fillRect(0, 0, cardWidth, cardHeight);
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, cardWidth, cardHeight);

        g.setColor(Color.WHITE);

        Shape oval = AffineTransform.getRotateInstance(Math.PI/6)
                .createTransformedShape(new Ellipse2D.Double(0, 0, 3*cardWidth/4, cardHeight));

        oval = AffineTransform.getTranslateInstance(cardWidth/2 + 2, -cardHeight/16).createTransformedShape(oval);
        g.fill(oval);
        g.setColor(Color.WHITE);

        g.setFont(new Font("Type", 1, 20));
        int stringWidth = g.getFontMetrics().stringWidth(""+card.type);
        int stringHeight = g.getFontMetrics().getHeight();
        g.drawString(""+card.type, buffer, (stringHeight)/2 + buffer);//top left
        g.drawString(""+card.type, cardWidth - stringWidth - buffer, cardHeight - buffer);//Bottom right


        g.setColor(card.colour);

        g.setFont(new Font("Type", 1, 40));
        stringWidth = g.getFontMetrics().stringWidth(""+card.type);
        stringHeight = g.getFontMetrics().getHeight();

        g.drawString(""+card.type, (cardWidth - stringWidth)/2, (cardHeight + stringHeight/2)/2);//middle

        return i;
    }

    private void drawMovingCard(Graphics2D g)
    {
        if(movingCard!=null)
        {
            Image card = createCard(movingCard);
            g.drawImage(card,(int)(Math.round(cursor.x - xOffset)),(int)(Math.round(cursor.y - yOffset)),this);

        }
    }

    private void drawDeck(Graphics2D g)
    {
        if(deck.cards.size() > 0)
        {
            Card card = deck.cards.get(0);
            topCard = createCard(card);
            g.drawImage(topCard,(screenSize.width - topCard.getWidth(this))/2,(screenSize.height - topCard.getHeight(this))/2,this);
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode)
        {
            case VK_Q:
                this.dispose();
                System.exit(0);
                break;
            case VK_N:
                if(deck.cards.size() > 0)
                {
                    deck.cards.remove(0);
                    repaint();
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        cursor = e.getPoint();

        xOffset = cursor.x - deckArea.getBounds2D().getMinX();
        yOffset = cursor.y - deckArea.getBounds2D().getMinY();

        if(cursor != null && deckArea.contains(cursor) && deck.cards.size() > 0)
        {
            movingCard = deck.cards.remove(0);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(movingCard!=null)
        {
            boolean inArea = false;
            for(Polygon p : playerAreas)
            {
                if(p.contains(e.getPoint()))
                {

                    inArea = true;
                    break;
                }
            }
            if(!inArea)
            {
                deck.cards.add(0,movingCard);
            }

        }
        movingCard = null;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(movingCard!=null)
        {
            cursor = e.getPoint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}

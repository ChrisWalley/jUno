public class tempMain {
    public static void main(String[] args) {
        Deck shuffled = new Deck(true);
        Game g = new Game(shuffled, 1);
        g.run();
    }
}

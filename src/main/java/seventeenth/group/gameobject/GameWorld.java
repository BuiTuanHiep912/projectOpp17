package seventeenth.group.gameobject;

import java.awt.Graphics2D;

public class GameWorld {

    //protected Hero hero;
    protected PhysicalMap physicalMap;

    public GameWorld() {
        // Hero = new Hero(300, 300, 100, 100, 0.1f);
        physicalMap = new PhysicalMap(0, 0);
    }

    public void Update() {
        // hero.update()
    }

    public void Render(Graphics2D g2) {
        // hero.draw(g2);
        physicalMap.draw(g2);
    }
}

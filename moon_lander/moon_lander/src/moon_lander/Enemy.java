package moon_lander;

import java.util.Random;
import java.awt.Graphics2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.net.URL;

public class Enemy {
    private Random random;
    private Rectangle bounds;
    private BufferedImage image;
    private int x;
    private int y;

    public Enemy(String img) {
        LordImage(img);
        random = new Random();
        ResetEnemy();
        bounds = new Rectangle(x, y, getImgWidth(), getImgHeight());
    }

    public int getImgWidth() {
        return image.getWidth();
    }

    public int getImgHeight() {
        return image.getHeight();
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public boolean collision(Rectangle a, Rectangle b) {
        return a.intersects(b);
    }

    public void ResetEnemy() {
        x = random.nextInt(Framework.frameWidth - getImgWidth() - 50);
        y = 200;
        bounds = new Rectangle(x, y, getImgWidth(), getImgHeight());
    }

    public void LordImage(String imgURL) {
        try {
            URL enemyImgUrl = this.getClass().getResource(imgURL);
            image = ImageIO.read(enemyImgUrl);
        } catch (Exception e) {
            Logger.getLogger(Enemy.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void Draw(Graphics2D g2d) {
        g2d.drawImage(image, x, y, null);
    }
}

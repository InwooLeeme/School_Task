package moon_lander;

import java.awt.Graphics2D;
import java.net.URL;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.awt.Rectangle;

public class Bullet {

    /* 총알 좌표 */
    public int x;

    public int y;

    private BufferedImage bulletImage;

    public int bulletImageWidth;

    public int bulletImageHeight;

    public Bullet(int _x, int _y) {
        LordImage();
    }

    // 움직임을 위한 메소드
    public void tick() {
        y -= 5;
    }

    public void LordImage() {
        try {
            URL bulletImageURL = this.getClass().getResource("/resources/images/bullet.png");
            bulletImage = ImageIO.read(bulletImageURL);
            bulletImageWidth = bulletImage.getWidth();
            bulletImageHeight = bulletImage.getHeight();
        } catch (Exception e) {
            Logger.getLogger(Moving_Enemy.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    // 충돌 확인을 위한 Rectangle 메소드
    public Rectangle drawRect() {
        return new Rectangle(x, y, bulletImageWidth, bulletImageHeight);
    }

    public void Draw(Graphics2D g2d) {
        g2d.drawImage(bulletImage, x, y, null);
    }
}

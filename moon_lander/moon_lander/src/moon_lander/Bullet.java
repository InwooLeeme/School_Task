package moon_lander;

import java.awt.Graphics2D;
import java.net.URL;
import java.util.Random;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.awt.Rectangle;

public class Bullet {

    private Random random = new Random();

    /* 총알 좌표 */
    private int x;

    private int y;

    public int bulletSpeedY;

    private BufferedImage bulletImage;

    public int bulletImageWidth;

    public int bulletImageHeight;

    public Bullet(int _x, int _y) {
        this.x = _x;
        this.y = _y;
        LordImage();
        this.bulletSpeedY = random.nextInt(50);
        if (this.bulletSpeedY == 0) {
            this.bulletSpeedY = 5;
        }
    }

    // 움직임을 위한 메소드
    public void tick() {
        y -= bulletSpeedY;
        if (y <= 0)
            y = Framework.frameHeight - 40;

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

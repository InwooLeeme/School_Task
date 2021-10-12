package moon_lander;

import java.util.LinkedList;
import java.awt.Graphics2D;

public class EnemyController {

    LinkedList<Moving_Enemy> e = new LinkedList<Moving_Enemy>();
    LinkedList<MovingEnemyWithBullet> bulletsEnemy = new LinkedList<MovingEnemyWithBullet>();
    LinkedList<Bullet> bullets = new LinkedList<Bullet>();

    PlayerRocket playerRocket;
    Moving_Enemy tempEnemy;
    MovingEnemyWithBullet tempBulletEnemy;
    Bullet tempBullet;

    public EnemyController(int count) {
        for (int i = 0; i < count; i++) {
            addEnemy(new Moving_Enemy());
            addEnemyWithBullet(new MovingEnemyWithBullet());
            addBullet(new Bullet(bulletsEnemy.get(i).x, bulletsEnemy.get(i).y));
        }
    }

    public void ResetController(int count) {
        e.clear();
        bulletsEnemy.clear();
        bullets.clear();
        for (int i = 0; i < count; i++) {
            addEnemy(new Moving_Enemy());
            addEnemyWithBullet(new MovingEnemyWithBullet());
            addBullet(new Bullet(bulletsEnemy.get(i).x, bulletsEnemy.get(i).y));
        }
    }

    public void Draw(Graphics2D g2d) {
        for (int i = 0; i < e.size(); i++) {
            tempEnemy = e.get(i);
            tempBulletEnemy = bulletsEnemy.get(i);
            tempBullet = bullets.get(i);
            tempEnemy.Draw(g2d);
            tempBulletEnemy.Draw(g2d);
            tempBullet.Draw(g2d);
        }
    }

    public void Update() {
        for (int i = 0; i < e.size(); i++) {
            tempEnemy = e.get(i);
            tempBullet = bullets.get(i);
            tempEnemy.tick();
            tempBullet.tick();
        }
    }

    public LinkedList<Moving_Enemy> getEnemyList() {
        return e;
    }

    public void addEnemy(Moving_Enemy Enemy) {
        e.add(Enemy);
    }

    public void removeEnemy(Moving_Enemy Enemy) {
        e.remove(Enemy);
    }

    /* 총알 추가 */
    public void addBullet(Bullet b) {
        bullets.add(b);
    }

    /* 총알 삭제 */
    public void removeBullet(Bullet b) {
        bullets.remove(b);
    }

    /*  */
    public LinkedList<Bullet> getBulletList() {
        return bullets;
    }

    public void addEnemyWithBullet(MovingEnemyWithBullet enemy) {
        bulletsEnemy.add(enemy);
    }

    public void removeEnemyWithBullet(MovingEnemyWithBullet enemy) {
        bulletsEnemy.remove(enemy);
    }

    public LinkedList<MovingEnemyWithBullet> getEnemyWithBulletList() {
        return bulletsEnemy;
    }
}
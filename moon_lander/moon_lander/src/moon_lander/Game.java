package moon_lander;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.util.LinkedList;

/**
 * Actual game.
 * 
 * @author www.gametutorial.net
 */

public class Game {

    /**
     * The space rocket with which player will have to land.
     */
    private PlayerRocket playerRocket;

    /**
     * Landing area on which rocket will have to land.
     */
    private LandingArea landingArea;

    /**
     * Game background image.
     */
    private BufferedImage backgroundImg;

    /**
     * Red border of the frame. It is used when player crash the rocket.
     */
    private BufferedImage redBorderImg;
    /* Enemy */
    private Unmoved_Enemy UnmoveEnemy;

    private EnemyController moving_Enemy;

    public Game() {
        Framework.gameState = Framework.GameState.GAME_CONTENT_LOADING;

        Thread threadForInitGame = new Thread() {
            @Override
            public void run() {
                // Sets variables and objects for the game.
                Initialize();
                // Load game files (images, sounds, ...)
                LoadContent();

                Framework.gameState = Framework.GameState.PLAYING;
            }
        };
        threadForInitGame.start();
    }

    /**
     * Set variables and objects for the game.
     */
    private void Initialize() {
        playerRocket = new PlayerRocket();
        landingArea = new LandingArea();
        UnmoveEnemy = new Unmoved_Enemy();
        moving_Enemy = new EnemyController();
    }

    /**
     * Load game files - images, sounds, ...
     */
    private void LoadContent() {
        try {
            URL backgroundImgUrl = this.getClass().getResource("/resources/images/background.jpg");
            backgroundImg = ImageIO.read(backgroundImgUrl);

            URL redBorderImgUrl = this.getClass().getResource("/resources/images/red_border.png");
            redBorderImg = ImageIO.read(redBorderImgUrl);
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Restart game - reset some variables.
     */
    public void RestartGame() {
        playerRocket.ResetPlayer();
        UnmoveEnemy.ResetUnmovedEnemy();
        moving_Enemy.ResetController();
    }

    /**
     * Update game logic.
     * 
     * @param gameTime      gameTime of the game.
     * @param mousePosition current mouse position.
     */
    public void UpdateGame(long gameTime, Point mousePosition) {
        // Move the rocket
        playerRocket.Update();
        moving_Enemy.Update();
        // Checks where the player rocket is. Is it still in the space or is it landed
        // or crashed?
        // First we check bottom y coordinate of the rocket if is it near the landing
        // area.
        if (playerRocket.y + playerRocket.rocketImgHeight - 10 > landingArea.y) {
            // Here we check if the rocket is over landing area.
            if ((playerRocket.x > landingArea.x) && (playerRocket.x < landingArea.x + landingArea.landingAreaImgWidth
                    - playerRocket.rocketImgWidth)) {
                // Here we check if the rocket speed isn't too high.
                if (playerRocket.speedY <= playerRocket.topLandingSpeed)
                    playerRocket.landed = true;
                else
                    playerRocket.crashed = true;
            } else
                playerRocket.crashed = true;

            Framework.gameState = Framework.GameState.GAMEOVER;
        }

        /* Enemy Collision */
        Rectangle rocket = playerRocket.makeRect();
        Rectangle enemy = UnmoveEnemy.drawRect();
        if (rocket.intersects(enemy)) {
            playerRocket.crashed = true;
            Framework.gameState = Framework.gameState.GAMEOVER;
        }
        /* Moving Enemy Collision */
        LinkedList<Moving_Enemy> e = moving_Enemy.getEnemyList();

        for (int i = 0; i < e.size(); i++) {
            if (rocket.intersects(e.get(i).drawRect())) {
                playerRocket.crashed = true;
                Framework.gameState = Framework.gameState.GAMEOVER;
                break;
            }
        }
    }

    /**
     * Draw the game to the screen.
     * 
     * @param g2d           Graphics2D
     * @param mousePosition current mouse position.
     */
    public void Draw(Graphics2D g2d, Point mousePosition) {
        g2d.drawImage(backgroundImg, 0, 0, Framework.frameWidth, Framework.frameHeight, null);

        landingArea.Draw(g2d);

        playerRocket.Draw(g2d);

        UnmoveEnemy.Draw(g2d);

        moving_Enemy.Draw(g2d);
    }

    /**
     * Draw the game over screen.
     * 
     * @param g2d           Graphics2D
     * @param mousePosition Current mouse position.
     * @param gameTime      Game time in nanoseconds.
     */
    public void DrawGameOver(Graphics2D g2d, Point mousePosition, long gameTime) {
        Draw(g2d, mousePosition);

        g2d.drawString("Press space or enter to restart.", Framework.frameWidth / 2 - 100,
                Framework.frameHeight / 3 + 70);

        if (playerRocket.landed) {
            g2d.drawString("You have successfully landed!", Framework.frameWidth / 2 - 100, Framework.frameHeight / 3);
            g2d.drawString("You have landed in " + gameTime / Framework.secInNanosec + " seconds.",
                    Framework.frameWidth / 2 - 100, Framework.frameHeight / 3 + 20);
        } else {
            g2d.setColor(Color.red);
            g2d.drawString("You have crashed the rocket!", Framework.frameWidth / 2 - 95, Framework.frameHeight / 3);
            g2d.drawImage(redBorderImg, 0, 0, Framework.frameWidth, Framework.frameHeight, null);
        }
    }
}
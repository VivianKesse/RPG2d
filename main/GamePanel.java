package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import background.BackManager;
import entity.Player;
import object.SuperObject;

public class GamePanel extends JPanel implements Runnable {

  // configurações da tela
  final int originalTileSize = 16; // 16x16
  final int scale = 3; // para aumentar o personagem em telas maiores

  public final int tileSize = originalTileSize * scale;
  public final int maxScreenCol = 16;
  public final int maxScreenRow = 12;
  public final int screenWidth = tileSize * maxScreenCol; // 768px
  public final int screenHeight = tileSize * maxScreenRow;// 576px

  public final int maxWorldCol = 50;
  public final int maxWorldRow = 50;
  public final int WorldWidth = tileSize * maxWorldCol;
  public final int WorldHeighg = tileSize * maxWorldRow;

  int FPS = 60;

  BackManager back = new BackManager(this);
  KeyHandler keyH = new KeyHandler();
  Thread gameThread;
  public CollisionChecker cChecker = new CollisionChecker(this);
  public AssetSetter aSetter = new AssetSetter(this);
  public Player player = new Player(this, keyH);
  public SuperObject obj[] = new SuperObject[10];

  public GamePanel() {
    this.setPreferredSize(new Dimension(screenWidth, screenHeight));
    this.setBackground(Color.black);
    this.setDoubleBuffered(true);
    this.addKeyListener(keyH);
    this.setFocusable(true);
  }

  public void setupGame() {
    aSetter.setObject();
  }

  public void startGameThread() {
    gameThread = new Thread(this);
    gameThread.start();
  }

  @Override
  public void run() {

    double drawInterval = 1000000000 / FPS;
    double delta = 0;
    double lastTime = System.nanoTime();
    long currentTime;
    long timer = 0;
    int drawCount = 0;

    while (gameThread != null) {
      // atualizar posição do personagem
      // desenhar a nova tela com a atualização
      currentTime = System.nanoTime();

      delta += (currentTime - lastTime) / drawInterval;

      lastTime = currentTime;

      if (delta >= 1) {
        update();
        repaint();
        delta--;
        drawCount++;
      }

      if (timer >= 1000000000) {
        drawCount = 0;
        timer = 0;
      }
    }
  }

  public void update() {

    player.update();
  }

  public void paintComponent(Graphics g) {

    super.paintComponent(g);

    Graphics2D g2 = (Graphics2D) g;

    back.draw(g2);
    player.draw(g2);
    for (int i = 0; i < obj.length; i++) {
      if (obj[i] != null) {
        obj[i].draw(g2, this);
      }
    }

    g2.dispose();
  }
}

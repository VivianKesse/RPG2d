package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
  GamePanel gp;
  KeyHandler keyH;

  public final int screenX;
  public final int screenY;
  public int countKey = 0;

  public Player(GamePanel gp, KeyHandler keyH) {
    this.gp = gp;
    this.keyH = keyH;

    screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
    screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

    solidArea = new Rectangle(8, 16, 32, 32);
    solidAreaDefaultX = solidArea.x;
    solidAreaDefaultY = solidArea.y;

    setDefaultValues();
    getPlayerImage();
  }

  public void setDefaultValues() {
    // posiçao inicial do jogador no mapa
    worldX = gp.tileSize * 23;
    worldY = gp.tileSize * 21;
    speed = 4;
    direction = "down";
  }

  public void getPlayerImage() {
    try {
      up1 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_up_1.png"));
      up2 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_up_2.png"));
      down1 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_down_1.png"));
      down2 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_down_2.png"));
      left1 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_left_1.png"));
      left2 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_left_2.png"));
      right1 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_right_1.png"));
      right2 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_right_2.png"));

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void update() {
    // para deixar o personagem parado quando nenhuma tecla estiver pressionada
    if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
      if (keyH.upPressed == true) {
        direction = "up";
      }

      else if (keyH.downPressed == true) {
        direction = "down";
      }

      else if (keyH.leftPressed == true) {
        direction = "left";

      }

      else if (keyH.rightPressed == true) {
        direction = "right";

      }

      // checando colisão
      collisionOn = false;
      gp.cChecker.checkBack(this);

      // checando colisão com o objeto
      int objIndex = gp.cChecker.checkObject(this, true);
      pickUpObject(objIndex);

      // se não há colisão, o player consegue se mover
      if (collisionOn == false) {
        switch (direction) {
          case "up":
            worldY -= speed;
            break;

          case "down":
            worldY += speed;
            break;

          case "left":
            worldX -= speed;
            break;

          case "right":
            worldX += speed;
            break;

        }
      }

      // incrementa spriteCounter 60 vezes por segundo, pois estamos no loop do jogo
      // os if's servem para dar o efeito de caminhada do personagem, a cada 12 frames
      // ele troca a imagem
      spriteCounter++;
      if (spriteCounter > 12) {
        if (spriteNum == 1) {
          spriteNum = 2;
        }

        else if (spriteNum == 2) {
          spriteNum = 1;
        }
        spriteCounter = 0;
      }
    }

  }

  // o objeto some quando o player "pega" ele
  public void pickUpObject(int i) {
    if (i != 999) {
      String objectName = gp.obj[i].name;

      switch (objectName) {
        case "Key":
          gp.playSE(1);
          countKey++;
          gp.obj[i] = null;
          gp.ui.showMessage("Você pegou uma chave!");
          break;

        case "Door":
          gp.playSE(3);

          if (countKey > 0) {
            gp.obj[i] = null;
            countKey--;
            gp.ui.showMessage("Você abriu a porta!");
          } else {
            gp.ui.showMessage("Você precisa de uma chave!");

          }
          break;

        case "Boots":
          gp.playSE(2);

          speed += 1;
          gp.obj[i] = null;
          gp.ui.showMessage("Acelere!");
          break;
        case "Chest":
          gp.ui.gameFinished = true;
          gp.stopMusic();
          gp.playSE(4);
          break;

      }
    }
  }

  public void draw(Graphics2D g2) {

    BufferedImage image = null;

    switch (direction) {
      case "up":
        if (spriteNum == 1) {
          image = up1;

        }
        if (spriteNum == 2) {
          image = up2;
        }
        break;

      case "down":
        if (spriteNum == 1) {
          image = down1;

        }
        if (spriteNum == 2) {
          image = down2;
        }
        break;

      case "left":
        if (spriteNum == 1) {
          image = left1;

        }
        if (spriteNum == 2) {
          image = left2;
        }
        break;

      case "right":
        if (spriteNum == 1) {
          image = right1;

        }
        if (spriteNum == 2) {
          image = right2;
        }
        break;

    }

    g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
  }

}

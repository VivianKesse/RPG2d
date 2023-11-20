package background;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class BackManager {
  GamePanel gp;
  public Background[] background;
  public int mapBackNum[][];

  public BackManager(GamePanel gp) {
    this.gp = gp;
    background = new Background[10];
    mapBackNum = new int[gp.maxWorldCol][gp.maxWorldRow];
    getBackImage();
    loadMap("/res/maps/world01.txt");
  }

  public void getBackImage() {
    try {

      background[0] = new Background();
      background[0].image = ImageIO.read(getClass().getResourceAsStream("/res/background/grass.png"));

      background[1] = new Background();
      background[1].image = ImageIO.read(getClass().getResourceAsStream("/res/background/wall.png"));
      background[1].collision = true;

      background[2] = new Background();
      background[2].image = ImageIO.read(getClass().getResourceAsStream("/res/background/water.png"));
      background[2].collision = true;

      background[3] = new Background();
      background[3].image = ImageIO.read(getClass().getResourceAsStream("/res/background/earth.png"));

      background[4] = new Background();
      background[4].image = ImageIO.read(getClass().getResourceAsStream("/res/background/tree.png"));
      background[4].collision = true;

      background[5] = new Background();
      background[5].image = ImageIO.read(getClass().getResourceAsStream("/res/background/sand.png"));

    } catch (IOException e) {
      // Imprime a exceção
      e.printStackTrace();
    }
  }

  public void loadMap(String filePath) {
    try {
      // "pegar" o arquivo
      InputStream is = getClass().getResourceAsStream(filePath);
      // ler o que está escrito no arquivo
      BufferedReader br = new BufferedReader(new InputStreamReader(is));

      int col = 0;
      int row = 0;
      while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

        String line = br.readLine();
        while (col < gp.maxWorldCol) {
          // criando um vetor com os numeros no arquivo(que representam quadros do mapa)
          String numbers[] = line.split(" ");
          // transformando em inteiros pra usar no vetor que representa o mapa
          int num = Integer.parseInt(numbers[col]);
          mapBackNum[col][row] = num;
          col++;

        }
        if (col == gp.maxWorldCol) {
          col = 0;
          row++;
        }
      }
      br.close();
    } catch (Exception e) {

    }
  }

  public void draw(Graphics2D g2) {
    int worldCol = 0;
    int worldRow = 0;

    while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
      int backNum = mapBackNum[worldCol][worldRow];

      int worldX = worldCol * gp.tileSize;
      int worldY = worldRow * gp.tileSize;
      int screenX = worldX - gp.player.worldX + gp.player.screenX;
      int screenY = worldY - gp.player.worldY + gp.player.screenY;

      if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
          && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
          && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
          && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

        g2.drawImage(background[backNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);

      }
      worldCol++;

      if (worldCol == gp.maxWorldCol) {
        worldCol = 0;
        worldRow++;
      }

    }
  }
}

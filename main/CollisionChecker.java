package main;

import entity.Entity;

public class CollisionChecker {
  GamePanel gp;

  public CollisionChecker(GamePanel gp) {
    this.gp = gp;
  }

  public void checkBack(Entity entity) {
    int entityLeftWorldX = entity.worldX + entity.solidArea.x;
    int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
    int entityTopWorldY = entity.worldY + entity.solidArea.y;
    int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

    int entityLeftCol = entityLeftWorldX / gp.tileSize;
    int entityRightCol = entityRightWorldX / gp.tileSize;
    int entityTopRow = entityTopWorldY / gp.tileSize;
    int entityBottomRow = entityBottomWorldY / gp.tileSize;

    int backNum1, backNum2;

    switch ((entity.direction)) {
      case "up":
        entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
        backNum1 = gp.back.mapBackNum[entityLeftCol][entityTopRow];
        backNum2 = gp.back.mapBackNum[entityRightCol][entityTopRow];

        if (gp.back.background[backNum1].collision == true || gp.back.background[backNum2].collision == true) {
          entity.collisionOn = true;
        }
        break;
      case "down":
        entityBottomRow = (entityBottomWorldY - entity.speed) / gp.tileSize;
        backNum1 = gp.back.mapBackNum[entityLeftCol][entityBottomRow];
        backNum2 = gp.back.mapBackNum[entityRightCol][entityBottomRow];

        if (gp.back.background[backNum1].collision == true || gp.back.background[backNum2].collision == true) {
          entity.collisionOn = true;
        }
        break;

      case "left":
        entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
        backNum1 = gp.back.mapBackNum[entityLeftCol][entityTopRow];
        backNum2 = gp.back.mapBackNum[entityLeftCol][entityBottomRow];

        if (gp.back.background[backNum1].collision == true || gp.back.background[backNum2].collision == true) {
          entity.collisionOn = true;
        }
        break;

      case "right":
        entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
        backNum1 = gp.back.mapBackNum[entityRightCol][entityTopRow];
        backNum2 = gp.back.mapBackNum[entityRightCol][entityBottomRow];

        if (gp.back.background[backNum1].collision == true || gp.back.background[backNum2].collision == true) {
          entity.collisionOn = true;
        }
        break;
    }

  }
}

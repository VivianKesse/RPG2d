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

  public int checkObject(Entity entity, boolean player) {
    int index = 999;
    // checar se o player encontrou o objeto e retornar um index
    for (int i = 0; i < gp.obj.length; i++) {
      if (gp.obj[i] != null) {
        // pegar posição do personagem
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;
        // pegar posição do objeto
        gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
        gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

        switch (entity.direction) {
          case "up":
            entity.solidArea.y -= entity.speed;
            if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
              if (gp.obj[i].collision == true) {
                entity.collisionOn = true;
              }

              if (player == true) {
                index = i;
              }
            }
            break;

          case "down":
            entity.solidArea.y += entity.speed;
            if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
              if (gp.obj[i].collision == true) {
                entity.collisionOn = true;
              }

              if (player == true) {
                index = i;
              }
            }
            break;

          case "left":
            entity.solidArea.x -= entity.speed;
            if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
              if (gp.obj[i].collision == true) {
                entity.collisionOn = true;
              }

              if (player == true) {
                index = i;
              }
            }
            break;

          case "right":
            entity.solidArea.x += entity.speed;
            if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
              if (gp.obj[i].collision == true) {
                entity.collisionOn = true;
              }

              if (player == true) {
                index = i;
              }
            }
            break;
        }
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
        gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
      }
    }
    return index;
  }
}

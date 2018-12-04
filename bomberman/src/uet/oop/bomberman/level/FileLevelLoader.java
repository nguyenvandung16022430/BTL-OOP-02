
package uet.oop.bomberman.level;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Balloon;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.entities.tile.item.SpeedItem;
import uet.oop.bomberman.exceptions.LoadLevelException;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

import java.io.*;
import java.net.URL;
import uet.oop.bomberman.entities.character.enemy.Doll;
import uet.oop.bomberman.entities.character.enemy.Doria;
import uet.oop.bomberman.entities.character.enemy.Minvo;
import uet.oop.bomberman.entities.character.enemy.Oneal;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.item.BombItem;
import uet.oop.bomberman.entities.tile.item.FlameItem;


public class FileLevelLoader extends LevelLoader {

    /**
     * Ma trận chứa thông tin bản đồ, mỗi phần tử lưu giá trị kí tự đọc được
     * từ ma trận bản đồ trong tệp cấu hình
     */
    private static char[][] _map;

    public FileLevelLoader(Board board, int level) throws LoadLevelException {
        super(board, level);
    }

    @Override
    public void loadLevel(int level)throws LoadLevelException {
        // TODO: đọc dữ liệu từ tệp cấu hình /levels/Level{level}.txt
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource("./levels/Level"+level+".txt");
        String line = "";
        try{
            FileReader fileReader =
                    new FileReader(url.getPath());

            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            line = bufferedReader.readLine();

            int curLevel = Integer.parseInt(line.split(" ")[0]);
            int height = Integer.parseInt(line.split(" ")[1]);
            int width = Integer.parseInt(line.split(" ")[2]);

            char map[][] = new char[height][width];

            for (int i = 0; i < height; i++) {
                line = bufferedReader.readLine();
                for (int j = 0; j < width; j++) {
                    map[i][j] = line.charAt(j);
                    System.out.print(map[i][j]);
                }
                System.out.println("");
            }

            _map = map;
            _width = width;
            _height = height;
            _level = curLevel;

            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public void createEntities() {
        System.out.println(getWidth());
        System.out.println(getHeight());

        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                int pos = x + y * getWidth();
               Sprite sprite = Sprite.grass;
                switch (_map[y][x]) {
                    case '#':
                        sprite = Sprite.wall;
                        _board.addEntity(pos, new Wall(x, y, sprite));
                        break;
                    case '*':
                        int xB = x, yB = y;
                        _board.addEntity(xB + yB * _width,
                                new LayeredEntity(xB, yB,
                                        new Grass(xB, yB, Sprite.grass),
                                        new Brick(xB, yB, Sprite.brick)
                                )
                        );
                        break;
                    case 'x':
                         int xP = x, yP = y;
                        sprite = Sprite.portal;
                         _board.addEntity(xP + yP * _width,
                                new LayeredEntity(xP, yP,
                                        new Grass(xP, yP, Sprite.grass),
                                        new Portal(xP,yP, Sprite.portal,_board),
                                        new Brick(xP, yP, Sprite.brick)
                                )
                        );
                        break;
                    case 'p':
                        int xBomber = x, yBomber = y;
                        _board.addCharacter(new Bomber(Coordinates.tileToPixel(xBomber), Coordinates.tileToPixel(yBomber) + Game.TILES_SIZE, _board));
                        Screen.setOffset(0, 0);
                        _board.addEntity(xBomber + yBomber * _width, new Grass(xBomber, yBomber, Sprite.grass));
                        break;
                    case '1':
                        int xE = x, yE = y;
                        _board.addCharacter(new Balloon(Coordinates.tileToPixel(xE), Coordinates.tileToPixel(yE) + Game.TILES_SIZE, _board));
                        _board.addEntity(xE + yE * _width, new Grass(xE, yE, Sprite.grass));

                        break;
                    case '2':
                        sprite = Sprite.oneal_left1;
                        int xO= x,yO=y;
                        _board.addCharacter(new Oneal(Coordinates.tileToPixel(xO), Coordinates.tileToPixel(yO) + Game.TILES_SIZE, _board));
                        _board.addEntity(xO + yO * _width, new Grass(xO, yO, Sprite.grass));

                        break;
                    case 'k':
                        sprite = Sprite.kondoria_left1;
                        int xk = x,yk=y;
                        _board.addCharacter(new Doria(Coordinates.tileToPixel(xk), Coordinates.tileToPixel(yk) + Game.TILES_SIZE, _board));
                        _board.addEntity(xk + yk * _width, new Grass(xk, yk, Sprite.grass));
                        break;
                    case 'm':
                          sprite = Sprite.minvo_left1;
                        int xm = x,ym=y;
                        _board.addCharacter(new Minvo(Coordinates.tileToPixel(xm), Coordinates.tileToPixel(ym) + Game.TILES_SIZE, _board));
                        _board.addEntity(xm + ym * _width, new Grass(xm, ym, Sprite.grass));
                        break;
                    case'd':
                         sprite = Sprite.doll_left1;
                        int xd = x,yd=y;
                        _board.addCharacter(new Doll(Coordinates.tileToPixel(xd), Coordinates.tileToPixel(yd) + Game.TILES_SIZE, _board));
                        _board.addEntity(xd + yd * _width, new Grass(xd, yd, Sprite.grass));
                        break;
                    case 'f':
                        int xI = x,yI=y;
                      _board.addEntity(xI + yI * _width,
                        new LayeredEntity(xI, yI,
                        new Grass(xI, yI, Sprite.grass),
                        new FlameItem(xI, yI, Sprite.powerup_flames),
                        new Brick(xI, yI, Sprite.brick)  ));
                        break;
                    case 'b':
                          int xb = x,yb=y;
                      _board.addEntity(xb + yb * _width,
                        new LayeredEntity(xb, yb,
                        new Grass(xb, yb, Sprite.grass),
                        new BombItem(xb, yb, Sprite.powerup_bombs),
                        new Brick(xb, yb, Sprite.brick)  ));
                        break;
                    case 's':
                          int xS = x,yS=y;
                      _board.addEntity(xS + yS * _width,
                        new LayeredEntity(xS, yS,
                        new Grass(xS, yS, Sprite.grass),
                        new SpeedItem(xS, yS, Sprite.powerup_speed),
                        new Brick(xS, yS, Sprite.brick)  ));
                        break;
                    default:
                        _board.addEntity(pos, new Grass(x, y, sprite));
                        break;
                }
            }
        }
 
    }

}

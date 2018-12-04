/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.character.enemy.ai.AIMedium;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;

/**
 *
 * @author nguyen
 */
public class Doria extends Enemy {
    public int Bompass = 0;

    public Doria(int x, int y, Board board) {
        super(x, y, board,Sprite.kondoria_dead,Game.getBomberSpeed()/4,1000);
        _sprite = Sprite.kondoria_left1;	
	_ai = new AIMedium(_board.getBomber(), this); //TODO: implement AIHigh 
	_direction  = _ai.calculateDirection();
	}

    @Override
    protected void chooseSprite() {
       switch(_direction) {
			case 0:
			case 1:
				if(_moving)
					_sprite = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2, Sprite.kondoria_right3, _animate, 60);
				else
					_sprite = Sprite.kondoria_left1;
				break;
			case 2:
			case 3:
				if(_moving)
					_sprite = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2, Sprite.kondoria_left3, _animate, 60);
				else
					_sprite = Sprite.kondoria_left1;
				break;
		}  
    }
 @Override
	public void calculateMove() {
		int xa = 0, ya = 0;
                int xa1= 0,ya1 =0;
		if(_steps <= 0){
			_direction = _ai.calculateDirection();
			_steps = MAX_STEPS;
		}
			
		if(_direction == 0){
                    ya--;
                    ya1-=2;
                }
		if(_direction == 2) {
                    ya++;
                    ya1 +=2;
                }
		if(_direction == 3) {
                    xa--;
                    xa1-=2;
                }
		if(_direction == 1){
                    xa++;
                    xa1+=2;
                }
		
		if(canMove(xa, ya)) {
                    if(!Bompass(xa1,ya1)){
                      _steps = 0;
			_moving = false;  
                    }
                    else{
			_steps -= 1 + rest;
			move(xa * _speed, ya * _speed);
			_moving = true;
                    }
		} else {
                        _steps = 0;
			_moving = false;
	}}
	
	@Override
	public void move(double xa, double ya) {
		if(!_alive) return;
		_y += ya;
		_x += xa;
	}
	
	@Override
	public boolean canMove(double x, double y) {
		double xr = _x, yr = _y - 16; //subtract y to get more accurate results
		
		//the thing is, subract 15 to 16 (sprite size), so if we add 1 tile we get the next pixel tile with this
		//we avoid the shaking inside tiles with the help of steps
		if(_direction == 0) { yr += _sprite.getSize() -1 ; xr += _sprite.getSize()/2; } 
		if(_direction == 1) {yr += _sprite.getSize()/2; xr += 1;}
		if(_direction == 2) { xr += _sprite.getSize()/2; yr += 1;}
		if(_direction == 3) { xr += _sprite.getSize() -1; yr += _sprite.getSize()/2;}
		
		int xx = Coordinates.pixelToTile(xr) +(int)x;
		int yy = Coordinates.pixelToTile(yr) +(int)y;
		
		Entity a = _board.getEntity(xx, yy, this); //entity of the position we want to go
		
		return a.collide(this);
	}
        public boolean Bompass(double x,double y){
            double xr = _x, yr = _y - 16; //subtract y to get more accurate results
		
		//the thing is, subract 15 to 16 (sprite size), so if we add 1 tile we get the next pixel tile with this
		//we avoid the shaking inside tiles with the help of steps
		if(_direction == 0) { yr += _sprite.getSize() -1 ; xr += _sprite.getSize()/2; } 
		if(_direction == 1) {yr += _sprite.getSize()/2; xr += 1;}
		if(_direction == 2) { xr += _sprite.getSize()/2; yr += 1;}
		if(_direction == 3) { xr += _sprite.getSize() -1; yr += _sprite.getSize()/2;}
		
		int xx = Coordinates.pixelToTile(xr) +(int)x;
		int yy = Coordinates.pixelToTile(yr) +(int)y;
		Entity a = _board.getEntity(xx, yy, this);
                if( a instanceof Bomb){
                    return false;
                }
                return true;
        }

    }

  


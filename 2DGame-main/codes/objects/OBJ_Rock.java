package objects;

import characters.Characters;
import characters.Projectile;
import main.GameScreen;

public class OBJ_Rock extends Projectile{
	
	GameScreen gs;

	public OBJ_Rock(GameScreen gs) {
		super(gs);
		this.gs=gs;
		
		name= "Rock";
		speed=8;
		maxLife=80;
		life=maxLife;
		attack=2;
		
		useCost = 1;
		alive=false;
		getImage();
	}
	
	public void getImage() {
		up1 = setup("/projectile/rock_down_1",gs.sizeRect,gs.sizeRect);
		up2 = setup("/projectile/rock_down_1",gs.sizeRect,gs.sizeRect);
		down1 = setup("/projectile/rock_down_1",gs.sizeRect,gs.sizeRect);
		down2 = setup("/projectile/rock_down_1",gs.sizeRect,gs.sizeRect);
		left1 = setup("/projectile/rock_down_1",gs.sizeRect,gs.sizeRect);
		left2 =  setup("/projectile/rock_down_1",gs.sizeRect,gs.sizeRect);
		right1 = setup("/projectile/rock_down_1",gs.sizeRect,gs.sizeRect);
		right2 = setup("/projectile/rock_down_1",gs.sizeRect,gs.sizeRect);
	}
	
	public boolean haveResource(Characters user) {
		boolean haveResource = false;
		if(user.ammo >= useCost) {
			haveResource = true;
		}
		return haveResource;
	}
	
	public void subtractResource(Characters user) {
		user.ammo -= useCost;
	}
}

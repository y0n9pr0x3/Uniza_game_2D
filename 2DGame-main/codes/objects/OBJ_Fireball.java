package objects;

import characters.Characters;
import characters.Projectile;
import main.GameScreen;

public class OBJ_Fireball extends Projectile{
	
	GameScreen gs;

	public OBJ_Fireball(GameScreen gs) {
		super(gs);
		this.gs=gs;
		
		name= "Ohnivo guľo!";
		speed=8;
		maxLife=80;
		life=maxLife;
		attack=2;
		
		knockBackPower = 0;
		
		useCost = 1;
		alive=false;
		getImage();
	}
	
	public void getImage() {
		up1 = setup("/projectile/fireball_up_1",gs.sizeRect,gs.sizeRect);
		up2 = setup("/projectile/fireball_up_2",gs.sizeRect,gs.sizeRect);
		down1 = setup("/projectile/fireball_down_1",gs.sizeRect,gs.sizeRect);
		down2 = setup("/projectile/fireball_down_2",gs.sizeRect,gs.sizeRect);
		left1 = setup("/projectile/fireball_left_1",gs.sizeRect,gs.sizeRect);
		left2 =  setup("/projectile/fireball_left_2",gs.sizeRect,gs.sizeRect);
		right1 = setup("/projectile/fireball_right_1",gs.sizeRect,gs.sizeRect);
		right2 = setup("/projectile/fireball_right_2",gs.sizeRect,gs.sizeRect);
	}
	
	public boolean haveResource(Characters user) {
		boolean haveResource = false;
		if(user.mana >= useCost) {
			haveResource = true;
		}
		return haveResource;
	}
	
	public void subtractResource(Characters user) {
		user.mana -= useCost;
	}
}

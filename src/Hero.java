
public class Hero implements java.io.Serializable {
	private int hp;
	private Item curWeapon;
	private int curLocation;
	
	public Hero (int hp, Item item) {
		this.hp = hp;
		curWeapon = item;
		curLocation = 0;
	}
	
	public void regeneration() {
		hp = 100;
	}
	
	public String getWeapon() {
		return curWeapon.getDescription();
	}
	
	public void damaged (int dmg) {
		hp = hp - dmg;
	}
	
	public int getHealth() {
		return hp;
	}
	
	public int getCurLocation() {
		return curLocation;
	}
	
	public void setCurLocation(int curLoc) {
		curLocation = curLoc;
	}
	
	public int getDamage () {
		return curWeapon.getDamage();
	}
	
	public void takeItem (Item item) {
		this.curWeapon = item;
	}
}
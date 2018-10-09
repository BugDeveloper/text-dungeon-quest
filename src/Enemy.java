
public class Enemy implements java.io.Serializable {
	private String description;
	private int hp;
	private int dmg;
	
	public Enemy (String description, int hp, int damage) {
		this.description = description;
		this.hp = hp;
		this.dmg = damage;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void damaged (int dmg) {
		hp = hp - dmg;
	}
	
	public int getHealth() {
		return hp;
	}

	public int getDamage () {
		return dmg;
	}
	public String getEnemyDescription() {
		return description;
	}
}

public class Room implements java.io.Serializable {
	
	private String description;
	private Enemy monster;
	private Item item;
	private int ways[] = new int[4];
	
	public Room (String description, Enemy monster, Item item, int back, int left, int forward, int right) {
		this.description = description;
		this.monster = monster;
		this.item = item;
		ways[0] = back;
		ways[1] = left;
		ways[2] = forward;
		ways[3] = right;
	}
	
	public void itemTaken () {
		item = null;
	}
	public Item getItem () {
		return item;
	}
	public boolean checkItem () {
		if (item == null)
			return false;
		else 
			return true;
	}
	
	public Enemy getEnemy() {
		return monster;
	}
	
	public void monsterDies () {
		monster = null;
	}
	public boolean checkMonster() {
		if (monster == null)
			return false;
		else return true;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getEnemyDescription() {
		return monster.getDescription();
	}
	
	public int getBack() {
		return ways[0];
	}
	
	public int getLeft() {
		return ways[1];
	}
	
	public int getForward() {
		return ways[2];
	}
	
	public int getRight() {
		return ways[3];
	}
	
}
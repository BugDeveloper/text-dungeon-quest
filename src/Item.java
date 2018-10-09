
public class Item implements java.io.Serializable {
	private String description;
	private int dmg;

	public Item (String description, int dmg) {
		this.description = description;
		this.dmg = dmg;
	}
	public String getDescription() {
		return description;
	}
	
	public int getDamage () {
		return dmg;
	}
	
}
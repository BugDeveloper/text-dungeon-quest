import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Game implements Serializable {
	
	private Room[] world = new Room[10];
	private Hero hero;
			
	public Game () {
		Item stick = new Item("simple wooden stick", 10);
		Item laserChainsaw = new Item ("laser chainsaw", 100);
		Item poop = new Item ("just poop", 0);
		Item titanSword = new Item ("sword of titans", 20);
		Item fireAxe = new Item ("fire axe", 30);
		Item bloodyHammer = new Item ("bloody hammer", 40);
		hero = new Hero(100, stick);
		Enemy darkGolem = new Enemy("dark golem of the fallen damnation world", 75, 25);
		Enemy bloodySomething = new Enemy("bloody something", 50, 15);
		Enemy centaur = new Enemy("huge centaur", 100, 50);
		Enemy piggie = new Enemy("little piggie", 20, 1);
		
		world[0] = new Room("You woke up on the floor in the cold basement. Torches on the walls illuminate it. "
				+ "\nThe whole body hurts and you don't remember anything."
				+ "You can \"goaway\" from this room, check your current \"weapon\" and \"save\" your progress.", null, null, 0, 0, 2, 0);
		world[1] = new Room ("\"All room is in blood. What happened here?..\"", null, titanSword, 0, 0, 4, 2);
		world[2] = new Room ("\"Scary sounds everywhere. I don't know what to do..\"", piggie, null, 0, 1, 5, 3);
		world[3] = new Room ("\"Someday I hope I'll go home..\"", bloodySomething, poop, 0, 2, 6, 0);
		world[4] = new Room ("\"I need to sleep..\"", bloodySomething, stick, 1, 0, 7, 5);
		world[5] = new Room ("\"I'm so hungry..\"", null, laserChainsaw, 2, 4, 8, 6);
		world[6] = new Room ("\"Where am I? Why?..\"", centaur, fireAxe, 3, 5, 9, 0);
		world[7] = new Room ("\"So cold..\"", darkGolem, bloodyHammer, 4, 0, 0, 8);
		world[8] = new Room ("\"Something shines from the right side..\"", null, titanSword, 5, 7, 0, 9);
	}
	
	public boolean play() {
		while (true) {
			if (hero.getCurLocation() == 9) return true;
			
			if(!describe()) return false;
			doSomething();
		}
	}
	
	private void doSomething() {
		
		String action = Main.in.next();
		
		switch(action.toLowerCase()) {
			case ("goaway"):
				goAway();
				break;
			case ("take"):
				if (world[hero.getCurLocation()].checkItem()) {
					System.out.print("You dropped away " + hero.getWeapon());
					hero.takeItem(world[hero.getCurLocation()].getItem());
					world[hero.getCurLocation()].itemTaken();
					System.out.println(" and took " + hero.getWeapon() + ".");
					doSomething();
					break;
				} else {
					System.out.println("No items in this room.");
					doSomething();
					break;
				}
			case ("weapon"):
				System.out.println("You current weapon is " + hero.getWeapon() + " which deals " + hero.getDamage() + " dmg.");
				doSomething();
				break;
			case ("save"):
				save();
				System.out.println("Game saved.");
				doSomething();
				break;
			default:
				System.out.println("You can't do this.");
				doSomething();
				break;
		}
	}
	
	private boolean goAway() {
		System.out.println("Which way?");
		int curLoc = hero.getCurLocation();
		if (world[curLoc].getLeft() != 0)
			System.out.print("You can go \"left\".");
		if (world[curLoc].getForward() != 0)
			System.out.print(" You can go \"forward\".");
		if (world[curLoc].getRight() != 0)
			System.out.print(" You can go \"right\".");
		if (world[curLoc].getBack() != 0)
			System.out.print(" You can go \"back\".");
		System.out.println();
		String go = Main.in.next();
		switch(go.toLowerCase()) {
			case "left":
				if (world[curLoc].getLeft() != 0) {
					hero.setCurLocation(world[curLoc].getLeft());
					return true;
				} else {
					noWay();
					return goAway();
				}
			case "right":
				if (world[curLoc].getRight() != 0) {
					hero.setCurLocation(world[curLoc].getRight());
					return true;
				} else {
					noWay();
					return goAway();
				}
			case "forward":
				if (world[curLoc].getForward() != 0) {
					hero.setCurLocation(world[curLoc].getForward());
					return true;
				} else {
					noWay();
					return goAway();
				}
			case "back":
				if (world[curLoc].getBack() != 0) {
					hero.setCurLocation(world[curLoc].getBack());
					return true;
				} else {
					noWay();
					return goAway();
				}
			default:
					System.out.println("Choose way already.");
				return goAway();
		}
	}
	
	private void noWay() {
		System.out.println("You can't go there.");
	}
	
	private boolean fight(Enemy monster) {
		boolean turn = true;
		System.out.println("Fight begins..");
		System.out.println("You can \"hit\" the monster or \"cry\".");
		char firLet = monster.getDescription().toUpperCase().charAt(0);
		String monsterName = firLet + monster.getDescription().substring(1, monster.getDescription().length());
		
		while (hero.getHealth() > 0 && monster.getHealth() > 0) {
			System.out.println(monsterName + ": " + monster.getHealth() + " hp.\nYou: " + hero.getHealth() + " hp.");
			if (turn)
			switch (Main.in.next()) {
				case "hit":
					System.out.println("You dealt " + hero.getDamage() + " dmg to " + monster.getDescription() + ".");
					monster.damaged(hero.getDamage());
					turn = !turn;
					break;
				case "cry":
					System.out.println("\"Pls *gasp* dont *gasp* hurt me!\"");
					turn = !turn;
					break;
			} else {
				System.out.println(monsterName + " dealt " + monster.getDamage() + " dmg to you.");
				hero.damaged(monster.getDamage());
				turn = !turn;
			}
		}
		if (hero.getHealth() <= 0)
			return false;
		else if (monster.getHealth() <= 0) {
			world[hero.getCurLocation()].monsterDies();
			System.out.println(monsterName + " dies.");
			hero.regeneration();
			return true;
		}
		else
			return false;
	}
	
	private void save() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("save.ser"));
			out.writeObject(this);
			out.close();
			} catch (IOException e) {
			}
	}
	private boolean describe() {
		if (world[hero.getCurLocation()].checkMonster()) {
			System.out.println("You go out on a " + world[hero.getCurLocation()].getEnemyDescription() + ".");
			if (!fight(world[hero.getCurLocation()].getEnemy()))
				return false;
		}
		
		System.out.println(world[hero.getCurLocation()].getDescription());
		if (world[hero.getCurLocation()].checkItem())
			System.out.println("You noticed " + world[hero.getCurLocation()].getItem().getDescription() + " on the floor. Which"
					+ " deals " + world[hero.getCurLocation()].getItem().getDamage() + " dmg. You can \"take\" it.");
		return true;
	}
}
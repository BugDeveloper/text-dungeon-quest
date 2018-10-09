import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;

public class Main {
	static Game game;
	static Scanner in = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		menu();
		
		if(game.play())
			System.out.println("Tadaaaaam!");
		else {
			System.out.println("You are dead..");
		}
	}
	
	private static void menu () {
		
		System.out.println("Begin \"new\" game or \"load\" save?");
		
		switch(in.next()) {
			case("new"):
				game = new Game();
				break;
			case("load"):
				load();
				break;
			default:
				System.out.println("Show some manners!");
				menu();
				break;
		}
	}
	private static void load() {
	      try
	      {
	         FileInputStream fileIn = new FileInputStream("save.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         game = (Game) in.readObject();
	         in.close();
	         fileIn.close();
	         
	      }catch(IOException i)
	      {
	         i.printStackTrace();
	         return;
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("Class not found");
	         c.printStackTrace();
	         return;
	      }
	}

}
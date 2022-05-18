import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.*;
import java.lang.Class;
import java.lang.reflect.*;

public class KlaskGame
{
	public static Player player1 = new Player(1325, 444, 1320, 391, 33, 8 , 38, "BLACK", 4);
	public static Player player2 = new Player(257, 444, 253, 391, 33, 8 , 38, "BLACK", 4);
	public static GameArena arena = new GameArena(1600, 900, true);
	
	public static void main(String[] args) 
	{
	   gameStart();
	}
	
	public static void gameStart()
	{
		
		// Arena Creation
		Rectangle rec = new Rectangle(28, 43, 1544, 826, "LIGHTGREY", 1);
		Rectangle rec2 = new Rectangle(52, 63, 1499, 783, "BLUE", 2);
		Line line1 = new Line(130, 57, 131, 147, 5, "LIGHTGREY", 3);
		Line line2 = new Line(47, 147, 131, 147, 5, "LIGHTGREY", 3);
		Line line3 = new Line(130, 847, 131, 756, 5, "LIGHTGREY", 3);
		Line line4 = new Line(47, 756, 131, 756, 5, "LIGHTGREY", 3);
		Line line5 = new Line(1468, 55, 1468, 147, 5, "LIGHTGREY", 3);
		Line line6 = new Line(1555, 147, 1468, 147, 5, "LIGHTGREY", 3);
		Line line7 = new Line(1555, 756, 1468, 756, 5, "LIGHTGREY", 3);
		Line line8 = new Line(1468, 847, 1468, 756, 5, "LIGHTGREY", 3);
		Line line9 = new Line(795, 57, 795, 847, 1, "LIGHTGREY", 3);
		Goal goal1 = new Goal(122, 451, 80, "LIGHTGREY", 3);
		Goal goal2 = new Goal(1471, 451, 80, "LIGHTGREY", 3);
		
		// Moveable Objects
		Ball b = new Ball(600, 500, 20, "YELLOW", 4);
		Magnets magnet1 = new Magnets(795, 451, 15, "WHITE", 4);
		Magnets magnet2 = new Magnets(795, 219, 15, "WHITE", 4);
		Magnets magnet3 = new Magnets(795, 684, 15, "WHITE", 4);
		Player player1 = new Player(1325, 444, 1320, 391, 33, 8 , 38, "BLACK", 4);
		Player player2 = new Player(257, 444, 253, 391, 33, 8 , 38, "BLACK", 4);
		
		// Adding to the arena
		arena.addRectangle(rec);
		arena.addRectangle(rec2);
		arena.addGoal(goal1);
		arena.addGoal(goal2);
		arena.addLine(line1);
		arena.addLine(line2);
		arena.addLine(line3);
		arena.addLine(line4);
		arena.addLine(line5);
		arena.addLine(line6);
		arena.addLine(line7);
		arena.addLine(line8);
		arena.addLine(line9);
		
		arena.addMagnet(magnet1);
		arena.addMagnet(magnet2);
		arena.addMagnet(magnet3);
		
		arena.addBall(b);
		arena.addPlayer(player1);
		arena.addPlayer(player2);
	}
}

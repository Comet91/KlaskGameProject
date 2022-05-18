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
	
	public static void main(String[] args) 
	{
	   gameStart();
	}
	
	public static void gameStart()
	{
		GameArena arena = new GameArena(1600, 900, true);
		
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
		
		Ball b = new Ball(600, 500, 10, "BLUE", 4);
		arena.addBall(b);
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
	}

}

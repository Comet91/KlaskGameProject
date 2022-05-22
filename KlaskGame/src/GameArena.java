import javax.swing.*;
import javax.swing.Timer;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.*;
import java.lang.Class;
import java.lang.reflect.*;

/**
 * This class provides a simple window in which grahical objects can be drawn.
 * @author Joe Finney
 */
public class GameArena extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener, ActionListener
{
	
	// Size of playarea
	private JFrame frame;
	private int arenaWidth;
	private int arenaHeight;

	private boolean exiting = false;

	private ArrayList<Object> things = new ArrayList<Object>();

	private HashMap<String, Color> colours = new HashMap<>();

	private boolean up = false;
	private boolean down = false;
	private boolean left = false;
	private boolean right = false;
	private boolean shift = false;
	private boolean space = false;
	private boolean esc = false;
	private boolean enter = false;
	private boolean[] alphasPressed = new boolean[26];
	private final static int asciiAZOffset = 65;
	private final static int alphabetLength = 25;
	private boolean leftMouse = false;
	private boolean rightMouse = false;
	private int mouseX = 0;
	private int mouseY = 0;

	private BufferedImage buffer;
	private Graphics2D graphics;
	private Map<RenderingHints.Key, Object> renderingHints;
	private boolean rendered = false;
	private Image backgroundImage = null;
	private Player player1 = KlaskGame.player1;
	private Player player2 = KlaskGame.player2;
	private Ball b;
	private Magnets m1;
	private Magnets m2;
	private Magnets m3;
	private Goal g;
	private Goal g2;
	public static double[] reb1;
	public static double[] reb2;
	public static double[] rebM1;
	public static double[] rebM2;
	public static double[] rebM3;
	public static double[] frictionMethod;
	private boolean hitWall = false;
	//private boolean hitWallM1 = false;
	//private boolean hitWallM2 = false;
	//private boolean hitWallM3 = false;
	private boolean hitPlayer1 = false;
	private boolean hitPlayer2 = false;
	private boolean hitBottomOrTopWall = false;
	private boolean hitRightOrLeftWall = false;
	//private boolean hitM1BottomOrTopWall = false;
	//private boolean hitM1RightOrLeftWall = false;
	//private boolean hitM2BottomOrTopWall = false;
	//private boolean hitM2RightOrLeftWall = false;
	//private boolean hitM3BottomOrTopWall = false;
	//private boolean hitM3RightOrLeftWall = false;
	private boolean reversed = false;
	//private boolean reversedM1 = false;
	//private boolean reversedM2 = false;
	//private boolean reversedM3 = false;
	private boolean hitMagnet = false;
	private boolean hitMagnet1 = false;
	private boolean hitMagnet2 = false;
	private boolean hitMagnet3 = false;
	private double ballFriction;
	private double magnetFriction;
	private int lastHit = 0;
	private int player1score;
	private int player2score;
	private Timer timer = new Timer(100 , this);
	
    //Timer tm = new Timer(100, this);
	
	/**
	 * Create a view of a GameArena.
	 *
	 * @param width The width of the playing area, in pixels.
	 * @param height The height of the playing area, in pixels.
	 */
	public GameArena(int width, int height)
	{
		this.init(width, height, true);
	}

	/**
	 * Create a view of a GameArena.
	 *
	 * @param width The width of the playing area, in pixels.
	 * @param height The height of the playing area, in pixels.
	 * @param createWindow Defines if a window should be created to host this GameArena. @see getPanel.
	 */
	public GameArena(int width, int height, boolean createWindow)
	{
		this.init(width, height, createWindow);
	}

	/**
	 * Internal initialisation method - called by constructor methods.
	 */
	private void init(int width, int height, boolean createWindow)
	{
		if (createWindow)
		{
			this.frame = new JFrame();
			frame.setTitle("Let's Play!");
			frame.setSize(width, height);
			frame.setResizable(false);
			frame.setBackground(Color.BLACK);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setContentPane(this);
			frame.setVisible(true);
		}

		this.setSize(width, height);

		// Add standard colours.
		colours.put("BLACK", Color.BLACK);
		colours.put("BLUE", Color.BLUE);
		colours.put("CYAN", Color.CYAN);
		colours.put("DARKGREY", Color.DARK_GRAY);
		colours.put("GREY", Color.GRAY);
		colours.put("GREEN", Color.GREEN);
		colours.put("LIGHTGREY", Color.LIGHT_GRAY);
		colours.put("MAGENTA", Color.MAGENTA);
		colours.put("ORANGE", Color.ORANGE);
		colours.put("PINK", Color.PINK);
		colours.put("RED", Color.RED);
		colours.put("WHITE", Color.WHITE);
		colours.put("YELLOW", Color.YELLOW);

		// Setup graphics rendering hints for quality
		renderingHints = new HashMap<>();
		renderingHints.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		renderingHints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		renderingHints.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		renderingHints.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		renderingHints.put(RenderingHints.KEY_FRACTIONALMETRICS,RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		renderingHints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		renderingHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		renderingHints.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		renderingHints.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

		try {
			Class rh = Class.forName("java.awt.RenderingHints");
			RenderingHints.Key key = (RenderingHints.Key) rh.getField("KEY_RESOLUTION_VARIANT").get(null);
			Object value = rh.getField("VALUE_RESOLUTION_VARIANT_DPI_FIT").get(null);
			renderingHints.put(key, value);
		}
		catch (Exception e){}

		Thread t = new Thread(this);
		t.start();

		this.addMouseListener(this);
		this.addMouseMotionListener(this);

		if (frame != null)
			frame.addKeyListener(this);
	}

	public void run() {
		try {
			while (!exiting) {
				this.repaint();
				Thread.sleep(10);
			}
		} catch (InterruptedException iex) {}

		if (frame != null)
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}

	/**
	 * Update the size of the GameArena.
	 *
	 * @param width the new width of the window in pixels.
	 * @param height the new height of the window in pixels.
	 */
	public void setSize(int width, int height)
	{
		this.arenaWidth = width;
		this.arenaHeight = height;

		super.setSize(width,height);

		if (frame != null)
			frame.setSize(arenaWidth + frame.getInsets().left + frame.getInsets().right, arenaHeight + frame.getInsets().top + frame.getInsets().bottom);


	}

	/**
	 * Set the background of the GameArena window to the specified image file.
	 * The image will be scaled to fit the size of the GameArena
	 *
	 * @param filename the filename of an image to use.
	 */
	public void setBackgroundImage(String filename)
	{
		Toolkit t=Toolkit.getDefaultToolkit();
		backgroundImage = t.getImage(filename);
	}

	/**
	 * Retrieves the JPanel on which this gameArena is drawn, so that it can be integrated into
	 * a users application.
	 *
	 * n.b. This should only be called if this GameArena was constructed without its own JFrame
	 *
	 * @return the JPanel containing this GameArena.
	 */
	public JPanel getPanel()
	{
		return this;
	}
	/**
	 * Close this GameArena window.
	 *
	 */
	public void exit()
	{
		this.exiting = true;
	}

	/**
	 * A method called by the operating system to draw onto the screen - <p><B>YOU DO NOT (AND SHOULD NOT) NEED TO CALL THIS METHOD.</b></p>
	 */
	public void paint (Graphics gr)
	{
		Graphics2D window = (Graphics2D) gr;

		if (!rendered)
		{
			this.setSize(arenaWidth, arenaHeight);

			// Create a buffer the same size of the window, which we can reuse from frame to frame to improve performance.
			buffer = new BufferedImage(arenaWidth, arenaHeight, BufferedImage.TYPE_INT_ARGB);
			graphics = buffer.createGraphics();
			graphics.setRenderingHints(renderingHints);

			// Remember that we've completed this initialisation, so that we don't do it again...
			rendered = true;
		}

		if (frame == null)
		{
			// Find the JFrame we have been added to, and attach a KeyListner
			frame = (JFrame) SwingUtilities.getWindowAncestor(this);

			if (frame != null)
				frame.addKeyListener(this);
		}

		window.setRenderingHints(renderingHints);

		synchronized (this)
		{
			if (!this.exiting)
			{
				graphics.clearRect(0,0, arenaWidth, arenaHeight);

				if (backgroundImage != null)
					graphics.drawImage(backgroundImage, 0, 0, arenaWidth, arenaHeight, 0, 0, backgroundImage.getWidth(null), backgroundImage.getHeight(null), null);

				for (Object o : things)
				{
					if (o instanceof Ball)
					{
						Ball b = (Ball) o;
						graphics.setColor(this.getColourFromString(b.getColour()));
						graphics.fillOval((int)(b.getXPosition() - b.getSize()/2), (int)(b.getYPosition() - b.getSize()/2), (int)b.getSize(), (int)b.getSize());
					}

					if (o instanceof Rectangle)
					{
						Rectangle r = (Rectangle) o;
						graphics.setColor(this.getColourFromString(r.getColour()));
						graphics.fillRect((int)r.getXPosition(), (int)r.getYPosition(), (int)r.getWidth(), (int)r.getHeight());
					}

					if (o instanceof Line)
					{
						Line l = (Line) o;
						graphics.setColor(this.getColourFromString(l.getColour()));
						graphics.setStroke(new BasicStroke((float)l.getWidth()));

						float sx = (float)l.getXStart();
						float sy = (float)l.getYStart();
						float ex = (float)l.getXEnd();
						float ey = (float)l.getYEnd();

						if (l.getArrowSize() > 0)
						{
							float arrowRatio = (float) (1.0 - ((l.getWidth() * l.getArrowSize()) / l.getLength()));
							ex = sx + ((ex - sx) * arrowRatio);
							ey = sy + ((ey - sy) * arrowRatio);
							graphics.fillPolygon(l.getArrowX(), l.getArrowY(), 3);
						}
						graphics.draw(new Line2D.Float(sx,sy,ex,ey));
					}

					if (o instanceof Text)
					{
						Text t = (Text) o;
						graphics.setFont(new Font("SansSerif", Font.BOLD, t.getSize()));
						graphics.setColor(this.getColourFromString(t.getColour()));
						graphics.drawString(t.getText(),(float)t.getXPosition(), (float)t.getYPosition());
					}
					if (o instanceof Goal)
					{
						Goal g = (Goal) o;
						graphics.setColor(this.getColourFromString(g.getColour()));
						graphics.fillOval((int)(g.getXPosition() - g.getSize()/2), (int)(g.getYPosition() - g.getSize()/2), (int)g.getSize(), (int)g.getSize());
					}
					if (o instanceof Magnets)
					{
						Magnets m = (Magnets) o;
						graphics.setColor(this.getColourFromString(m.getColour()));
						graphics.fillOval((int)(m.getXPosition() - m.getSize()/2), (int)(m.getYPosition() - m.getSize()/2), (int)m.getSize(), (int)m.getSize());
					}
					if (o instanceof Player)
					{
						Player p = (Player) o;
						graphics.setColor(this.getColourFromString(p.getColour()));
						graphics.fillOval((int)(p.getXPosition() - p.getSize()/2), (int)(p.getYPosition() - p.getSize()/2), (int)p.getSize(), (int)p.getSize());
						graphics.fillRect((int)p.getXPPosition(), (int)p.getYPPosition(), (int)p.getWidth(), (int)p.getHeight());
					}
				}
			}

			window.drawImage(buffer, this.getInsets().left, this.getInsets().top, this);
		}
	}

	//
	// Shouldn't really handle colour this way, but the student's haven't been introduced
	// to constants properly yet, hmmm....
	//
	private Color getColourFromString(String col)
	{
		Color c = colours.get(col.toUpperCase());

		if (c == null && col.startsWith("#"))
		{
			int r = Integer.valueOf( col.substring( 1, 3 ), 16 );
			int g = Integer.valueOf( col.substring( 3, 5 ), 16 );
			int b = Integer.valueOf( col.substring( 5, 7 ), 16 );

			c = new Color(r,g,b);
			colours.put(col.toUpperCase(), c);
		}

		if (c == null)
			c = Color.WHITE;

		return c;
	}

	/**
	 * Adds a given Object to the drawlist, maintaining z buffering order.
	 *
	 * @param o the object to add to the drawlist.
	 */
	private void addThing(Object o, int layer)
	{
		boolean added = false;

		if (exiting)
			return;

		synchronized (this)
		{
			if (things.size() > 100000)
			{
				System.out.println("\n\n");
				System.out.println(" ********************************************************* ");
				System.out.println(" ***** Only 100000 Objects Supported per Game Arena! ***** ");
				System.out.println(" ********************************************************* ");
				System.out.println("\n");
				System.out.println("-- Joe\n\n");

				this.exit();
			}
			else
			{
				// Try to insert this object into the list.
				for (int i=0; i<things.size(); i++)
				{
					int l = 0;
					Object obj = things.get(i);

					if (obj instanceof Ball)
						l = ((Ball)obj).getLayer();

					if (obj instanceof Rectangle)
						l = ((Rectangle)obj).getLayer();

					if (obj instanceof Line)
						l = ((Line)obj).getLayer();

					if (obj instanceof Text)
						l = ((Text)obj).getLayer();
					
					if (obj instanceof Goal)
						l = ((Goal)obj).getLayer();
					
					if (obj instanceof Magnets)
						l = ((Magnets)obj).getLayer();
					
					if (obj instanceof Player)
						l = ((Player)obj).getLayer();

					if (layer < l)
					{
						things.add(i,o);
						added = true;
						break;
					}
				}

				// If there are no items in the list with an equivalent or higher layer, append this object to the end of the list.
				if (!added)
					things.add(o);
			}
		}
	}

	/**
	 * Remove an object from the drawlist.
	 *
	 * @param o the object to remove from the drawlist.
	 */
	private void removeObject(Object o)
	{
		synchronized (this)
		{
			things.remove(o);
		}
	}

	/**
	 * Adds a given Ball to the GameArena.
	 * Once a Ball is added, it will automatically appear on the window.
	 *
	 * @param b the ball to add to the GameArena.
	 */
	public void addBall(Ball b)
	{
		this.addThing(b, b.getLayer());
	}

	/**
	 * Adds a given Rectangle to the GameArena.
	 * Once a rectangle is added, it will automatically appear on the window.
	 *
	 * @param r the rectangle to add to the GameArena.
	 */
	public void addRectangle(Rectangle r)
	{
		this.addThing(r, r.getLayer());
	}

	/**
	 * Adds a given Line to the GameArena.
	 * Once a Line is added, it will automatically appear on the window.
	 *
	 * @param l the line to add to the GameArena.
	 */
	public void addLine(Line l)
	{
		this.addThing(l, l.getLayer());
	}

	/**
	 * Adds a given Text object to the GameArena.
	 * Once a Text object is added, it will automatically appear on the window.
	 *
	 * @param t the text object to add to the GameArena.
	 */
	public void addText(Text t)
	{
		this.addThing(t, t.getLayer());
	}
	
	/**
	 * Adds a given Goal object to the GameArena.
	 * Once a Goal object is added, it will automatically appear on the window.
	 *
	 * @param g the text object to add to the GameArena.
	 */
	public void addGoal(Goal g)
	{
		this.addThing(g, g.getLayer());
	}
	
	/**
	 * Adds a given Magnets object to the GameArena.
	 * Once a Magnets object is added, it will automatically appear on the window.
	 *
	 * @param m the text object to add to the GameArena.
	 */
	public void addMagnet(Magnets m)
	{
		this.addThing(m, m.getLayer());
	}
	
	/**
	 * Adds a given Player object to the GameArena.
	 * Once a Player object is added, it will automatically appear on the window.
	 *
	 * @param p the text object to add to the GameArena.
	 */
	public void addPlayer(Player p)
	{
		this.addThing(p, p.getLayer());
	}


	/**
	 * Remove a Rectangle from the GameArena.
	 * Once a Rectangle is removed, it will no longer appear on the window.
	 *
	 * @param r the rectangle to remove from the GameArena.
	 */
	public void removeRectangle(Rectangle r)
	{
		this.removeObject(r);
	}

	/**
	 * Remove a Ball from the GameArena.
	 * Once a Ball is removed, it will no longer appear on the window.
	 *
	 * @param b the ball to remove from the GameArena.
	 */
	public void removeBall(Ball b)
	{
		this.removeObject(b);
	}

	/**
	 * Remove a Line from the GameArena.
	 * Once a Line is removed, it will no longer appear on the window.
	 *
	 * @param l the line to remove from the GameArena.
	 */
	public void removeLine(Line l)
	{
		this.removeObject(l);
	}

	/**
	 * Remove a Text object from the GameArena.
	 * Once a Text object is removed, it will no longer appear on the window.
	 *
	 * @param t the text object to remove from the GameArena.
	 */
	public void removeText(Text t)
	{
		this.removeObject(t);
	}

	/**
	 * Removes every object that has ever been added to the GameArena. Nothing
	 * should appear on the GameArena window after this has executed.
	 */
	public void clearGameArena() {
		synchronized(this) {
			things.clear();
		}
	}

	/**
	 * Pause for a 1/50 of a second.
	 * This method causes your program to delay for 1/50th of a second. You'll find this useful if you're trying to animate your application.
	 *
	 */
	public void pause()
	{
		try { Thread.sleep(20); }
		catch (Exception e) {};
	}

	/**
	 *	Internal method to check if ASCII code is A-Z.
	 *	@param asciiVal the ascii character value to check if it is within A-Z ascii.
	 *	@return true or false if the ascii character value is A-Z ascii.
	 */
	private boolean isAsciiAZ(int asciiVal)
	{
		if (asciiVal >= asciiAZOffset && asciiVal <= asciiAZOffset+alphabetLength)
			return true;
			
		return false;
	}

	/**
	 *	Check if an ASCII A-Z or a-z letter key has been pressed.
	 *	@param key the ASCII A-Z or a-z char to check if it has been pressed.
	 * 	@return whether or not that char has been pressed.
	 */
	public boolean letterPressed(char key)
	{
		if (Character.isLetter(key))
			if(isAsciiAZ((int) Character.toUpperCase(key)))
				return alphasPressed[(int) Character.toUpperCase(key) -asciiAZOffset];
		return false;
	}

 	public void keyPressed(KeyEvent e)
	{
		keyAction(e,true);
		
		b = KlaskGame.b;
		
		timer.start();
		
		if (e.getKeyCode() == KeyEvent.VK_W) 
		{
			if (player1 != null && player1.getXPosition() > 817 && player1.getXPosition() < 1534 && player1.getYPosition() > 78 && player1.getYPosition() < 826)
			{
				System.out.print("Player 1 Position: " + player1.getXPosition() + ", " + player1.getYPosition() + "\n");
				player1.move(0, -20);
			}
			else 
			{
				System.out.print("Out of bounds.");
				player1.move(0, 21);
			}
			
			if (b == null)
			{
				System.out.print("Ball is null!\n");
			}
			if (player1 != null && b != null && player1.collidesB(b))
			{
				reb1 = Rebound.getVelocity(20, 0, 20, 0, player1.getXPosition(), b.getXPosition(), player1.getYPosition(), b.getYPosition());
				hitWall = false;
				hitPlayer1 = true;
				hitPlayer2 = false;
				lastHit = 1;
			}
		} 
		else if (e.getKeyCode() == KeyEvent.VK_D)
		{
			if (player1 != null && player1.getXPosition() > 817 && player1.getXPosition() < 1534 && player1.getYPosition() > 78 && player1.getYPosition() < 826)
			{
				System.out.print("Player 1 Position: " + player1.getXPosition() + ", " + player1.getYPosition() + "\n");
				player1.move(20, 0);
			}
			else 
			{
				System.out.print("Out of bounds.");
				player1.move(-21, 0);
			}
			
			if (b == null)
			{
				System.out.print("Ball is null!\n");
			}
			if (player1 != null && b != null && player1.collidesB(b))
			{
				reb1 = Rebound.getVelocity(20, 0, 20, 0, player1.getXPosition(), b.getXPosition(), player1.getYPosition(), b.getYPosition());
				hitWall = false;
				hitPlayer1 = true;
				hitPlayer2 = false;
				lastHit = 1;
			}
		}
		else if (e.getKeyCode() == KeyEvent.VK_S)
		{
			if (player1 != null && player1.getXPosition() > 817 && player1.getXPosition() < 1534 && player1.getYPosition() > 78 && player1.getYPosition() < 826)
			{
				System.out.print("Player 1 Position: " + player1.getXPosition() + ", " + player1.getYPosition() + "\n");
				player1.move(0, 20);
			}
			else 
			{
				System.out.print("Out of bounds.");
				player1.move(0, -21);
			}
			
			if (b == null)
			{
				System.out.print("Ball is null!\n");
			}
			if (player1 != null && b != null && player1.collidesB(b))
			{
				reb1 = Rebound.getVelocity(20, 0, 20, 0, player1.getXPosition(), b.getXPosition(), player1.getYPosition(), b.getYPosition());
				hitWall = false;
				hitPlayer1 = true;
				hitPlayer2 = false;
				lastHit = 1;
			}
		}
		else if (e.getKeyCode() == KeyEvent.VK_A)
		{
			if (player1 != null && player1.getXPosition() > 817 && player1.getXPosition() < 1534 && player1.getYPosition() > 78 && player1.getYPosition() < 826)
			{
				System.out.print("\nPlayer 1 Position: " + player1.getXPosition() + ", " + player1.getYPosition() + "\n");
				player1.move(-20, 0);
			}
			else 
			{
				System.out.print("Out of bounds.");
				player1.move(21, 0);
			}
			
			if (b == null)
			{
				System.out.print("Ball is null!\n");
			}
			if (player1 != null && b != null && player1.collidesB(b))
			{
				reb1 = Rebound.getVelocity(20, 0, 20, 0, player1.getXPosition(), b.getXPosition(), player1.getYPosition(), b.getYPosition());
				hitWall = false;
				hitPlayer1 = true;
				hitPlayer2 = false;
				lastHit = 1;
			}
		} else if (e.getKeyCode() == KeyEvent.VK_UP)
		{
			if (player2 != null && player2.getXPosition() < 781 && player2.getXPosition() > 68 && player2.getYPosition() > 78 && player2.getYPosition() < 826)
			{
				System.out.print("Player 1 Position: " + player1.getXPosition() + ", " + player1.getYPosition() + "\n");
				player2.move(0, -20);
			}
			else 
			{
				System.out.print("Out of bounds.");
				player2.move(0, 21);
			}
			
			if (b == null)
			{
				System.out.print("Ball is null!\n");
			}
			if (player1 != null && b != null && player2.collidesB(b))
			{
				reb2 = Rebound.getVelocity(20, 0, 20, 0, player2.getXPosition(), b.getXPosition(), player2.getYPosition(), b.getYPosition());
				hitWall = false;
				hitPlayer1 = false;
				hitPlayer2 = true;
				lastHit = 2;
			}
		}
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			if (player2 != null && player2.getXPosition() < 781 && player2.getXPosition() > 68 && player2.getYPosition() > 78 && player2.getYPosition() < 826)
			{
				System.out.print("Player 1 Position: " + player1.getXPosition() + ", " + player1.getYPosition() + "\n");
				player2.move(20, 0);
			}
			else 
			{
				System.out.print("Out of bounds.");
				player2.move(-21, 0);
			}
			
			if (b == null)
			{
				System.out.print("Ball is null!\n");
			}
			if (player1 != null && b != null && player2.collidesB(b))
			{
				reb2 = Rebound.getVelocity(20, 0, 20, 0, player2.getXPosition(), b.getXPosition(), player2.getYPosition(), b.getYPosition());
				hitWall = false;
				hitPlayer1 = false;
				hitPlayer2 = true;
				lastHit = 2;
			}
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			if (player2 != null && player2.getXPosition() < 781 && player2.getXPosition() > 68 && player2.getYPosition() > 78 && player2.getYPosition() < 826)
			{
				System.out.print("Player 1 Position: " + player1.getXPosition() + ", " + player1.getYPosition() + "\n");
				player2.move(0, 20);
			}
			else 
			{
				System.out.print("Out of bounds.");
				player2.move(0, -21);
			}
			
			if (b == null)
			{
				System.out.print("Ball is null!\n");
			}
			if (player1 != null && b != null && player2.collidesB(b))
			{
				reb2 = Rebound.getVelocity(20, 0, 20, 0, player2.getXPosition(), b.getXPosition(), player2.getYPosition(), b.getYPosition());
				hitWall = false;
				hitPlayer1 = false;
				hitPlayer2 = true;
				lastHit = 2;
			}
		}
		else if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			if (player2 != null && player2.getXPosition() < 781 && player2.getXPosition() > 68 && player2.getYPosition() > 78 && player2.getYPosition() < 826)
			{
				System.out.print("Player 1 Position: " + player1.getXPosition() + ", " + player1.getYPosition() + "\n");
				player2.move(-20, 0);
			}
			else 
			{
				System.out.print("Out of bounds.");
				player2.move(21, 0);
			}
			
			if (b == null)
			{
				System.out.print("Ball is null!\n");
			}
			if (player1 != null && b != null && player2.collidesB(b))
			{
				reb2 = Rebound.getVelocity(20, 0, 20, 0, player2.getXPosition(), b.getXPosition(), player2.getYPosition(), b.getYPosition());
				hitWall = false;
				hitPlayer1 = false;
				hitPlayer2 = true;
				lastHit = 2;
			}
		}
	}

	public void keyAction(KeyEvent e,boolean yn)
	{
		int code = e.getKeyCode();

		if (code == KeyEvent.VK_UP)
			up = yn;
		if (code == KeyEvent.VK_DOWN)
			down = yn;
		if (code == KeyEvent.VK_LEFT)
			left = yn;
		if (code == KeyEvent.VK_RIGHT)
			right = yn;
		if (code == KeyEvent.VK_SPACE)
			space = yn;
		if (code == KeyEvent.VK_SHIFT)
			shift = yn;
		if (code == KeyEvent.VK_ESCAPE)
			esc = yn;
		if (code == KeyEvent.VK_ENTER)
			enter = yn;
		//ASCII A-Z check
		if (isAsciiAZ(code))
			alphasPressed[code-asciiAZOffset] = yn;
	}

	public void keyReleased(KeyEvent e){
		keyAction(e,false);
	}


 	public void keyTyped(KeyEvent e)
	{
	}


	public void mousePressed(MouseEvent e)
	{
		if (e.getButton() == MouseEvent.BUTTON1)
			this.leftMouse = true;

		if (e.getButton() == MouseEvent.BUTTON3)
			this.rightMouse = true;
	}

	public void mouseReleased(MouseEvent e)
	{
		if (e.getButton() == MouseEvent.BUTTON1)
			this.leftMouse = false;

		if (e.getButton() == MouseEvent.BUTTON3)
			this.rightMouse = false;
	}

	public void mouseEntered(MouseEvent e)
	{
	}

	public void mouseExited(MouseEvent e)
	{
	}

	public void mouseClicked(MouseEvent e)
	{
	}

	public void mouseMoved(MouseEvent e)
	{
		mouseX = e.getX();
		mouseY = e.getY();
	}

	public void mouseDragged(MouseEvent e)
	{
		mouseX = e.getX();
		mouseY = e.getY();
	}

	/**
	 * Gets the width of the GameArena window, in pixels.
	 * @return the width in pixels
	 */
	public int getArenaWidth()
	{
		return arenaWidth;
	}

	/**
	 * Gets the height of the GameArena window, in pixels.
	 * @return the height in pixels
	 */
	public int getArenaHeight()
	{
		return arenaHeight;
	}

	/**
	 * Determines if the user is currently pressing the cursor up button.
	 * @return true if the up button is pressed, false otherwise.
	 */
	public boolean upPressed()
	{
		return up;
	}

	/**
	 * Determines if the user is currently pressing the cursor down button.
	 * @return true if the down button is pressed, false otherwise.
	 */
	public boolean downPressed()
	{
		return down;
	}

	/**
	 * Determines if the user is currently pressing the cursor left button.
	 * @return true if the left button is pressed, false otherwise.
	 */
	public boolean leftPressed()
	{
		return left;
	}

	/**
	 * Determines if the user is currently pressing the cursor right button.
	 * @return true if the right button is pressed, false otherwise.
	 */
	public boolean rightPressed()
	{
		return right;
	}

	/**
	 * Determines if the user is currently pressing the space bar.
	 * @return true if the space bar is pressed, false otherwise.
	 */
	public boolean spacePressed()
	{
		return space;
	}

        /**
	 * Determines if the user is currently pressing the Esc button.
	 * @return true if the esc button is pressed, false otherwise.
	 */
	public boolean escPressed()
	{
		return esc;
	}

	/**
	 * Determines if the user is currently pressing the enter button.
	 * @return true if the enter button is pressed, false otherwise.
	 */
	public boolean enterPressed()
	{
		return enter;
	}

	/**
	 * Determines if the user is currently pressing the shift key.
	 * @return true if the shift key is pressed, false otherwise.
	 */
	public boolean shiftPressed()
	{
		return shift;
	}

	/**
	 * Determines if the user is currently pressing the left mouse button.
	 * @return true if the left mouse button is pressed, false otherwise.
	 */
	public boolean leftMousePressed()
	{
		return leftMouse;
	}

	/**
	 * Determines if the user is currently pressing the right mouse button.
	 * @return true if the right mouse button is pressed, false otherwise.
	 */
	public boolean rightMousePressed()
	{
		return rightMouse;
	}

	/**
	 * Gathers location informaiton on the mouse pointer.
	 * @return the current X coordinate of the mouse pointer in the GameArena.
	 */
	public int getMousePositionX()
	{
		return mouseX;
	}

	/**
	 * Gathers location informaiton on the mouse pointer.
	 * @return the current Y coordinate of the mouse pointer in the GameArena.
	 */
	public int getMousePositionY()
	{
		return mouseY;
	}
	
	/**
	 * Handles magnet collisions and friction.
	 */
	public void magnets()
	{
		b = KlaskGame.b;
		m1 = KlaskGame.magnet1;
		m2 = KlaskGame.magnet2;
		m3 = KlaskGame.magnet3;
		magnetFriction = 0.15;
		
		if (rebM1 != null)
		{
			frictionMethod = Friction.addFriction(rebM1[0], rebM1[1], magnetFriction);
			rebM1[0] = frictionMethod[0];
			rebM1[1] = frictionMethod[1];
		}
		if (rebM2 != null)
		{
			frictionMethod = Friction.addFriction(rebM2[0], rebM2[1], magnetFriction);
			rebM2[0] = frictionMethod[0];
			rebM2[1] = frictionMethod[1];
		}
		if (rebM2 != null)
		{
			frictionMethod = Friction.addFriction(rebM2[0], rebM2[1], magnetFriction);
			rebM2[0] = frictionMethod[0];
			rebM2[1] = frictionMethod[1];
		}
		
		/*
		if ((m1.getYPosition() < 61 || m1.getYPosition() > 840) || (m1.getXPosition() < 51 || m1.getXPosition()> 1550))		
		{
			hitWallM1 = true;
			hitMagnet1 = false;
			hitMagnet = false;
		}
		if ((m2.getYPosition() < 61 || m2.getYPosition() > 840) || (m2.getXPosition() < 51 || m2.getXPosition() > 1550))
		{
			hitWallM2 = true;
			hitMagnet2 = false;
			hitMagnet = false;
		}
		if ((m3.getYPosition() < 61 || m3.getYPosition() > 840) || (m3.getXPosition() < 51 || m3.getXPosition() > 1550))
		{
			hitWallM3 = true;
			hitMagnet3 = false;
			hitMagnet = false;
		}
		if (m1.getYPosition() < 61 || m1.getYPosition() > 840)
		{
			hitM1RightOrLeftWall = false;
			hitM1BottomOrTopWall = true;
			//reversedM1 = false;
		}
		if (m1.getXPosition() < 51 || m1.getXPosition() > 1550)
		{
			hitM1RightOrLeftWall = true;
			hitM1BottomOrTopWall = false;
			//reversedM1 = false;
		}
		if (m2.getYPosition() < 61 || m2.getYPosition() > 840)
		{
			hitM2RightOrLeftWall = false;
			hitM2BottomOrTopWall = true;
			reversedM2 = false;
		}
		if (m2.getXPosition() < 51 || m2.getXPosition() > 1550)
		{
			hitM2RightOrLeftWall = true;
			hitM2BottomOrTopWall = false;
			reversedM2 = false;
		}
		if (m3.getYPosition() < 61 || m3.getYPosition() > 840)
		{
			hitM1RightOrLeftWall = false;
			hitM1BottomOrTopWall = true;
			reversedM3 = false;
		}
		if (m3.getXPosition() < 51 || m3.getXPosition() > 1550)
		{
			hitM3RightOrLeftWall = true;
			hitM3BottomOrTopWall = false;
			reversedM3 = false;
		}
		
		
		if (hitWallM1 == true && hitM1BottomOrTopWall == true)
		{
			if (reversedM1 == false)
			{
				System.out.print("Running!");
				rebM1[1] = -rebM1[1];
				b.move(rebM1[0], rebM1[1]);
				reversedM1 = true;
			}
			else 
			{
				b.move(rebM1[0], rebM1[1]);
			}
		}
		if (hitWallM1 == true && hitM1RightOrLeftWall == true)
		{
			if (reversedM1 == false)
			{
				rebM1[0] = -rebM1[0];
				b.move(rebM1[0], rebM1[1]);
				reversedM1 = true;
			}
			else 
			{
				b.move(rebM1[0], rebM1[1]);
			}
		}
		if (hitWallM2 == true && hitM2BottomOrTopWall == true)
		{
			if (reversedM2 == false)
			{
				rebM2[1] = -rebM2[1];
				b.move(rebM2[0], rebM2[1]);
				reversedM2 = true;
			}
			else 
			{
				b.move(rebM2[0], rebM2[1]);
			}
		}
		if (hitWallM2 == true && hitM2RightOrLeftWall == true)
		{
			if (reversedM2 == false)
			{
				rebM2[0] = -rebM2[0];
				b.move(rebM2[0], rebM2[1]);
				reversedM2 = true;
			}
			else 
			{
				b.move(rebM2[0], rebM2[1]);
			}
		}
		if (hitWallM3 == true && hitM3BottomOrTopWall == true)
		{
			if (reversedM3 == false)
			{
				rebM3[1] = -rebM3[1];
				b.move(rebM3[0], rebM3[1]);
				reversedM3 = true;
			}
			else 
			{
				b.move(rebM3[0], rebM3[1]);
			}
		}
		if (hitWallM3 == true && hitM3RightOrLeftWall == true)
		{
			if (reversedM3 == false)
			{
				rebM1[0] = -rebM1[0];
				b.move(rebM3[0], rebM3[1]);
				reversedM3 = true;
			}
			else 
			{
				b.move(rebM3[0], rebM3[1]);
			}
		}
		*/
		
		if (b != null && m1 != null && m2 != null && m3 != null)
		{
			
			if (b.collidesM(m1))
			{
				hitMagnet = false;
				hitMagnet1 = true;
				
			}
			if (b.collidesM(m2))
			{
				hitMagnet = false;
				hitMagnet2 = true;
			}
			if (b.collidesM(m3))
			{
				hitMagnet = false;
				hitMagnet3 = true;
			}
			if (lastHit == 1)
			{
				if (hitMagnet1 == true)
				{
					if (hitMagnet == false)
					{
						rebM1 = Rebound.getVelocity(0, reb1[2], 0, reb1[3], m1.getXPosition(), b.getXPosition(), m1.getYPosition(), b.getYPosition());
						hitMagnet = true;
						reb1[2] = rebM1[2];
						reb1[3] = rebM1[3];
						m1.move(rebM1[0], rebM1[1]);
						b.move(reb1[2], reb1[3]);
					}
				}
				if (hitMagnet2 == true)
				{
					if (hitMagnet == false)
					{
						rebM2 = Rebound.getVelocity(0, reb1[2], 0, reb1[3], m1.getXPosition(), b.getXPosition(), m1.getYPosition(), b.getYPosition());
						hitMagnet = true;
						reb1[2] = rebM2[2];
						reb1[3] = rebM2[3];
						m2.move(rebM2[0], rebM2[1]);
						b.move(reb1[2], reb1[3]);
					}
				}
				if (hitMagnet3 == true)
				{
					if (hitMagnet == false)
					{
						rebM3 = Rebound.getVelocity(0, reb1[2], 0, reb1[3], m1.getXPosition(), b.getXPosition(), m1.getYPosition(), b.getYPosition());
						hitMagnet = true;
						reb1[2] = rebM3[2];
						reb1[3] = rebM3[3];
						m3.move(rebM3[0], rebM3[1]);
						b.move(reb1[2], reb1[3]);
					}
				}
				if (hitMagnet == true && hitMagnet1 == true)
				{
					m1.move(rebM1[0], rebM1[1]);
					b.move(reb1[2], reb1[3]);
				}
				if (hitMagnet == true && hitMagnet2 == true)
				{
					m2.move(rebM2[0], rebM2[1]);
					b.move(reb1[2], reb1[3]);
				}
				if (hitMagnet == true && hitMagnet3 == true)
				{
					m3.move(rebM3[0], rebM3[1]);
					b.move(reb1[2], reb1[3]);
				}
			}
			if (lastHit == 2)
			{
				if (hitMagnet1 == true)
				{
					if (hitMagnet == false)
					{
						rebM1 = Rebound.getVelocity(0, reb2[2], 0, reb2[3], m1.getXPosition(), b.getXPosition(), m1.getYPosition(), b.getYPosition());
						hitMagnet = true;
						reb2[2] = rebM1[2];
						reb2[3] = rebM1[3];
						m2.move(rebM1[0], rebM1[1]);
						b.move(reb2[2], reb2[3]);
					}
				}
				if (hitMagnet2 == true)
				{
					if (hitMagnet == false)
					{
						rebM2 = Rebound.getVelocity(0, reb2[2], 0, reb2[3], m1.getXPosition(), b.getXPosition(), m1.getYPosition(), b.getYPosition());
						hitMagnet = true;
						reb2[2] = rebM2[2];
						reb2[3] = rebM2[3];
						m2.move(rebM2[0], rebM2[1]);
						b.move(reb2[2], reb2[3]);
					}
				}
				if (hitMagnet3 == true)
				{
					if (hitMagnet == false)
					{
						rebM3 = Rebound.getVelocity(0, reb2[2], 0, reb2[3], m1.getXPosition(), b.getXPosition(), m1.getYPosition(), b.getYPosition());
						hitMagnet = true;
						reb2[2] = rebM3[2];
						reb2[3] = rebM3[3];
						m3.move(rebM3[0], rebM3[1]);
						b.move(reb2[2], reb2[3]);
					}
				}
				if (hitMagnet == true && hitMagnet1 == true)
				{
					m1.move(rebM1[0], rebM1[1]);
					b.move(reb2[2], reb2[3]);
				}
				if (hitMagnet == true && hitMagnet2 == true)
				{
					m2.move(rebM2[0], rebM2[1]);
					b.move(reb2[2], reb2[3]);
				}
				if (hitMagnet == true && hitMagnet3 == true)
				{
					m3.move(rebM3[0], rebM3[1]);
					b.move(reb2[2], reb2[3]);
				}
			}
		}
	}
	/**
	 * Handles ball collisions and friction.
	 */
	public void ball()
	{
		b = KlaskGame.b;
		ballFriction = 0.05;
		
		System.out.print("\nHit Magnet Bool: " + hitMagnet + "\n");
		
		if (reb1 != null)
		{
			frictionMethod = Friction.addFriction(reb1[2], reb1[3], ballFriction);
			reb1[2] = frictionMethod[0];
			reb1[3] = frictionMethod[1];
			
			System.out.print("\n[Friction] Value: " + frictionMethod[0] + " | " + frictionMethod[1] + "\n");
			System.out.print("\n[Rebound] X: " + reb1[2] + " | y " + reb1[3] + "\n");
		}
		if (reb2 != null)
		{
			frictionMethod = Friction.addFriction(reb2[2], reb2[3], ballFriction);
			reb2[2] = frictionMethod[0];
			reb2[3] = frictionMethod[1];
			
			System.out.print("\n[Friction] Value: " + frictionMethod[0] + " | " + frictionMethod[1] + "\n");
			System.out.print("\n[2] X: " + reb2[2] + " | y " + reb2[3] + "\n");
		}
		
		if (b.getYPosition() < 73 || b.getYPosition() > 830 || b.getXPosition() < 63 || b.getXPosition() > 1537 )
		{
			hitPlayer1 = false;
			hitPlayer2 = false;
			hitWall = true;
		}
		if (b.getYPosition() < 73 || b.getYPosition() > 832)
		{
			hitRightOrLeftWall = false;
			hitBottomOrTopWall = true;
			reversed = false;
		}
		if (b.getXPosition() < 63 || b.getXPosition() > 1537)
		{
			hitBottomOrTopWall = false;
			hitRightOrLeftWall = true;
			reversed = false;
		}
		
		if (player1 != null && b != null && player1.collidesB(b))
		{
			if (hitPlayer1 == false)
			{
				hitPlayer1 = true;
				reb1 = Rebound.getVelocity(20, 0, 20, 0, player1.getXPosition(), b.getXPosition(), player1.getYPosition(), b.getYPosition());
				hitWall = false;
				hitPlayer1 = true;
				hitPlayer2 = false;
				lastHit = 1;
			}
		}
		if (player2 != null && b != null && player2.collidesB(b))
		{
			if (hitPlayer2 == false)
			{
				hitPlayer2 = true;
				reb2 = Rebound.getVelocity(20, 0, 20, 0, player2.getXPosition(), b.getXPosition(), player2.getYPosition(), b.getYPosition());
				hitWall = false;
				hitPlayer1 = true;
				hitPlayer2 = false;
				lastHit = 1;
			}
		}
		if (hitPlayer1 == true)
		{
			b.move(reb1[2], reb1[3]);
		}
		if (hitPlayer2 == true)
		{
			b.move(reb2[2], reb2[3]);
		}
		if (hitWall == true && lastHit == 1 && hitBottomOrTopWall == true)
		{
			if (reversed == false)
			{
				reb1[3] = -reb1[3];
				b.move(reb1[2], reb1[3]);
				reversed = true;
			}
			else 
			{
				b.move(reb1[2], reb1[3]);
			}
		}
		if (hitWall == true && lastHit == 2 && hitBottomOrTopWall == true)
		{
			if (reversed == false)
			{
				reb2[3] = -reb2[3];
				b.move(reb2[2], reb2[3]);
				reversed = true;
			}
			else 
			{
				b.move(reb2[2], reb2[3]);
			}
		}
		if (hitWall == true && lastHit == 1 && hitRightOrLeftWall == true)
		{
			if (reversed == false)
			{
				reb1[2] = -reb1[2];
				b.move(reb1[2], reb1[3]);
				reversed = true;
			}
			else 
			{
				b.move(reb1[2], reb1[3]);
			}
		}
		if (hitWall == true && lastHit == 2 && hitRightOrLeftWall == true)
		{
			if (reversed == false)
			{
				reb2[2] = -reb2[2];
				b.move(reb2[2], reb2[3]);
				reversed = true;
			}
			else
			{
				b.move(reb2[2], reb2[3]);
			}
		}
	}
	
	/**
	 * Handles when the ball enters either goal, adding to the scores and resetting pieces.
	 */
	public void playerScores()
	{
		g = KlaskGame.goal1;
		g2 = KlaskGame.goal2;
		
		if (b.collidesG(g))
		{
			player1score++;
			String s = String.valueOf(player1score);		
			KlaskGame.player1score.setText(s);
			timer.stop();
			
			Random ran = new Random();
			int start = ran.nextInt(1) + 0;
			
			if (start == 1)
			{
				b.setXPosition(1511);
				b.setYPosition(100);
				m1.setXPosition(795);
				m1.setYPosition(451);
				m2.setXPosition(795);
				m2.setYPosition(219);
				m3.setXPosition(795);
				m3.setYPosition(684);
				if (reb1 != null)
				{
					reb1[2] = 0;
					reb2[3] = 0;
				}
				if (reb2 != null)
				{
					reb1[2] = 0;
					reb2[3] = 0;
				}
				timer.start();
			}
			else if (start == 0)
			{
				b.setXPosition(1516);
				b.setYPosition(804);
				m1.setXPosition(795);
				m1.setYPosition(451);
				m2.setXPosition(795);
				m2.setYPosition(219);
				m3.setXPosition(795);
				m3.setYPosition(684);
				if (reb1 != null)
				{
					reb1[2] = 0;
					reb1[3] = 0;
				}
				if (reb2 != null)
				{
					reb2[2] = 0;
					reb2[3] = 0;
				}
				timer.start();
			}
		}
		if (b.collidesG(g2))
		{
			player2score++;
			String s = String.valueOf(player2score);		
			KlaskGame.player1score.setText(s);
			timer.stop();
			
			Random ran = new Random();
			int start = ran.nextInt(1) + 0;
			
			if (start == 1)
			{
				b.setXPosition(89);
				b.setYPosition(103);
				m1.setXPosition(795);
				m1.setYPosition(451);
				m2.setXPosition(795);
				m2.setYPosition(219);
				m3.setXPosition(795);
				m3.setYPosition(684);
				if (reb1 != null)
				{
					reb1[2] = 0;
					reb1[3] = 0;
				}
				if (reb2 != null)
				{
					reb2[2] = 0;
					reb2[3] = 0;
				}
				timer.start();
			}
			else if (start == 0)
			{
				b.setXPosition(91);
				b.setYPosition(805);
				m1.setXPosition(795);
				m1.setYPosition(451);
				m2.setXPosition(795);
				m2.setYPosition(219);
				m3.setXPosition(795);
				m3.setYPosition(684);
				if (reb1 != null)
				{
					reb1[2] = 0;
					reb1[3] = 0;
				}
				if (reb2 != null)
				{
					reb2[2] = 0;
					reb2[3] = 0;
				}
				timer.start();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		
		ball();
		magnets();
		playerScores();
		
	}
}
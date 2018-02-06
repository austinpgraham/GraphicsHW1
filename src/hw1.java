//******************************************************************************
// Last modified: Sun Feb 04 2018 by Austin Graham
//******************************************************************************
//
// Recreate the picture given in the homework description
//
//******************************************************************************
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

//******************************************************************************

/**
 * The <CODE>hw1</CODE> class.<P>
 *
 * @author  Austin Graham
 * @version %I%, %G%
 */
public final class hw1
{
	private static final Rectangle	BOUNDS = new Rectangle(50, 50, 400, 420);

	public static void	main(String[] argv)
	{
		JFrame	frame = new JFrame("Picture For HW1");
		JPanel	panel = new Panel();

		frame.getContentPane().add(panel);
		frame.setVisible(true);
		frame.setBounds(BOUNDS);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.validate();

		panel.revalidate();
		panel.repaint();
	}

	private static final class Panel extends JPanel
	{

    public void drawArrow(Graphics2D g, int sourceX, int sourceY)
    {
      // Draw the arrows
      Path2D.Double path = new Path2D.Double();
      path.moveTo(sourceX, sourceY);
      path.lineTo(sourceX + 20, sourceY - 20);
      path.lineTo(sourceX + 20, sourceY - 8);
			path.lineTo(sourceX + 60, sourceY - 8);
			path.lineTo(sourceX + 60, sourceY - 20);
			path.lineTo(sourceX + 80, sourceY);
			path.lineTo(sourceX + 60, sourceY + 20);
			path.lineTo(sourceX + 60, sourceY + 8);
			path.lineTo(sourceX + 20, sourceY + 8);
			path.lineTo(sourceX + 20, sourceY + 20);
      path.lineTo(sourceX, sourceY);

      g.setColor(Color.YELLOW);
      g.fill(path);
      g.setColor(Color.BLACK);
      g.draw(path);
    }

		public void drawCloud(Graphics2D g, int sourceX, int sourceY)
		{
			g.setStroke(new BasicStroke(1));
			g.setColor(new Color(169, 169, 169, 150));
			Ellipse2D.Double cloud = new Ellipse2D.Double(sourceX, sourceY, 70, 27);
			g.fill(cloud);
			g.setColor(new Color(102, 102, 102));
			g.draw(cloud);
		}

		public void drawCatEar(Graphics2D g, int sourceX, int sourceY)
		{
			g.setColor(new Color(204, 232, 255));
			final Path2D.Double ear = new Path2D.Double();
			ear.moveTo(sourceX, sourceY);
			ear.lineTo(sourceX + 10, sourceY + 30);
			ear.lineTo(sourceX - 10, sourceY + 30);
			ear.lineTo(sourceX, sourceY);
			g.fill(ear);
			g.setColor(Color.BLACK);
			g.draw(ear);
		}

		public void		paintComponent(Graphics graphics)
		{
      final int WIDTH = (int)BOUNDS.getWidth();
      final int HEIGHT = (int)BOUNDS.getHeight();
      Graphics2D g = (Graphics2D)graphics;

      // Draw the road with a gray background
      final Rectangle ROAD = new Rectangle(0, HEIGHT - 100, WIDTH, 100);
      final Color ROAD_COLOR = new Color(169, 169, 169);
      g.setColor(ROAD_COLOR);
      g.fill(ROAD);
      g.draw(ROAD);

      // Draw the dirt with a gradient
      final Rectangle DIRT = new Rectangle(0, HEIGHT / 2 - 100, WIDTH, (int)ROAD.getY() - (HEIGHT / 2 - 100));
      final Color BROWN = new Color(139, 69, 19);
      final Color ROSY_BROWN = new Color(165, 42, 42);
      final GradientPaint DIRT_PAINT = new GradientPaint(800, 1080, BROWN, 720, 1080, ROSY_BROWN, true);
      g.setPaint(DIRT_PAINT);
      g.fill(DIRT);
      g.draw(DIRT);

      // Draw the sky with gradient
      final Rectangle SKY = new Rectangle(0, 0, WIDTH, HEIGHT / 2 - 50);
      final Color PALE_BLUE = new Color(153, 204, 255);
      final GradientPaint SKY_PAINT = new GradientPaint(200, 0, PALE_BLUE, 200, 300, Color.WHITE, false);
      g.setPaint(SKY_PAINT);
      g.fill(SKY);
      g.draw(SKY);

      // Draw the arrows on the road
      drawArrow(g, 10, (int)(ROAD.getHeight() / 2) + (int)ROAD.getY());
      drawArrow(g, WIDTH / 2 - 50, (int)(ROAD.getHeight() / 2) + (int)ROAD.getY());
      drawArrow(g, WIDTH - 105, (int)(ROAD.getHeight() / 2) + (int)ROAD.getY());

      // Draw the grass
      g.setStroke(new BasicStroke(5));
      g.setColor(new Color(12, 144, 36));
      for(int x = 0; x < WIDTH + 20; x+=8)
      {
        g.draw(new Line2D.Double(x, (int)(DIRT.getY() + DIRT.getHeight() * .75), x - 10, (int)(DIRT.getY() + DIRT.getHeight())));
      }

      // Draw the tree
      final Rectangle TREE_TRUNK = new Rectangle(30, 120, 15, 160);
      g.setStroke(new BasicStroke(1));
      g.setColor(new Color(102, 51, 0));
      g.fill(TREE_TRUNK);
      g.setColor(Color.BLACK);
      g.draw(TREE_TRUNK);

			// Draw the tree top
			g.setStroke(new BasicStroke(5));
			Path2D.Double treeFace = new Path2D.Double();
			treeFace.moveTo(38, 60);
			treeFace.lineTo(68, 180);
			treeFace.lineTo(8, 180);
			treeFace.lineTo(38, 60);
			g.setColor(new Color(12, 144, 36, 150));
			g.fill(treeFace);
			g.setColor(new Color(12, 114, 26));
			g.draw(treeFace);

			// Draw the sun
			Image sun = fullyLoadImage("sun.png");
			final float SCALE = .40f;
			g.drawImage(sun, 275, 10, (int)(sun.getWidth(null)*SCALE), (int)(sun.getHeight(null)*SCALE), null);

			// Draw the clouds
			drawCloud(g, 100, 20);
			drawCloud(g, 135, 30);
			drawCloud(g, 135, 25);
			drawCloud(g, 120, 40);
			drawCloud(g, 100, 35);

			// Draw ball
			g.setStroke(new BasicStroke(0));
			final GradientPaint BALL_PAINT = new GradientPaint(100, 250, Color.RED, 200, 170, Color.WHITE, false);
			g.setPaint(BALL_PAINT);
			final Ellipse2D.Double BALL = new Ellipse2D.Double(110, 200, 40, 40);
			g.fill(BALL);
			g.draw(BALL);

			// Draw the shadow
			final Ellipse2D.Double SHADOW = new Ellipse2D.Double(85, 240, 50, 20);
			g.setColor(new Color(15, 15, 15, 155));
			g.fill(SHADOW);
			g.draw(SHADOW);

			// Draw the text
			Font font = new Font("Serif", Font.PLAIN, 35);
			g.setColor(Color.BLACK);
			g.setFont(font);
			g.drawString("so", 57, 80);
			g.setStroke(new BasicStroke(3));
			g.draw(new Line2D.Double(57, 83, 87, 83));

			final Rectangle muchRect = new Rectangle(90, 80, 85, 40);
			g.setStroke(new BasicStroke(1));
			g.draw(muchRect);

			g.setColor(Color.RED);
			font = new Font("Serif", Font.PLAIN, 35);
			g.setFont(font);
			g.drawString("much", 93, 108);

			final RoundRectangle2D.Double colorEllipse = new RoundRectangle2D.Double(177, 107, 85, 45, 50, 50);
			g.setColor(new Color(0, 153, 153));
			g.fill(colorEllipse);
			g.setColor(Color.BLACK);
			g.setStroke(new BasicStroke(3));
			g.draw(colorEllipse);

			font = new Font("Serif", Font.ITALIC, 30);
			g.setColor(Color.WHITE);
			g.setFont(font);
			g.drawString("color!", 181, 137);

			g.setStroke(new BasicStroke(1));
			// Draw right cat ear
			drawCatEar(g, 220, 150);
			// Draw cat head
			final Ellipse2D.Double catHead = new Ellipse2D.Double(175, 170, 55, 30);
			g.setColor(new Color(204, 232, 255));
			g.fill(catHead);
			g.setColor(Color.BLACK);
			g.draw(catHead);
			// Draw cat eye
			final Ellipse2D.Double eye = new Ellipse2D.Double(183, 177, 13, 8);
			g.setColor(Color.WHITE);
			g.fill(eye);
			g.setColor(Color.BLACK);
			g.draw(eye);
			final Ellipse2D.Double pupil = new Ellipse2D.Double(184, 180, 3, 3);
			g.setColor(new Color(0, 153, 153));
			g.fill(pupil);
			g.setColor(Color.BLACK);
			g.draw(pupil);
			// Draw left cat ear
			drawCatEar(g, 210, 150);
			// Draw the whiskers
			g.setStroke(new BasicStroke(1));
			g.setColor(Color.BLACK);
			g.draw(new Arc2D.Double(169, 188, 35, 25, 90, 80, Arc2D.OPEN));
			g.draw(new Arc2D.Double(162, 188, 35, 25, 90, 80, Arc2D.OPEN));
			g.draw(new Arc2D.Double(178, 188, 35, 25, 90, 80, Arc2D.OPEN));

			// Draw the cats body
			final Ellipse2D.Double catBody = new Ellipse2D.Double(195, 195, 80, 40);
			g.setColor(new Color(204, 232, 255));
			g.fill(catBody);
			g.setColor(Color.BLACK);
			g.draw(catBody);
		}

		private Image	fullyLoadImage(String filename)
		{
			Image	image = null;

			// We're going to do IO and thread stuff, so catch some exceptions
			try
			{
				// Load an image file into an image object
				image = Toolkit.getDefaultToolkit().createImage(filename);

				// This class watches for media loads & calculations to finish
				MediaTracker	tracker = new MediaTracker(this);

				tracker.addImage(image, 0);		// Track loading of the image
				tracker.waitForAll();			// Block until it's fully loaded
			}
			catch (Exception ex)
			{
				System.err.println("that wasn't supposed to happen");
				ex.printStackTrace();
				System.exit(1);
			}

			return image;
		}
	}
}

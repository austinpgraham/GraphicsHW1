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
 * The <CODE>Drawing</CODE> class.<P>
 *
 * @author  Austin Graham
 * @version %I%, %G%
 */
public final class hw1
{
	private static final Rectangle	BOUNDS = new Rectangle(50, 50, 800, 700);

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
      path.lineTo(sourceX + 50, sourceY - 50);
      path.lineTo(sourceX + 50, sourceY + 50);
      path.lineTo(sourceX, sourceY);

      Path2D.Double rightPath = new Path2D.Double();
      path.moveTo(sourceX + 200, sourceY);
      path.lineTo(sourceX + 150, sourceY - 50);
      path.lineTo(sourceX + 150, sourceY + 50);
      path.lineTo(sourceX + 200, sourceY);

      Rectangle middlePart = new Rectangle(sourceX + 50, sourceY - 20, 100, 40);

      g.setColor(Color.YELLOW);
      g.fill(path);
      g.fill(rightPath);
      g.fill(middlePart);
      g.setColor(Color.BLACK);
      g.draw(middlePart);
      g.draw(path);
      g.draw(rightPath);
    }

		public void		paintComponent(Graphics graphics)
		{
      final int WIDTH = (int)BOUNDS.getWidth();
      final int HEIGHT = (int)BOUNDS.getHeight();
      Graphics2D g = (Graphics2D)graphics;

      // Draw the road with a gray background
      final Rectangle ROAD = new Rectangle(0, HEIGHT - 150, WIDTH, HEIGHT);
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
      final Rectangle SKY = new Rectangle(0, 0, WIDTH, HEIGHT / 2);
      final Color PALE_BLUE = new Color(153, 204, 255);
      final GradientPaint SKY_PAINT = new GradientPaint(200, 0, PALE_BLUE, 200, 300, Color.WHITE, false);
      g.setPaint(SKY_PAINT);
      g.fill(SKY);
      g.draw(SKY);

      // Draw the arrows on the road
      drawArrow(g, 50, HEIGHT - 75);
      drawArrow(g, 300, HEIGHT - 75);
      drawArrow(g, 550, HEIGHT - 75);

      // Draw the grass
      g.setStroke(new BasicStroke(10));
      g.setColor(new Color(12, 144, 36));
      for(int x = 0; x < WIDTH + 20; x+=15)
      {
        g.draw(new Line2D.Double(x, HEIGHT - 225, x - 10, HEIGHT - 153));
      }

      // Draw the tree
      final Rectangle TREE_TRUNK = new Rectangle(75, 200, 40, 300);
      g.setStroke(new BasicStroke(1));
      g.setColor(new Color(102, 51, 0));
      g.fill(TREE_TRUNK);
      g.setColor(Color.BLACK);
      g.draw(TREE_TRUNK);

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

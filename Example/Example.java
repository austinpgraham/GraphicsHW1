//******************************************************************************
// Last modified: Fri Jan 29 17:30:30 2016 by Chris Weaver
//******************************************************************************
//
// Example of experimenting with Java2D.
//
//******************************************************************************

//package graphics.foo;

// Only these imports are allowed in homework #1!
//import java.lang.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

//******************************************************************************

/**
 * The <CODE>Drawing</CODE> class. It draws stuff. Something fishy's going on.<P>
 *
 * @author  Chris Weaver
 * @version %I%, %G%
 */
public final class Example
{
	private static final Rectangle	BOUNDS = new Rectangle(50, 50, 400, 420);

	public static void	main(String[] argv)
	{
		JFrame	frame = new JFrame("Drawing");
		JPanel	panel = new Panel();

		frame.getContentPane().add(panel);
		frame.setVisible(true);
		frame.setBounds(BOUNDS);
		frame.validate();

		panel.revalidate();
		panel.repaint();
	}

	private static final class Panel extends JPanel
	{
		// For homework 1, only change what's in here. (You can add methods to
		// call from here if you want.) Document your code thoroughly! Graphics
		// code is usually easy to understand syntactically but hard to imagine
		// what will happen at each point when it runs.
		public void		paintComponent(Graphics graphics)
		{
			// Where to send drawing commands for rendering
			Graphics2D		g = (Graphics2D)graphics;
			Stroke			stroke = g.getStroke();	// Save to restore later

			// Note that Graphics2D remembers its settings (like the stroke)
			// and applies it to everything relevant until you change it again.
			// (By the way it's good practice to restore the settings to what
			// they were at the beginning.)

			String			s = "bait";	// The text to draw
			Color			fillColor = new Color(255, 255, 224);	// Cream
			Color			edgeColor = new Color(255, 128, 128);	// Pale red
			Color			textColor = new Color(128, 0, 0);		// Crimson

			// Apply this setting to draw lines thick and solid
			Stroke			thickStroke = new BasicStroke(3.0f);

			// Apply this setting to draw text large and bold
			Font			font = new Font("Serif", Font.BOLD, 36);

			// What are the font's measurements?
			FontMetrics		metrics = g.getFontMetrics(font);

			// Get minimal bounding box of s, as if it were drawn at the origin.
			Rectangle2D		rm = metrics.getStringBounds(s, g);
			double			x = rm.getX();
			double			y = rm.getY();
			double			w = rm.getWidth();
			double			h = rm.getHeight();

			// Shift the box to where we want to actually draw it relative to
			// the origin
			Rectangle2D.Double	r =
				new Rectangle2D.Double(x + 40.0, y + 30.0, w, h);

			g.setColor(fillColor);		// Switch to this color...
			g.fill(r);					// Fill the box

			g.setColor(edgeColor);		// Switch to that color...
			g.setStroke(thickStroke);	// Switch to the thick stroke...
			g.draw(r);					// Draw the box

			g.setColor(textColor);		// Switch to another color...
			g.setFont(font);			// Switch to the big, bold font...
			g.drawString(s, 40, 30);	// Draw the text, shifted with its box

			g.setColor(Color.black);	// Switch to black...

			// Draw a line from the origin to the shift point
			g.draw(new Line2D.Double(0.0, 0.0, 40.0, 30.0));

			// Draw a horizontal line from there across the width of the box
			// This shows the "baseline" of the text.
			g.draw(new Line2D.Double(40.0, 30.0, 40.0 + w, 30.0));

			// Draw a little more line plus an arc at the end
			g.draw(new Line2D.Double(40.0 + w, 30.0, 40.0 + w + 5.0, 30.0));
			g.draw(new Arc2D.Double(40.0 + w, 30.0 - 10.0, 10.0, 10.0,
									270.0, 135.0, Arc2D.OPEN));

			// Notice how the horizontal "anchor point" for drawing the string
			// is on the left side as expected, but that the vertical anchor
			// point is at the baseline of the text, not below the descending
			// parts of letters like 'p' and 'j'. It's a typesetting thing.

			// Define an oval and a rectangle that overlaps it.
			Ellipse2D.Double	outer = new Ellipse2D.Double(100, 100, 90, 70);
			Rectangle2D.Double	inner = new Rectangle2D.Double(160, 130, 40, 20);

			// You can create arbitrary areas by composing primitive shapes
			Area	area = new Area(outer);	// Start with the oval...

			area.subtract(new Area(inner));	// ...subtract the rect from it

			g.setColor(Color.BLUE);
			g.fill(area);				// Fill the area

			g.setColor(Color.GREEN);
			g.draw(area);				// Draw the area

			g.setColor(Color.WHITE);
			g.fill(new Ellipse2D.Double(145, 105, 15.0, 15.0));	// Fill the eye

			g.setColor(Color.BLACK);
			g.setStroke(stroke);		// Switch back to the default stroke
			g.draw(new Ellipse2D.Double(145, 105, 15.0, 15.0));	// Draw the eye

			g.setColor(Color.BLUE);		// Switch to blue...
			g.fill(new Ellipse2D.Double(148, 110, 7.0, 7.0));	// Fill the pupil

			// Let's define a path using two lines and a curve
			Path2D.Double		path = new Path2D.Double();

			path.moveTo(100, 135);				// Start here...
			path.lineTo(50, 165);				// ...straight line to here...
			path.quadTo(75, 135, 50, 105);		// ...curve to here...
			path.lineTo(100, 135);				// ...and back to the start.

			// Create a linear gradient that cycles through two colors
			GradientPaint	paint = new GradientPaint(100, 135, Color.GREEN,
													90, 135, Color.BLUE, true);

			//g.setColor(fillColor);	// Change back to this color? Nope!
			g.setPaint(paint);			// Use gradient instead of solid "paint"
			g.fill(path);				// Fill the interior of the path

			// Notice that Color implements the Paint interface!
			g.setPaint(edgeColor);		// Change to that color...
			g.draw(path);				// Draw the lines and curves of the path

			// Fi(ni)sh it off
			g.setColor(Color.WHITE);
			g.fill(new Ellipse2D.Double(170, 90, 7.0, 7.0));
			g.fill(new Ellipse2D.Double(175, 80, 7.0, 7.0));
			g.fill(new Ellipse2D.Double(165, 70, 7.0, 7.0));

			// Let's define another path for the waves
			Path2D.Double		wave = new Path2D.Double();

			wave.moveTo(0, 50);		// Start on the left side...

			for (int i=0; i<5; i++)	// ...add successive troughs to fill...
				wave.quadTo(i * 100 + 50, 75, i * 100 + 100, 50);

			g.setColor(Color.BLUE);
			g.draw(wave);			// Draw the waves

			wave.lineTo(400, 400);	// ...add the right side...
			wave.lineTo(0, 400);	// ...and the bottom...
			wave.lineTo(0, 50);		// ...and the left side to complete the water

			Color	water1 = new Color(96, 160, 255, 32);	// Light blue
			Color	water2 = new Color(0, 64, 255, 96);		// Darker blue

			// A gradient going from the top center to the bottom center
			// No cycling this time
			GradientPaint	paint2 =
				new GradientPaint(200, 0, water1, 200, 400, water2, false);

			g.setPaint(paint2);		// Change to use that paint
			g.fill(wave);			// Fill the water

			// (Hmmm...are there any other fancy paint classes?)

			// I wrote a basic utility function to make loading images easier
			Image	image = fullyLoadImage("seaweed.png");

			// Draw the image relative to the bottom using its height to calc y
			g.drawImage(image, 100, 400 - image.getHeight(this), null);

			// Look at all those numbers in the code. Yuck. Coordinate system
			// transforms would help a lot. More on those later in the course.

			// This function is also way too long and not very object oriented!
			// Good graphics code encapsulates the drawing commands for complex
			// objects into functions, then just performs a sequence of calls
			// to those function to assemble scenes. Sounds like a good plan
			// for getting a high score on this assignment.
		}

		// Use this to load images (and make sure they're done loading). The
		// filename must be relative to the directory containing this .java file.
		// Just put them in the same directory like I did with seaweed.png.
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

//******************************************************************************

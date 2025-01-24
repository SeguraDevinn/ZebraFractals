import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Random;
import java.util.Vector;

public class ZebraStripes {

    static Boolean left;
    static Boolean right;
    static Vector<Vector<Integer> > leftPts;
    static Vector<Vector<Integer> > rightPts;

    public static void main(String[] args) {
        //size of canvas
        int width = 1200;
        int height = 800;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        //background color
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(Color.BLACK);

        //this is to help get a truly random number for drawing the rectangles
        Long myNum = (long) (System.currentTimeMillis());
        Random myRandom = new Random(myNum);

        left = true;
        right = false;
        int offset = 50;

        leftPts = new Vector<Vector<Integer>>();
        rightPts = new Vector<Vector<Integer>>();

        //Start of Franchesco's
        for (int i = 0; i < width; i += 3 * offset) {


            double adjustAmount = 0.01875;

            double adjust1 = myRandom.nextDouble();
            adjust1 *= adjustAmount;
            adjust1 -= adjustAmount / 2.0;

            double adjust2 = myRandom.nextDouble();
            adjust2 *= adjustAmount;
            adjust2 -= adjustAmount / 2.0;

            double adjust3 = myRandom.nextDouble();
            adjust3 *= adjustAmount;
            adjust3 -= adjustAmount / 2.0;

            double adjust4 = myRandom.nextDouble();
            adjust4 *= adjustAmount;
            adjust4 -= adjustAmount / 2.0;

            // Draw the first arc (left arc - down)
            drawFractalArc(g2d, i, 0, i + offset / 2, 700, 7, 0.05 + adjust1, left);

            // Draw the first arc (right arc - down)
            drawFractalArc(g2d, i + offset, 0, i + offset / 2, 700, 7, 0.05 + adjust2, right);

            for (int j = 0; j < leftPts.size(); j++) {
                int x1 = leftPts.elementAt(j).elementAt(0);
                int y1 = leftPts.elementAt(j).elementAt(1);
                int x2 = leftPts.elementAt(j).elementAt(0);
                int y2 = leftPts.elementAt(j).elementAt(1);
                g2d.drawLine(x1, y1, x2, y2);

                x1 = leftPts.elementAt(j).elementAt(2);
                y1 = leftPts.elementAt(j).elementAt(3);
                x2 = leftPts.elementAt(j).elementAt(2);
                y2 = leftPts.elementAt(j).elementAt(3);
                g2d.drawLine(x1, y1, x2, y2);


            }


            //Start of Josh


            // Draw the third arc (left arc - up)
            drawFractalArc(g2d, i + offset, 800, i + 5 * offset / 2, 100, 7, -0.05 + adjust3, left);

            // Draw the fourth arc (right arc - up)
            drawFractalArc(g2d, i + 2 * offset, 800, i + 5 * offset / 2, 100, 7, -0.05 + adjust4, right);
        }

        for (int j = 0; j < leftPts.size(); j++) {
            int x1 = leftPts.elementAt(j).elementAt(0);
            int y1 = leftPts.elementAt(j).elementAt(1);
            int x2 = rightPts.elementAt(j).elementAt(0);
            int y2 = rightPts.elementAt(j).elementAt(1);
            g2d.drawLine(x1, y1, x2, y2);

            x1 = leftPts.elementAt(j).elementAt(2);
            y1 = leftPts.elementAt(j).elementAt(3);
            x2 = rightPts.elementAt(j).elementAt(2);
            y2 = rightPts.elementAt(j).elementAt(3);
            g2d.drawLine(x1, y1, x2, y2);
        }


        //Save image to PNG
        try {
            ImageIO.write(image, "png", new File("ZebraStripes.png"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);

        }
    }
    ////////////////////////////
    ///Function that creates the fractal arcs
    ////////////////////////////
    public static void drawFractalArc (Graphics2D g2d, int x1, int y1, int x2, int y2, int depth,
                                       double curvature, boolean leftSide) {
        if (depth == 0) {
            g2d.drawLine(x1, y1, x2, y2);
            if (leftSide) {
                //Adds all the points to the left side else to the right
                Vector<Integer> pts = new Vector<Integer>();

                pts.addElement(x1);
                pts.addElement(y1);
                pts.addElement(x2);
                pts.addElement(y2);

                leftPts.addElement(pts);

            } else {

                Vector<Integer> pts = new Vector<Integer>();

                pts.addElement(x1);
                pts.addElement(y2);
                pts.addElement(x2);
                pts.addElement(y2);

                rightPts.addElement(pts);
            }
        }
        else
        {
            // Franchesco pt2
            // Less overall curvature, only slight offset
            int midX = (x1 + x2) / 2 + (int) ((y2 - y1) * curvature);
            int midY = (y1 + y2) / 2 + (int) ((x1 - x2) * curvature);

            // Recursively draw the two halves
            drawFractalArc(g2d, x1, y1, midX, midY, depth -1, curvature, leftSide);
            drawFractalArc(g2d, midX, midY, x2, y2, depth -1, curvature, leftSide);
        }
    }

}



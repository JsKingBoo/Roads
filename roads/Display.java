package roads;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.*;

public class Display {
    
    public static final int WIDTH = 768; //16 x 9 ratio; 48 by 27 tiles
    public static final int HEIGHT = 432; //w x h
    
    //YEAH YEAH YEAH DON'T USE CONSTANTS I DON'T CARE
    public static final int WIDTH_TILE = WIDTH / 16; //16 x 9 ratio; 48 by 27 tiles
    public static final int HEIGHT_TILE = HEIGHT / 16; //w x h
    
    public static final int selectorOffset = -8;
    
    //0, 0 upon initial spawning
    //measured in tiles
    int offX;
    int offY;
    
    //location of the starting selector position
    int selectedX;
    int selectedY;
    
    JFrame frame;
    JLabel theeverything;
    
    BufferedImage background;
    BufferedImage selector;
    Roads roadHandler = new Roads();
    BufferedImage roads;
    
    BufferedImage everythingHolder;
    
    public Display() throws IOException {
        
        offX = 0;
        offY = 0;
        selectedX = 0;
        selectedY = 0;
        
        TileSheet sheet = new TileSheet();
        int halfTile = sheet.getTileWidth() / 2;
        background = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        //Create background
        Graphics2D g2 = background.createGraphics();
        //draw background image
        for (int i = -halfTile; i <= WIDTH; i += sheet.getTileWidth()){
            for (int j = 0; j < HEIGHT; j += sheet.getTileWidth()){
                g2.drawImage(sheet.getImage(-1), null, i, j);
            }
        }

        g2.dispose();

        //temp debug do not uncomment
        //ImageIO.write(background, "png", new File("C:\\temp\\test.png"));
        
        //creates selector
        
        selector = sheet.getImage(2, 0, 1, 1);
        
        everythingHolder = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        
        Graphics2D holder = everythingHolder.createGraphics();
        holder.drawImage(background, null, 0, 0);
        holder.drawImage(selector, null, WIDTH / 2, HEIGHT / 2 + selectorOffset);
        
        holder.dispose();
        
        //handles JFrame and its components
        frame = new JFrame("Roads");

        //adds the everything
        theeverything = new JLabel(new ImageIcon(everythingHolder));
        frame.getContentPane().add(theeverything);
        
        //arrow keys
        frame.addKeyListener(new KeyListener() {
            
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch( keyCode ) { 
                    case KeyEvent.VK_UP:
                        //System.out.println("up");
                        moveScreen(0, -1);
                        break;
                    case KeyEvent.VK_DOWN:
                        //System.out.println("down");
                        moveScreen(0, 1);
                        break;
                    case KeyEvent.VK_LEFT:
                        //System.out.println("left");
                        moveScreen(-1, 0);
                        break;
                    case KeyEvent.VK_RIGHT :
                        //System.out.println("right");
                        moveScreen(1, 0);
                        break;
                    case KeyEvent.VK_SPACE :
                        //System.out.println("space");
                        toggleRoad();
                        break;
                }
            }             

            //these are only here to stop netbeans from screeching
            @Override
            public void keyTyped(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyReleased(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        
        //ready for display
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocation(300, 300);
        frame.setVisible(true);
      
    }
    
    public void repaint(){
        
        TileSheet sheet = new TileSheet();
        
        everythingHolder = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        
        Graphics2D holder = everythingHolder.createGraphics();
        holder.drawImage(background, null, 0, 0);

        if (roadHandler == null) {
            //yes, this was a legitimate error I was getting while debugging...
            System.out.println("LOLOL");
        }
        /*
        System.out.println("offX: " + offX);
        System.out.println("offY: " + offY);
        
        System.out.println("selectedX: " + selectedX);
        System.out.println("selectedY: " + selectedY);
        */
        if (roadHandler.getBlocks().size() > 0) {
        
            //dear netbeans: no I will not use "functional operands", they are confusing as 
            //roadHandler.getBlocks().stream().filter((road) -> (road.getX() >= selectedX - WIDTH_TILE / 2 && road.getX() <= selectedX + WIDTH_TILE / 2)).filter((road) -> (road.getY() >= selectedY - WIDTH_TILE / 2 && road.getY() <= selectedY + HEIGHT_TILE / 2)).forEach((road) -> {
            for (RoadBlock road : roadHandler.getBlocks()){
                //check if the roadblock lies within the screen
                /*
                System.out.println("road.getX(): " + road.getX());
                System.out.println("road.getY(): " + road.getY());
                */
                if (road.getX() >= selectedX - WIDTH_TILE / 2 && road.getX() <= selectedX + WIDTH_TILE / 2){
                    if (road.getY() >= selectedY - WIDTH_TILE / 2 && road.getY() <= selectedY + HEIGHT_TILE / 2){
                        //draw it
                        BufferedImage roadblock = sheet.getImage(road.getID());
                        //BufferedImage roadblock = sheet.getImage(5, 4, 1, 1);
                        
                        holder.drawImage(roadblock, null, 
                                (road.getX() - selectedX + (WIDTH_TILE / 2)) * sheet.getTileWidth(), 
                                (road.getY() - selectedY + (HEIGHT_TILE / 2)) * sheet.getTileWidth()); 
                        //System.out.println("i maked it");
                    }
                }
            }
        
        }
    
        //selector goes over the blocks
        holder.drawImage(selector, null, WIDTH / 2, HEIGHT / 2 + selectorOffset);
        holder.dispose();
        
        theeverything.setIcon(new ImageIcon(everythingHolder));
        theeverything.repaint();
        
    }
    
    public void moveScreen(int xmove, int ymove){
        offX += xmove;
        offY += ymove;
        selectedX += xmove;
        selectedY += ymove;
        repaint();
    }
    
    public void toggleRoad(){
        
        //if it exists, remove it
        for (RoadBlock road : roadHandler.getBlocks()){
            if (road.getX() == selectedX && road.getY() == selectedY){
                //System.out.println("removed id = " + road.getID());                
                roadHandler.removeRoad(road);
                roadHandler.updateBlock(selectedX - 1, selectedY);
                roadHandler.updateBlock(selectedX + 1, selectedY);
                roadHandler.updateBlock(selectedX, selectedY - 1);
                roadHandler.updateBlock(selectedX, selectedY + 1);
                repaint();
                return;
            }
        }
        //otherwise create it
        roadHandler.createRoad(selectedX, selectedY);
        roadHandler.updateBlock(selectedX, selectedY);
        roadHandler.updateBlock(selectedX - 1, selectedY);
        roadHandler.updateBlock(selectedX + 1, selectedY);
        roadHandler.updateBlock(selectedX, selectedY - 1);
        roadHandler.updateBlock(selectedX, selectedY + 1);   
        //System.out.println("created");                     
        
        repaint();
    }

    /**
     * 
     * @param args 
     * @throws java.io.IOException 
     */
    public static void main(String args[]) throws IOException {
        Display d = new Display();   
    }
    
}

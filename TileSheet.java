package roads;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

/**
 * @author is MIA
 * 
 * Tile sheet handler
 */
public class TileSheet {
    //in pixels
    private static final int SPRITE_SHEET_WIDTH = 96;
    private static final int SPRITE_SHEET_HEIGHT = 80;
    private static final int TILE_WIDTH = 16;
    //How many tiles are there?
    //Currently 5x6 (hxw)
    //I MEAN ITS NOT LIKE I USE THESE VARIABLES RIGHT?
    //(thanks netbeans for telling me...jerk :()
    private static final int SPRITE_SHEET_TILE_WIDTH = SPRITE_SHEET_WIDTH / TILE_WIDTH;
    private static final int SPRITE_SHEET_TILE_HEIGHT = SPRITE_SHEET_HEIGHT / TILE_WIDTH;
    /*
     * Tile sheet; get all subimages from this master image
    */
    private BufferedImage tileSheet = null;
    
    /**
     *  Default constructor class for RenderBlocks
     */
    public TileSheet() {       
        //Finds the tile sheet and loads it in
        InputStream tileSheetStream = getClass().getResourceAsStream("/resources/roads.png");
        if (tileSheetStream != null){
            try {
                tileSheet = ImageIO.read(tileSheetStream);
            }
            catch (IOException e) {
                System.out.println("Could not import textures");
                System.out.println(e);
            }
        }
    }
    
    /**
     * Returns the tile width
     * 
     * @return Tile width 
     */
    public int getTileWidth(){
        return TILE_WIDTH;
    }    
    
    /**
     * Returns a tile image from the sprite sheet
     * 
     * @param x        X coordinate from the left of the sprite sheet counted by
     *                 tiles
     * @param y        Y coordinate from the top of the sprite sheet, counted by 
     *                 tiles
     * @param width    Width in tiles
     * @param height   Height in tiles
     * @return         requested sprite
     */
    public BufferedImage getImage(int x, int y, int width, int height){
        return tileSheet.getSubimage(x*TILE_WIDTH,y*TILE_WIDTH,width*TILE_WIDTH,height*TILE_WIDTH);
    } 
    
    /**
     * Returns a tile image from the sprite sheet via ID
     * 
     * @param id       id of tile
     * @return 
     */
    public BufferedImage getImage(int id){
        //all 4 connect
        if (id >= 40){
            return getImage(0, 4, 1, 1);
        }
        /*
        3 connect
        
        30 = T points left
        31 = T points up
        32 = T points right
        33 = T points down
        */
        else if (id >= 30){
            return getImage(id - 30, 3, 1, 1);
        }
        /*
        2 connect
        
        20 = Lower left bend (left to down)
        21 = Upper left bend (left to up)
        22 = Upper right bend (right to up)
        23 = Lower right bend (right to down)
        24 = Vertical road
        25 = Horizontal road
        */
        else if (id >= 20){
            return getImage(id - 20, 2, 1, 1);
        }
        /*
        1 connect
        
        10 = Left
        11 = Up
        12 = Right
        13 = Down
        */
        else if (id >= 10){
            return getImage(id - 10, 1, 1, 1);
        }
        //no joining segments
        else if (id == 0){
            return getImage(0, 0, 1, 1);
        }
        //grass
        else {
            return getImage(1, 0, 1, 1);
        }
    }
    
    /**
     * Prints out a tile image from the sprite sheet, mainly for debugging 
     * purposes
     * 
     * @param x        X coordinate from the left of the sprite sheet counted by
     *                 tiles
     * @param y        Y coordinate from the top of the sprite sheet, coutned by
     *                 tiles
     * @param width    Width in tiles
     * @param height   Height in tiles
     */
    public void printImage(int x, int y, int width, int height){
        try {
            File f = new File("C:\\temp\\test.png");
            ImageIO.write(this.getImage(0, 0, 1, 1), "png", f);
        } catch (IOException e) {
            System.out.println(e);
            System.out.println("How did you even get this message?!");
        }          
    }
    
}

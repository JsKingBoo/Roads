package roads;

/**
 *
 * @author Boo! I scare you!
 */
public class RoadBlock {
    
    private static final int TILE_WIDTH = 16;    
    private int currentTile; //grass
    private int x;
    private int y;
    
    /**
     *  Default constructor class for RenderBlocks
     */
    public RoadBlock(){
        currentTile = -1;
        x = 0;
        y = 0;               
    }
    
    /**
     *  Constructor class for RenderBlocks
     * 
     *  @param id The currentTile id
     *  @param xa Current X coordinate. Can be negative
     *  @param ya Current Y coordinate. Can be negative
     */    
    public RoadBlock(int id, int xa, int ya){
        currentTile = id;
        x = xa;
        y = ya;
    }
    
    public boolean isRoad(){
        return currentTile >= 0;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public void setX(int ax){
        x = ax;
    }
    
    public void setY(int ay){
        y = ay;
    }

    public void setID(int id){
        currentTile = id;
    }
    
    public int getID(){
        return currentTile;
    }
    
    /**
     * For convenience's sake only 
     *
     * @param block Check if this block is above the caller
     * @return idk lol
     */
    public boolean isAbove(RoadBlock block){
        if (RoadBlock.getDistance(this, block) == 1){
            return (this.y - block.getY() == 1);
        }
        return false;
    }
    
    public boolean isBelow(RoadBlock block){
        if (RoadBlock.getDistance(this, block) == 1){
            return (this.y - block.getY() == -1);
        }
        return false;
    }
    
    public boolean isLeft(RoadBlock block){
        if (RoadBlock.getDistance(this, block) == 1){
            return (this.x - block.getX() == 1);
        }
        return false;
    }
    
    public boolean isRight(RoadBlock block){
        if (RoadBlock.getDistance(this, block) == 1){
            return (this.x - block.getX() == -1);
        }
        return false;
    }
    
    public static double getDistance(RoadBlock b1, RoadBlock b2){
        return Math.sqrt(Math.pow(b1.getX() - b2.getX(), 2) + Math.pow(b1.getY() - b2.getY(), 2));
    }
    
    /**
     * Updates the current tile
     * 
     * @param l Left tile is a road
     * @param u Upper tile is a road
     * @param r Right tile is a road
     * @param d Downward tile is a road
     * 
     * @return true if successful completion, false otherwise
     */
    public boolean updateTile(boolean l, boolean u, boolean r, boolean d){
        //LET'S BRUTE FORCE THIS :D
        int count = 0;
        
        if (l){
            count++;
        }
        if (u){
            count++;
        }
        if (r){
            count++;
        }
        if (d){
            count++;
        }        
        
        /*
        4 connect
            40
        
        3 connect
            30 = T points left
            31 = T points up
            32 = T points right
            33 = T points down
        
        2 connect
            20 = Lower left bend (left to down)
            21 = Upper left bend (left to up)
            22 = Upper right bend (right to up)
            23 = Lower right bend (right to down)
            24 = Vertical road
            25 = Horizontal road

        1 connect
            10 = Left
            11 = Up
            12 = Right
            13 = Down
        
        0 connect
            0
        */
        
        if (count == 4){
            currentTile = 40;
        }
        if (count == 3){
            if (!l){
                currentTile = 32;
            } else if (!u){
                currentTile = 33;
            } else if (!r){
                currentTile = 30;
            } else if (!d){
                currentTile = 31;
            }
        }
        if (count == 2){
            if (l && d){
                currentTile = 20;
            } else if (l && u){
                currentTile = 21;
            } else if (r && u){
                currentTile = 22;
            } else if (r && d){
                currentTile = 23;
            } else if (u && d){
                currentTile = 24;
            } else if (l && r){
                currentTile = 25;
            }
        }
        if (count == 1){
            if (l){
                currentTile = 10;
            } else if (u){
                currentTile = 11;
            } else if (r){
                currentTile = 12;
            } else if (d){
                currentTile = 13;
            }
        }
        if (count == 0){
            currentTile = 0;
        }

        return true;
    }
    
}

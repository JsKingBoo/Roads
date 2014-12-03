package roads;

import java.util.ArrayList;

/**
 * @author A spooky ghost
 */
public class Roads {

    private ArrayList<RoadBlock> roadTiles = new ArrayList<>();
    
    public Roads(){
        //LOLOLOLOL
    }
    
    public void createRoad(int x, int y){
        RoadBlock block = new RoadBlock();
        block.setX(x);
        block.setY(y);
        roadTiles.add(block);
        System.out.println("created");
    }
    
    public void addRoad(RoadBlock block){
        roadTiles.add(block);
    }
    
    public boolean removeRoad(int x, int y){
        for (RoadBlock road : roadTiles){
            if (road.getX() == x && road.getY() == y){
                roadTiles.remove(road);
                return true;
            }
        }
        return false;
    }
    
    public boolean removeRoad(RoadBlock road){
        return roadTiles.remove(road);
    }
    
    public void updateBlock(RoadBlock block){
        boolean left = false;
        boolean up = false;
        boolean right = false;
        boolean down = false;
        
        for (RoadBlock roads : roadTiles){
            if (block.isAbove(roads)){
                up = true;
            }
            if (block.isBelow(roads)){
                down = true;
            }
            if (block.isLeft(roads)){
                left = true;
            }
            if (block.isRight(roads)){
                right = true;
            }
        }
        block.updateTile(left, up, right, down);
    }
    
    public void updateBlock(int x, int y){
        boolean left = false;
        boolean up = false;
        boolean right = false;
        boolean down = false;
        
        boolean found = false;
        RoadBlock block = new RoadBlock();
        
        //check if block is in the array
        for (RoadBlock roads1 : roadTiles){
            if (roads1.getX() == x && roads1.getY() == y){
                //found the block, copy and remove from original list
                block.setX(x);
                block.setY(y);
                found = true;
                roadTiles.remove(roads1);
                break;
            }
        } 
        //if not found, gtfo
        //System.out.println(found);
        if (!found){
            return;
        }
        
        //now update
        for (RoadBlock roads : roadTiles){
            if (block.isAbove(roads)){
                up = true;
            }
            if (block.isBelow(roads)){
                down = true;
            }
            if (block.isLeft(roads)){
                left = true;
            }
            if (block.isRight(roads)){
                right = true;
            }
        }
        //readd to array
        block.updateTile(left, up, right, down);
        roadTiles.add(block);
    }    
    
    public ArrayList<RoadBlock> getBlocks(){
        return roadTiles;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code applicati on logic here
    }
    
}

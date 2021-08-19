package Util;
public class Hash{

    private Block Block;
    
    public Hash(Block block){
        this.Block = block;
    }

    public int hash(){
        System.out.println(Block.hash);
        return Block.hash + 1;
    }
}
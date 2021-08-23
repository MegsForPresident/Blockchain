package Util;

import Util.Errors.GenesisBlockError;

public class Block {
    public final String data;
    public final String date;
    protected final String Phash;
    public final String hash;
    private Block Pblock;
    public Block(String data,String date,String pHash,Block block) throws Util.Errors.GenesisBlockError{
        this.data = data;
        this.date = date;
        this.Phash = pHash;
        this.Pblock = block;
        
        this.hash = new Hash(new Transformer(data,date)).hash();
        check();
    }
    public void print(){//TODO Delete after completion
        System.out.println(date+" " + data +" " +  hash+" " +  Phash);
    }
    private void check() throws GenesisBlockError{
        if(this.Pblock == null){
            if(this.Phash != ""){
                throw new GenesisBlockError("ID and Block both have to empty and Null respectively");
            }
        }
        else if (this.Phash != this.Pblock.hash){
            throw new GenesisBlockError("BlockChain has been Compromised ");            
        }
    }
}

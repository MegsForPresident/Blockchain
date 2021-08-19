package Util;

import Util.Errors.GenesisBlockError;

public class Block {
    public final String data;
    public final String date;
    protected final int Phash;
    public final int hash;
    private Block Pblock;
    public Block(String data,String date,int pHash,Block block) throws Util.Errors.GenesisBlockError{
        this.data = data;
        this.date = date;
        this.Phash = pHash;
        this.Pblock = block;
        if(Pblock != null){
            System.out.println("yes "+Pblock);
            this.hash = new Hash(this.Pblock).hash();
        }
        else{
            System.out.println(Pblock);
            this.hash = 1;
        }
        check();
    }
    public void print(){//TODO Delete after completion
        System.out.println(date+" " + data +" " +  hash+" " +  Phash);
    }
    private void check() throws GenesisBlockError{
        if(this.Pblock == null){
            if(this.Phash != 0){
                throw new GenesisBlockError("ID and Block both have to 0 and Null respectively");
            }
        }
        else if (this.Phash != this.Pblock.hash){
            throw new GenesisBlockError("BlockChain has been Compromised");            
        }
    }
}

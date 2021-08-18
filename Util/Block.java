package Util;

public class Block {
    public final String data;
    public final String date;
    protected final int Phash;
    public final int hash;
    private Block Pblock;
    public Block(String data,String date,int pHash,Block block) throws GenesisBlockError{
        this.data = data;
        this.date = date;
        this.Phash = pHash;
        this.hash = new Hash(block).hash();
        this.Pblock = block;
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

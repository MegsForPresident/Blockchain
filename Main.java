import java.util.*;

import Util.Block;
import Util.GenesisBlockError;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    // TODO Make it to GUI and load the blockchain line which was asked by user and needs a passwords to end which was inputed at the beginning
    public static void main(String[] args) throws GenesisBlockError {
        LinkedList<Block> blockchain = new LinkedList<>();
        HashMap<String,Block>blockchains = new HashMap<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"); 
        String step = "";
        Block pblock = null;
        while(!step.contentEquals("end chain")){
            if(step.contentEquals("new input")){
                String key = ask("Name for Blockchain: ") + " " +ask("Set the password for "+":");
                String data = ask("Data: ");
                LocalDateTime now = LocalDateTime.now();  
                String date = dtf.format(now);
                Block block;
                if (blockchain.size() == 0){
                    block = new Block(data,date,0,null);
                    pblock = block;
                }
                else{
                    block = new Block(data,date,pblock.hash,pblock);
                    pblock = block;
                }
                block.print();
                blockchains.put(key,block);
            }
            // else if(step.contentEquals("load chain")){
            //     String enterPassWord = ask("What is the password for "+":");
            // }
            step = ask("").toLowerCase().trim();
        }
        for(Block block : blockchain){
            block.print();
        }
        scanner.close();
        System.out.println(blockchains);
    }
    private static String ask(String prompt){
        System.out.print(prompt);
        return scanner.nextLine();
    }
}

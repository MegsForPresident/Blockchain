import java.util.*;

import Util.Block;
import Util.Errors.GenesisBlockError;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    // TODO Make it to GUI and load the blockchain line which was asked by user and needs a passwords to end which was inputed at the beginning
    public static void main(String[] args) throws GenesisBlockError {
        HashMap<String,LinkedList<Block>>blockchains = new HashMap<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"); 
        String step = "";
        while(!step.contentEquals("end chain")){
            if(step.contentEquals("new input")){
                LinkedList<Block> blockchain = new LinkedList<>();
                String name = ask("Name of blockchain:> ");
                String data = ask("Data:> ");
                LocalDateTime now = LocalDateTime.now();  
                String date = dtf.format(now);
                Block block = new Block(data, date, 0, null);
                block.print();
                blockchain.add(block);
                blockchains.put(name.toLowerCase(),blockchain);
            }
            else if(step.contains("add block to chain ")){
                step = step.replace("add block to chain ","");
                System.out.println(step);
                String data = ask("Data:> ");
                LocalDateTime now = LocalDateTime.now();  
                String date = dtf.format(now);
                System.out.println(blockchains.get(step));
                Block pblock = blockchains.get(step).getLast();
                Block block = new Block(data,date,pblock.hash,pblock);
                blockchains.get(step).add(block);
            }
            step = ask("Prompt:> ").toLowerCase().trim();
            System.out.println(blockchains);
        }
        scanner.close();
        
        for(String key : blockchains.keySet()){
            for(Block block : blockchains.get(key)){
                block.print();
            }
        }
    }
    private static String ask(String prompt){
        System.out.print(prompt);
        return scanner.nextLine();
    }
}

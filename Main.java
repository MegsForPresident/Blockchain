import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

import Util.Block;
import Util.Errors.GenesisBlockError;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    // TODO Make it to GUI and load the blockchain line which was asked by user and needs a passwords to end which was inputed at the beginning
    public static void main(String[] args) throws GenesisBlockError, IOException {
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
            else if(step.contains("add block >> ")){
                step = step.replace("add block >> ","");
                String data = ask("Data:> ");

                LocalDateTime now = LocalDateTime.now();  
                String date = dtf.format(now);

                System.out.println(blockchains.get(step).getLast());
                
                Block pblock = blockchains.get(step).getLast();
                Block block = new Block(data,date,pblock.hash,pblock);
                
                blockchains.get(step).add(block);
            }
            else if(step.contains("save chain ")){
                step.replace("save chain \"", "");
                save(blockchains.get(step),step);
            }
            step = ask("Prompt:> ").toLowerCase().trim();
            System.out.println(blockchains);// TODO Delete after completion
        }
        scanner.close();
        
        for(String key : blockchains.keySet()){
            for(Block block : blockchains.get(key)){
                block.print();
            }
        }
    }
    private static void save(LinkedList<Block> linkedList, String name) throws IOException {
        //TODO Save
        File file = new File(name+".txt");
        file.createNewFile();
        FileWriter writer = new FileWriter(file);
        for(Block block : linkedList){
            writer.write(
                "Data : " + block.data+"\nDate : " + block.date +  "\nHash : " + block.hash + "\n\n"
            );
        }
    }
    private static String ask(String prompt){
        System.out.print(prompt);
        return scanner.nextLine();
    }
}

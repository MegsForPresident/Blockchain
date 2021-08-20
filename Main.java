import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
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
        while(!step.contentEquals("end session")){
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
                step = step.replace("save chain ", "");
                save(blockchains.get(step),step);
            }
            else if(step.contains("load ")){
                step = step.replace("load ","");
                blockchains.put(step,load(step));
                System.out.println(blockchains);
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
    private static LinkedList<Block> load(String step) throws FileNotFoundException, GenesisBlockError {
        LinkedList<Block> blocks = new LinkedList<>();
        File file = new File(step + ".txt");
        String[] content = getContent(file).split("SplitFromHere");
        String []data = content[0].split("SplitfromHere2"); 
        System.out.println(Arrays.toString(content));
        Block block = new Block(data[0],data[1],0,null);
        blocks.add(block);
        for(int i = 1;i < content.length;i++){
            data = content[1].split("SplitfromHere2"); 
            block = new Block(data[0],data[1],Integer.parseInt(data[2]),blocks.get(i-1));
            blocks.add(block);
        }
        //Block block = new Block()
        return blocks;
    }
    private static String getContent(File file) throws FileNotFoundException {
        String data = "";
        Scanner scan = new Scanner(file);
        while(scan.hasNextLine()){
            data += scan.nextLine();
        }
        scan.close();
        return data;
    }
    private static void save(LinkedList<Block> linkedList, String name) throws IOException {
        //TODO Save
        File file = new File(name+".txt");
        file.createNewFile();
        FileWriter writer = new FileWriter(file);
        System.out.println(linkedList);
        for(Block block : linkedList){
            writer.write(
                block.data+"SplitfromHere2" + block.date +  "SplitfromHere2" + block.hash + "SplitFromHere"
            );
        }
        writer.close();
    }
    private static String ask(String prompt){
        System.out.print(prompt);
        return scanner.nextLine();
    }
}

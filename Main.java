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
    private static String username;
    public static void main(String[] args) throws GenesisBlockError, IOException {
        username =  getUserName();
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

                Block block = new Block(data, date, "", null);

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
            System.out.println(blockchains);
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
        File file = new File("C:\\Users\\"+username+"\\Blocks\\"+step + ".txt");
        String contentt = getContent(file);
        System.out.println(contentt);
        String[] content = contentt.split("--Content--Split--");
        String []data = content[0].split("--data--spilt--"); 
        System.out.println(Arrays.toString(content) + " " + Arrays.toString(data));
        Block block = new Block(data[0],data[1],"",null);
        blocks.add(block);
        System.out.println(blocks);
        for(int i = 1;i < content.length;i++){
            data = content[1].split("--data--spilt--"); 
            System.out.println(data[0]+" "+data[1]+" "+Integer.parseInt(data[2])+" "+blocks.get(i-1));
            block = new Block(data[0],data[1],data[2],blocks.get(i-1));
            blocks.add(block);
        }
        return blocks;
    }
    private static String getUserName() throws FileNotFoundException {
        if(new File("username.txt").exists()){
            return content("username.txt");
        }
        File file = new File("C:\\Users");
        String[]a = file.list();
        for(String g : a){
            if(!(g.contentEquals("Default") || g.contentEquals("Public"))){
                return g;
            }
        }
        return "Public";
    }
    private static String content(String string) throws FileNotFoundException {
        Scanner scan = new Scanner(new File(string));
        return scan.nextLine();
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
        File file = new File("C:\\Users\\"+username+"\\Blocks\\"+name+".txt");
        file.createNewFile();
        FileWriter writer = new FileWriter(file);
        System.out.println(linkedList);
        for(Block block : linkedList){
            writer.write(
                block.data+"--data--spilt--" + block.date +  "--data--spilt--" + block.hash + "--Content--Split--"
            );
        }
        writer.close();
        file = new File("username.txt");
        writer = new FileWriter(file);
        writer.write(username);
        writer.close();
    }
    private static String ask(String prompt){
        System.out.print(prompt);
        return scanner.nextLine();
    }
}

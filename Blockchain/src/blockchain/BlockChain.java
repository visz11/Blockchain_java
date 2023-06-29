package blockchain;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.nio.file.*;
import com.google.gson.GsonBuilder;

class ReadTextAsString {

    public static String readFileAsString(String fileName)throws Exception
    {
        String data = "";
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;
    }
}

public class BlockChain {

    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static int difficulty = 5;

    public static void main(String[] args) throws Exception{

        int[][] graph1 = new int[][] {{0, 4, 0, 0},
                {4, 0, 8, 0},
                {0, 8, 0, 7},
                {0, 0, 7, 0}} ;

        int graph2[][] = new int[][] {{0, 2, 0, 3},
                {2, 0, 1, 2},
                {0, 1, 0, 0},
                {3, 2, 0, 0}} ;

        int graph3[][] = new int[][] {{0, 4, 0, 0},
                {4, 0, 8, 0},
                {0, 8, 0, 7},
                {0, 0, 7, 0}} ;

        System.out.println("Trying to Mine block 1... ");
        addBlock(new Block(graph1, "0"));

        System.out.println("Trying to Mine block 2... ");
        addBlock(new Block(graph2,blockchain.get(blockchain.size()-1).hash));

        System.out.println("Trying to Mine block 3... ");
        addBlock(new Block(graph3,blockchain.get(blockchain.size()-1).hash));

        System.out.println("\nBlockchain is Valid: " + isChainValid());

        String blockchainJson = StringUtil.getJson(blockchain);
        System.out.println("\nThe block chain: ");
        System.out.println(blockchainJson);
        SwitchCase();

    }

    public static void SwitchCase(){
        int[][] graph1 = new int[][] {{0, 4, 0, 0},
                {4, 0, 8, 0},
                {0, 8, 0, 7},
                {0, 0, 7, 0}} ;

        int graph2[][] = new int[][] {{0, 2, 0, 3},
                {2, 0, 1, 2},
                {0, 1, 0, 0},
                {3, 2, 0, 0}} ;

        int graph3[][] = new int[][] {{0, 4, 0, 0},
                {4, 0, 8, 0},
                {0, 8, 0, 7},
                {0, 0, 7, 0}} ;
        System.out.println("Choose the algorithm:");
        System.out.println("1)Djikstra's Algorithm\n2)Prims Algorithm\n3)Kruskal's Algorithm");
        System.out.println("Enter your choice-");
        Scanner sc = new Scanner(System.in);
        int var = sc.nextInt();
        switch (var){
            case 1:
                System.out.println("Using Djikstra's Algorithm");
                System.out.println("Select block:");
                int x = sc.nextInt();
                Djikstra t = new Djikstra();
                switch (x){
                    case 1:
                        System.out.println("In Block 1");
                        t.dijkstra(graph1, 0);
                        break;
                    case 2:
                        System.out.println("In Block 2");
                        t.dijkstra(graph2, 0);
                        break;
                    case 3:
                        System.out.println("In Block 3");
                        t.dijkstra(graph3, 0);
                        break;
                }
                break;

            case 2:
                System.out.println("Using Prims Algorithm");
                System.out.println("Select block:");
                int y = sc.nextInt();
                Prims p = new Prims();
                switch (y) {
                    case 1:
                        System.out.println("In Block 1");
                        p.primMST(graph1);
                        break;
                    case 2:
                        System.out.println("In Block 2");
                        p.primMST(graph2);
                        break;
                    case 3:
                        System.out.println("In Block 3");
                        p.primMST(graph3);
                        break;
                }
                break;

            case 3:
                int[][] graph11 = new int[4][4];
                int[][] graph22 = new int[4][4];
                int[][] graph33 = new int[4][4];
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        if(graph1[i][j]==0)
                            graph11[i][j] = Integer.MAX_VALUE;
                        else graph11[i][j] = graph1[i][j];
                        if(graph2[i][j]==0)
                            graph22[i][j] = Integer.MAX_VALUE;
                        else graph22[i][j] = graph2[i][j];
                        if(graph3[i][j]==0)
                            graph33[i][j] = Integer.MAX_VALUE;
                        else graph33[i][j] = graph3[i][j];
                    }
                }
                System.out.println("Using Kruskal's Algorithm");
                System.out.println("Select block:");
                int z = sc.nextInt();
                Kruskal k = new Kruskal();
                switch (z) {
                    case 1:
                        System.out.println("In Block 1");
                        k.kruskalMST(graph11);
                        break;
                    case 2:
                        System.out.println("In Block 2");
                        k.kruskalMST(graph22);
                        break;
                    case 3:
                        System.out.println("In Block 3");
                        k.kruskalMST(graph33);
                        break;
                }
                break;
        }

    }

    public static Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        //loop through blockchain to check hashes:
        for(int i=1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i-1);
            //compare registered hash and calculated hash:
            if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
                System.out.println("Current Hashes not equal");
                return false;
            }
            //compare previous hash and registered previous hash
            if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
                System.out.println("Previous Hashes not equal");
                return false;
            }
            //check if hash is solved
            if(!currentBlock.hash.substring( 0, difficulty).equals(hashTarget)) {
                System.out.println("This block hasn't been mined");
                return false;
            }

        }
        return true;
    }

    public static void addBlock(Block newBlock) {
        newBlock.mineBlock(difficulty);
        blockchain.add(newBlock);
    }
}
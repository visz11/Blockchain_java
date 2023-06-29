package blockchain;

import java.util.ArrayList;
import java.util.Date;

public class Block {

    public String hash;
    public String previousHash;
    private int[][] data; //our data will be a simple message.
    private long timeStamp; //as number of milliseconds
    private int nonce;
//    ArrayList<String> h = new ArrayList<String>();

    //Block Constructor.
    public Block(int[][] data,String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();

        this.hash = calculateHash(); //Making sure we do this after we set the other values.
    }


    //Calculate new hash based on blocks contents
    public String calculateHash() {
        String calculatedhash = StringUtil.applySha256(
                previousHash +
                        Long.toString(timeStamp) +
                        Integer.toString(nonce) +
                        data
        );
//        ArrayList<String> h = new ArrayList<String>();
//        h.add(calculatedhash);
        return calculatedhash;
    }

    //Increases nonce value until hash target is reached.
    // for the computational work that nodes in the network undertake in hopes of earning new tokens.
    public void mineBlock(int difficulty) {
        String target = StringUtil.getDificultyString(difficulty); //Create a string with difficulty * "0"
        while(!hash.substring( 0, difficulty).equals(target)) {
            nonce ++;
            hash = calculateHash();
        }
        System.out.println("Block Mined!!! : " + hash);
    }

}

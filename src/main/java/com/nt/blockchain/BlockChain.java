package com.nt.blockchain;

import java.util.ArrayList;
import java.util.List;

public class BlockChain {
	private static List<Block> blockchain = new ArrayList<Block>();
	public static int difficulty = 5;

	public static Boolean isChainValid() {
		Block currentBlock;
		Block previousBlock;

		for (int i = 1; i < blockchain.size(); i++) {
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i - 1);
			if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
				System.out.println("Current Hashes not equal");
				return false;
			}
			if (!previousBlock.hash.equals(currentBlock.previousHash)) {
				System.out.println("Previous Hashes not equal");
				return false;
			}
			if (!currentBlock.blockMined()) {
				System.out.println("This block hasn't been mined");
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		Block b1 = new Block("0", "block 1");
		b1.mineBlock();
		Block b2 = new Block(b1.hash, "try to mine block 2");
		b2.mineBlock();
		blockchain.add(b1);
		blockchain.add(b2);
		System.out.println(blockchain);
		System.out.println(isChainValid());
	}

}

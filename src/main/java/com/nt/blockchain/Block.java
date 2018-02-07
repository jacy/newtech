package com.nt.blockchain;

import java.security.MessageDigest;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Block {
	public String previousHash;
	public String data;
	public long timeStamp;
	public String hash;
	public int nonce;
	private int difficulty;

	public Block(String previousHash, String data) {
		this.previousHash = previousHash;
		this.data = data;
		this.timeStamp = System.currentTimeMillis();
		this.hash = calculateHash();
		this.difficulty = 5;
	}

	public String calculateHash() {
		return StringUtil.applySha256(previousHash + timeStamp + nonce + data);
	}

	public void mineBlock() {
		while (!blockMined()) {
			nonce++;
			hash = calculateHash();
		}
		System.out.println("Block Mined!!! : " + hash);
	}

	public boolean blockMined() {
		return hash.startsWith(numbersOf0());
	}

	public String numbersOf0() {
		return IntStream.rangeClosed(1, difficulty).boxed().map(i -> "0").collect(Collectors.joining());
	}

	@Override
	public String toString() {
		return "Block [previousHash=" + previousHash + ", data=" + data + ", timeStamp=" + timeStamp + ", hash=" + hash + ", nonce=" + nonce + "]";
	}

	public static void main(String[] args) {
		String numberOfStrings = IntStream.rangeClosed(1, 1).boxed().map(i -> "0").collect(Collectors.joining());
		System.out.println(numberOfStrings);
	}

}

class StringUtil {
	// Applies Sha256 to a string and returns the result.
	public static String applySha256(String input) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			// Applies sha256 to our input,
			byte[] hash = digest.digest(input.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer(); // This will contain hash as hexidecimal
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

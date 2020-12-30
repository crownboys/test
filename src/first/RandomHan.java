package first;

import java.util.Random;

class RandomHan {
 
    public static char getRandomChar() {
        return (char) (0x4e00 + (int) (Math.random() * (0x9fa5 - 0x4e00 + 1)));
    }
    public static void main(String[] args) {
    	 RandomHan han = new RandomHan();
    	System.out.println(han.getRandomChar()); 
	}
}


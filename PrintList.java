package day21.javaConcepts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PrintList {

	public static void main(String[] args) {

		/*
		 * Write a java program to print the list (Need 3 different answers)
		 * Input: [B,u,g,a,t,t,i, ,C,h,i,r,o,n]
		 */
		List<Character> input = new ArrayList<Character>();
		String str = "Bugatti Chiron";
		
		for (int i = 0; i < str.length(); i++) {
			input.add(str.charAt(i));
		}
		/*
		 * Method 1 -> to print as List
		 * for (Character character : input) {
			System.out.print(character+",");
		}*/
		
		// Method 2 -> to print as List
		Iterator<Character> itr = input.iterator();
		while (itr.hasNext()) {
			System.out.print(itr.next()+",");
		}
		/*
		 * Method 3-> to print as List
		 * int count = 0;
		while (count<input.size()) {
			System.out.print(input.get(count));
			count++;
		}*/
	}

}

package day21.javaConcepts;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class PrintDuplicates {

	public static void main(String[] args) {

		/*
		 * Write a java program to print the duplicate character using Collections Framework.
		 * Input: "When life gives you lemons, make lemonade"
		 */
		
		
		String input = "When life gives you lemons, make lemonade";
		/*
		 * Method 1 using set
		 * Set<Character> charSet = new LinkedHashSet<Character>();
		
		for (int i = 0; i <input.length(); i++) {
			
			if (!charSet.add(input.charAt(i))) {
				System.out.print(input.charAt(i)+",");
			} 
		}*/

		// Method 2 using List
		List<Character> charList = new ArrayList<Character>();
		for (int j = 0; j < input.length(); j++) {
			if (charList.contains(input.charAt(j))) {
				System.out.print(input.charAt(j)+",");
			} else {
				charList.add(input.charAt(j));
			} 
		}
	}

}

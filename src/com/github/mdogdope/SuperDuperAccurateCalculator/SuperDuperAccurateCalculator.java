package com.github.mdogdope.SuperDuperAccurateCalculator;

import java.io.File;
import java.io.IOException;

public class SuperDuperAccurateCalculator {

	public static void main(String[] args) {
		try {
			
		File a1 = new File("a1.txt");
		File a2 = new File("a2.txt");
		
		
		
		SDANumber test = new Addition(a1, a2);
		
		}catch(IOException e) {
			System.out.println(e.toString());
		}
	}

}

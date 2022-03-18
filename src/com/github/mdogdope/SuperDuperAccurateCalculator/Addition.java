package com.github.mdogdope.SuperDuperAccurateCalculator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;


//TODO: Add support for decimal numbers.

public class Addition extends SDANumber {
	
	public Addition(File oarg1, File oarg2) throws IOException{
		boolean debug = false;
		
		
		if(debug) {
			long test = findDecimalPoint(oarg1);
			System.out.println(test);
		}else {
		
			// Make temp files for operation use.
			makeTemp(oarg1);
			makeTemp(oarg2);
			
			// Set copies of files to be used.
			File arg1 = new File(oarg1 + ".temp");
			File arg2 = new File(oarg2 + ".temp");
			
			// Make output file for final calculation.
			File outFile = new File("out.txt");
			
			reverse(arg1);
			reverse(arg2);
			
			long lenArg1, lenArg2 = 0;
			
			lenArg1 = getFileLength(arg1);
			lenArg2 = getFileLength(arg2);
			
	//		System.out.println(lenArg1 + " | " + lenArg2);
			
			if(lenArg1 > lenArg2) {
				appendZeros(arg2, lenArg2, lenArg1);
			}else if(lenArg1 < lenArg2) {
				appendZeros(arg1, lenArg1, lenArg2);
			}
			
			
			// Add the two files together.
			int carry = 0;
			
			RandomAccessFile fin1 = new RandomAccessFile(arg1, "r");
			RandomAccessFile fin2 = new RandomAccessFile(arg2, "r");
			BufferedWriter fout = new BufferedWriter(new FileWriter(outFile));
			
			for(long i = 0; i < fin1.length(); i++) {
				
				int n1 = Character.getNumericValue(fin1.read());
				int n2 = Character.getNumericValue(fin2.read());
				System.out.println(i + "|" + n1 + "|" + n2);
				Integer total = n1 + n2 + carry;
				
				if(total >= 10) {
					carry = 1;
					total -= 10;
				}else {
					carry = 0;
				}
				
				fout.append(total.toString());
				
			}
			
			
			
			fin1.close();
			fin2.close();
			fout.close();
			
			arg1.delete();
			arg2.delete();
			
			reverse(outFile);
		}
		
	}
	
	private void makeTemp(File f) throws IOException {

		
		BufferedReader fin = new BufferedReader(new FileReader(f));
		BufferedWriter fout = new BufferedWriter(new FileWriter(new File(f.toString() + ".temp")));
		
		int c;
		
		while((c = fin.read()) > 0) {
			fout.append((char) c);
		}
		
		fin.close();
		fout.close();
	}
	
	private long getFileLength(File f) throws IOException {
		RandomAccessFile raf = new RandomAccessFile(f, "r");
		long count = raf.length();
		raf.close();
		return count;
	}
	
	private void appendZeros(File f, long start, long count) throws IOException {
		BufferedWriter fout = new BufferedWriter(new FileWriter(f, true));
		
		for(long i = start; i < count; i++) {
			fout.write("0");
		}
		
		fout.close();
	}
	
	private long findDecimalPoint(File f) throws IOException {
		long counter = 0;
		
		BufferedReader fin = new BufferedReader(new FileReader(f));
		
		while(fin.ready()) {
			char c = (char) fin.read();
			if(c == '.') {
				fin.close();
				return counter;
			}
			counter++;
		}
		
		fin.close();
		return -1;
	}
	
	private void reverse(File f) throws IOException {
		File temp = new File("temp");
		RandomAccessFile fin = new RandomAccessFile(f, "r");
		BufferedWriter fout = new BufferedWriter(new FileWriter(temp));
		for(long len = fin.length() - 1; len >= 0; len--) {
			fin.seek(len);
			fout.write(fin.read());
		}
		fin.close();
		fout.close();
		
		f.delete();
		
		temp.renameTo(f);
	}
	
}

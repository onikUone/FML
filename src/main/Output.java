package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Output {
	//Field ******************************************
	String fileNameDir = "result/";
	// ************************************************

	//Constructor *************************************
	public Output(String _fileNameDir) {
		this.fileNameDir = _fileNameDir;
	}
	// ************************************************

	//Methods *****************************************
	public static void writeList(ArrayList<Double> _list, String _fileName) throws IOException {
		FileWriter fw = new FileWriter(_fileName, true);
		PrintWriter pw = new PrintWriter( new BufferedWriter(fw) );

		for(int i = 0; i < _list.size(); i++) {
			pw.println(_list.get(i));
		}
		pw.close();
	}

	public static void writeArray(double[] _array, String _fileName) throws IOException {
		FileWriter fw = new FileWriter(_fileName, true);
		PrintWriter pw = new PrintWriter( new BufferedWriter(fw) );
		for(int i = 0; i < _array.length; i++) {
			pw.println(_array[i]);
		}
		pw.close();
	}

	public static void writeConc(RuleSet _ruleSet, String _fileName) throws IOException {
		FileWriter fw = new FileWriter(_fileName, true);
		PrintWriter pw = new PrintWriter( new BufferedWriter(fw) );

		for(int i = 0; i < _ruleSet.rules.length; i++) {
			pw.println(_ruleSet.rules[i].getConclution());
		}
		pw.close();
	}
	// ************************************************
}

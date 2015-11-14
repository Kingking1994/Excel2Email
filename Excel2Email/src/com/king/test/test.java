package com.king.test;

import java.util.List;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> list = POIReadExcel.getNum();
		for (String string : list) {
			System.out.println(string);
		}
	}

}

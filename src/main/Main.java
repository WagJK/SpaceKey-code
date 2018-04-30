package main;

import java.util.HashSet;

import global.Config;
import global.Point;

public class Main {
	
	public static void main(String args[]) {
		Methods methods = new Methods();
		methods.constructData(Config.locUK, Config.docUK);
		
		HashSet<String> keywords = new HashSet<String>();
		keywords.add("house");
		keywords.add("school") ;
		
		HashSet<Point> result_exact = methods.mckExact(keywords);
		HashSet<Point> result_SKECaplus = methods.mckSKECaplus(keywords);
		HashSet<Point> result_GKG = methods.mckGKG(keywords);
		
		System.out.println("result_exact:");
		for (Point point : result_exact) {
			for (String keyword : point.keywords) {
				System.out.print(keyword);
				System.out.print(" ");
			}
			System.out.println();
		}
		System.out.println("result_SKECaplus:");
		for (Point point : result_SKECaplus) {
			for (String keyword : point.keywords) {
				System.out.print(keyword);
				System.out.print(" ");
			}
			System.out.println();
		}
		System.out.println("result_GKG:");
		for (Point point : result_GKG) {
			for (String keyword : point.keywords) {
				System.out.print(keyword);
				System.out.print(" ");
			}
			System.out.println();
		}
	}
}

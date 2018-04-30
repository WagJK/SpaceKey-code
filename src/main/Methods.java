package main;

import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Set;

import global.Pair;
import global.Point;
import global.Words;
import global.datastruct.docindex.InvertedFile;
import global.typedef.Dataset;
import global.typedef.Group;
import global.typedef.STObject;
import global.DataReader;
import global.Env;
import global.Link;

public class Methods implements MethodsInterface {
	
	private double loc[][];
	private String kws[][];
	private coskq.irtree.Node coskqRoot;
	private InvertedFile invertedFile;
	
	public void constructData(String locPath, String docPath) {
		// read data
		DataReader dataReader = new DataReader(locPath, docPath);
		this.loc = dataReader.readLoc();
		this.kws = dataReader.readKws();
		
		// mck & minsk construct
		Env.W = new Words();
		InvertedFile iv = new InvertedFile();
		Dataset db = new Dataset();
		for (int i=0 ; i<loc.length ; i++) {
			HashSet<String> keywords = new HashSet<String>();
			for (int j=0 ; j<kws[i].length ; j++)
				keywords.add(kws[i][j]);
			STObject obj = new STObject(i+1, loc[i][0], loc[i][1], keywords);
			db.add(obj);
			Env.W.add(obj);
		}
		for (STObject o: db) iv.add(o);	// loading objects into the inverted file
		this.invertedFile = iv;
		
		// coskq construct
		String indexFile = global.Config.indexUK;
		coskq.irtree.BuildIRTree builder1 = new coskq.irtree.BuildIRTree(loc, kws, indexFile);
		coskqRoot = builder1.build();
	}
	
	public HashSet<Point> mckGKG(HashSet<String> keywords) {
		if (invertedFile == null) {
			System.out.println("Error: mck not yet constructed"); return null;
		}
		mck.Algorithm alg = new mck.Algorithm();
		Group ans = alg.GKG(keywords, invertedFile);
		
		HashSet<Point> result = new HashSet<Point>();
		for (STObject obj : ans) {
			Point newPoint = new Point(obj.loc.x, obj.loc.y);
			for (String keyword : obj.text) {
				if (keywords.contains(keyword))
					newPoint.addKeyword(keyword);
			}
			result.add(newPoint);
		}
		return result;
	}
	
	public HashSet<Point> mckSKECaplus(HashSet<String> keywords) {
		if (invertedFile == null) {
			System.out.println("Error: mck not yet constructed"); return null;
		}
		mck.Algorithm alg = new mck.Algorithm();
		Group ans = alg.SKECaplus(keywords, invertedFile);
		
		HashSet<Point> result = new HashSet<Point>();
		for (STObject obj : ans) {
			Point newPoint = new Point(obj.loc.x, obj.loc.y);
			for (String keyword : obj.text) {
				if (keywords.contains(keyword))
					newPoint.addKeyword(keyword);
			}
			result.add(newPoint);
		}
		return result;
	}

	public HashSet<Point> mckExact(HashSet<String> keywords) {
		if (invertedFile == null) {
			System.out.println("Error: mck not yet constructed"); return null;
		}
		mck.Algorithm alg = new mck.Algorithm();
		Group ans = alg.Exact(keywords, invertedFile);
		
		HashSet<Point> result = new HashSet<Point>();
		for (STObject obj : ans) {
			Point newPoint = new Point(obj.loc.x, obj.loc.y);
			for (String keyword : obj.text) {
				if (keywords.contains(keyword))
					newPoint.addKeyword(keyword);
			}
			result.add(newPoint);
		}
		return result;
	}

	public HashSet<Point> minskScaleLune(HashSet<String> keywords) {
		if (invertedFile == null) {
			System.out.println("Error: minsk not yet constructed"); return null;
		}
		minsk.Algorithm alg = new minsk.Algorithm();
		Group ans = alg.ScaleLunePolar(keywords, invertedFile);
		
		HashSet<Point> result = new HashSet<Point>();
		for (STObject obj : ans) {
			Point newPoint = new Point(obj.loc.x, obj.loc.y);
			for (String keyword : obj.text) {
				if (keywords.contains(keyword))
					newPoint.addKeyword(keyword);
			}
			result.add(newPoint);
		}
		return result;
	}
	
	public HashSet<Point> coskqType1Appro(double query_x, double query_y, HashSet<String> keywords) {
		if (coskqRoot == null) {
			System.out.println("Error: coskq not yet constructed"); return null;
		}
		coskq.algorithm.Algorithm alg = new coskq.algorithm.Algorithm();
		coskq.pattern.Point		point = new coskq.pattern.Point(0, query_x, query_y);
		coskq.pattern.Pattern 	query = new coskq.pattern.Pattern(point, keywords);
		Pair<Double, HashSet<coskq.pattern.Point>> ans = alg.Type1Greedy(query, coskqRoot);
		
		HashSet<Point> result = new HashSet<Point>();
		for (coskq.pattern.Point obj : ans.getSecond()) {
			Point newPoint = new Point(obj.x, obj.y);
			for (String keyword : obj.keywords) {
				if (keywords.contains(keyword))
					newPoint.addKeyword(keyword);
			}
			result.add(newPoint);
		}
		return result;
	}
	
	public HashSet<Point> coskqType1Exact(double query_x, double query_y, HashSet<String> keywords) {
		if (coskqRoot == null) {
			System.out.println("Error: coskq not yet constructed"); return null;
		}
		coskq.algorithm.Algorithm alg = new coskq.algorithm.Algorithm();
		coskq.pattern.Point		point = new coskq.pattern.Point(0, query_x, query_y);
		coskq.pattern.Pattern 	query = new coskq.pattern.Pattern(point, keywords);
		Pair<Double, HashSet<coskq.pattern.Point>> ans = alg.Type1ExactWIndex(query, coskqRoot);
		
		HashSet<Point> result = new HashSet<Point>();
		for (coskq.pattern.Point obj : ans.getSecond()) {
			Point newPoint = new Point(obj.x, obj.y);
			for (String keyword : obj.keywords) {
				if (keywords.contains(keyword))
					newPoint.addKeyword(keyword);
			}
			result.add(newPoint);
		}
		return result;
	}
	
	public HashSet<Point> coskqType2Appro(double query_x, double query_y, HashSet<String> keywords) {
		if (coskqRoot == null) {
			System.out.println("Error: coskq not yet constructed"); return null;
		}
		coskq.algorithm.AlgorithmDist alg = new coskq.algorithm.AlgorithmDist();
		coskq.pattern.Point		point = new coskq.pattern.Point(0, query_x, query_y);
		coskq.pattern.Pattern 	query = new coskq.pattern.Pattern(point, keywords);
		Pair<Double, HashSet<coskq.pattern.Point>> ans = alg.Type2DistAppro(query, coskqRoot);
		
		HashSet<Point> result = new HashSet<Point>();
		for (coskq.pattern.Point obj : ans.getSecond()) {
			Point newPoint = new Point(obj.x, obj.y);
			for (String keyword : obj.keywords) {
				if (keywords.contains(keyword))
					newPoint.addKeyword(keyword);
			}
			result.add(newPoint);
		}
		return result;
	}
	
	public HashSet<Point> coskqType2Exact(double query_x, double query_y, HashSet<String> keywords) {
		if (coskqRoot == null) {
			System.out.println("Error: coskq not yet constructed"); return null;
		}
		coskq.algorithm.AlgorithmDist alg = new coskq.algorithm.AlgorithmDist();
		coskq.pattern.Point		point = new coskq.pattern.Point(0, query_x, query_y);
		coskq.pattern.Pattern 	query = new coskq.pattern.Pattern(point, keywords);
		Pair<Double, HashSet<coskq.pattern.Point>> ans = alg.Type2DistExact(query, coskqRoot);
		
		HashSet<Point> result = new HashSet<Point>();
		for (coskq.pattern.Point obj : ans.getSecond()) {
			Point newPoint = new Point(obj.x, obj.y);
			for (String keyword : obj.keywords) {
				if (keywords.contains(keyword))
					newPoint.addKeyword(keyword);
			}
			result.add(newPoint);
		}
		return result;
	}
	
	public HashSet<Point> coskqType3Appro(double query_x, double query_y, HashSet<String> keywords) {
		if (coskqRoot == null) {
			System.out.println("Error: coskq not yet constructed"); return null;
		}
		coskq.algorithm.AlgorithmDist alg = new coskq.algorithm.AlgorithmDist();
		coskq.pattern.Point		point = new coskq.pattern.Point(0, query_x, query_y);
		coskq.pattern.Pattern 	query = new coskq.pattern.Pattern(point, keywords);
		Pair<Double, HashSet<coskq.pattern.Point>> ans = alg.Type3DistAppro(query, coskqRoot);
		
		HashSet<Point> result = new HashSet<Point>();
		for (coskq.pattern.Point obj : ans.getSecond()) {
			Point newPoint = new Point(obj.x, obj.y);
			for (String keyword : obj.keywords) {
				if (keywords.contains(keyword))
					newPoint.addKeyword(keyword);
			}
			result.add(newPoint);
		}
		return result;
	}
	
	public HashSet<Point> coskqType3Exact(double query_x, double query_y, HashSet<String> keywords) {
		if (coskqRoot == null) {
			System.out.println("Error: coskq not yet constructed"); return null;
		}
		coskq.algorithm.AlgorithmDist alg = new coskq.algorithm.AlgorithmDist();
		coskq.pattern.Point		point = new coskq.pattern.Point(0, query_x, query_y);
		coskq.pattern.Pattern 	query = new coskq.pattern.Pattern(point, keywords);
		Pair<Double, HashSet<coskq.pattern.Point>> ans = alg.Type3DistExact(query, coskqRoot);
		
		HashSet<Point> result = new HashSet<Point>();
		for (coskq.pattern.Point obj : ans.getSecond()) {
			Point newPoint = new Point(obj.x, obj.y);
			for (String keyword : obj.keywords) {
				if (keywords.contains(keyword))
					newPoint.addKeyword(keyword);
			}
			result.add(newPoint);
		}
		return result;
	}
}
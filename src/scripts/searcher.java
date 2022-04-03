package scripts;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;

public class searcher {
	private static String input_file;
	private static String query; 
	public searcher(String path,String query) {
		// TODO Auto-generated constructor stub
		this.input_file = path;
		this.query=query;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	 @SuppressWarnings({ "rawtypes", "unchecked", "nls" })
	public void srch() throws IOException, ClassNotFoundException{
		// TODO Auto-generated method stub
		 FileInputStream FS = new FileInputStream(input_file);
	      ObjectInputStream obj = new ObjectInputStream(FS);

	      Object object = obj.readObject();
	      obj.close();

	      HashMap hash = (HashMap) object;


	      String qry = query;

	      Map map = new HashMap();   //query map
	      Double[] id = new Double[5]; //id value
	      String[] fl = { "떡", "라면", "아이스크림", "초밥", "파스타" };
	      for (int i = 0; i < 5; i++) {
	         id[i] = 0.0;
	      }

	      KeywordExtractor ke = new KeywordExtractor();
	      KeywordList k1 = ke.extractKeyword(qry, true);

	      for (int j = 0; j < k1.size(); j++) {
	         Keyword kwrd = k1.get(j);
	         map.put(kwrd.getString(), kwrd.getCnt());
	      }

	      Iterator<String> ii = map.keySet().iterator();
	      while (ii.hasNext()) {
	         String key = ii.next();
	         int tf = (int) map.get(key);
	         ArrayList arr = (ArrayList) hash.get(key);
	         for (int i = 0; i < 5; i++) {
	            id[i] += tf * (double) arr.get(i);
	         }
	      }

	      //search and output
	      Double[] ab = { id[0], id[1], id[2], id[3], id[4] };
	      int[] st = { -1, -1, -1, -1, -1 };
	      Arrays.sort(ab, Collections.reverseOrder());
	      for (int i = 0; i < 5; i++) {
	         if (ab[0] == id[i]) {
	            if (ab[0] != 0.0)
	               st[i] = 0;
	         } 
	         else if (ab[1] == id[i]) {
	            if (ab[1] != 0.0)
	               st[i] = 1;
	         } 
	         else if (ab[2] == id[i]) {
	            if (ab[2] != 0.0)
	               st[i] = 2;
	         } 
	      }

	      for (int i = 0; i < 5; i++) {
	         if (st[i] == 0) {
	            System.out.println("첫번째:" + fl[i]);
	         }
	      }
	      for (int i = 0; i < 5; i++) {
	         if (st[i] == 1) {
	            System.out.println("두번째:" + fl[i]);
	         }
	      }
	      for (int i = 0; i < 5; i++) {
	         if (st[i] == 2) {
	            System.out.println("세번째" + fl[i]);
	         }
	      }
	      System.out.println("5주차 실행완료");

	}

}

package scripts;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.SecureCacheResponse;
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


public class searcher implements Serializable{
	private static String input_file;
	private static String query; 
	public searcher(String path,String query) {
		// TODO Auto-generated constructor stub
		this.input_file = path;
		this.query=query;
	}
	
	static Double[] CalcSim(double a,Double[] bsum,Double[] id,Map map,HashMap hash){
	      
	       Iterator<String> ii = map.keySet().iterator();
	           while (ii.hasNext()) {
	              String key = ii.next();
	              int tf = (int) map.get(key);
	              a+=((double)tf*(double)tf);
	              ArrayList arr = (ArrayList) hash.get(key);
	              if(arr!=null)
	              for (int i = 0; i < 5; i++) {
	                 double w=(double)arr.get(i);
	                 id[i] += tf * w;      
	                 bsum[i]+=w*w;
	              }
	           }
	           
	       Double[] cos=new Double[5];
	       for(int i=0;i<5;i++) {
	          Double A=Math.sqrt(a);
	            Double B=Math.sqrt(bsum[i]);
	            if(B!=0)
	             cos[i] =  id[i]/(A*B);    
	            else
	              cos[i]=0.0;
	       }
	       return cos;
	    }
	
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		// TODO Auto-generated method stub

	}
	
	 @SuppressWarnings({ "rawtypes", "unchecked", "nls" })
	public static void srch() throws IOException, ClassNotFoundException{
		// TODO Auto-generated method stub
		 FileInputStream FS = new FileInputStream(input_file);
	      ObjectInputStream obj = new ObjectInputStream(FS);

	      Object object = obj.readObject();
	      obj.close();

	      HashMap hash = (HashMap) object;


	      String qry = query;

	      Map map = new HashMap();   //query map
	        Double[] id = new Double[5]; //id value
	        Double[] cos = new Double[5];
	        Double asum=0.0;
	        Double[] bsum=new Double[5];
	         
	        String[] fl = { "떡", "라면", "아이스크림", "초밥", "파스타" };
	        for (int i = 0; i < 5; i++) {
	           id[i] = 0.0;
	        }
	        for (int i = 0; i < 5; i++) {
	            cos[i] = 0.0;
	         }
	        for (int i = 0; i < 5; i++) {
	            bsum[i] = 0.0;
	         }
	        
	        
	         
	        
	        KeywordExtractor ke = new KeywordExtractor();
	        KeywordList k1 = ke.extractKeyword(qry, true);
	        
	        for (int j = 0; j < k1.size(); j++) {
	           Keyword kwrd = k1.get(j);
	           map.put(kwrd.getString(), kwrd.getCnt());
	        }
	        
	        
	        
	        cos=CalcSim(asum,bsum,id,map,(HashMap)object);
	       
	        
	        

	        //search and output
	        Double[] ab = { cos[0], cos[1], cos[2], cos[3], cos[4] };
	        int[] st = { -1, -1, -1, -1, -1 };
	        Arrays.sort(ab,Collections.reverseOrder());
	        for (int i = 0; i < 5; i++) {
	           if (ab[0] == cos[i]) {
	              if (ab[0] != 0.0)
	                 st[i] = 0;
	           } 
	           else if (ab[1] == cos[i]) {
	              if (ab[1] != 0.0)
	                 st[i] = 1;
	           } 
	           else if (ab[2] == cos[i]) {
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
	              System.out.println("세번째:" + fl[i]);
	           }
	        }
	        if(ab[0]==0) {
	           System.out.println("검색 된 결과 값이 없습니다.");
	        }
	        System.out.println("5주차 실행완료");

	     
		

	}

}

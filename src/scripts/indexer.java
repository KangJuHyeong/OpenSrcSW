package scripts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.jsoup.Jsoup;

public class indexer implements Serializable{
	private static String input_file;
	private static String output_flie = "./index.post";
	public indexer(String path) {
		// TODO Auto-generated constructor stub
		this.input_file = path;
	}

	
	public static void main(String[] args)  {
		
		
	}
	
	@SuppressWarnings({"rawtypes", "unchecked","nls"})
	public void makepost() throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		FileOutputStream fileStream =new FileOutputStream(output_flie);
	      ObjectOutputStream objectOutputStream =new ObjectOutputStream(fileStream);
	      
	      

	      HashMap map = new HashMap<String, Object>();
	      HashMap[] fn = new HashMap[5];
	      for (int i = 0; i < 5; i++) {
	         fn[i] = new HashMap<String, String>();
	      }

	      File br = new File(input_file);
	      org.jsoup.nodes.Document html = Jsoup.parse(br, "UTF-8");

	      for (int i = 0; i < html.select("title").size(); i++) {

	         org.jsoup.select.Elements a = html.select("docs doc#" + i); // doc id로 본문 접근
	         String wr = a.text();

	         StringTokenizer st = new StringTokenizer(wr, " ");
	         String titleData = st.nextToken(); // title 데이터 추출
	         String bodyData = wr.substring(titleData.length()); // body 데이터 추출
	         bodyData = bodyData.trim();

	         StringTokenizer st1 = new StringTokenizer(bodyData, "#");
	         while (st1.hasMoreTokens()) {
	            String ss = st1.nextToken();
	            StringTokenizer st2 = new StringTokenizer(ss, ":");
	            String key = st2.nextToken();
	            String value = st2.nextToken();
	            fn[i].put(key, value);
	         }

	      }
	      //

	      for (int i = 0; i < 5; i++) {
	         Iterator<String> keys = fn[i].keySet().iterator();
	         while (keys.hasNext()) {
	            String key = keys.next();
	            String value = (String) fn[i].get(key);
	            int tfxy = Integer.parseInt(value);
	            int dfx = 0;
	            for (int j = 0; j < 5; j++) {
	               if (fn[j].get(key) != null) {
	                  dfx++;
	               }
	            }

	            if (map.containsKey(key)) {

	               double wx = tfxy * (Math.log(5.0 / dfx));
	               ((ArrayList) map.get(key)).set(i,wx);
	               
	            } else {
	               List lli=new ArrayList(5);
	               for(int j=0;j<5;j++)
	               lli.add(0.0);
	               map.put(key, lli);
	               double wx = tfxy * (Math.log(5.0 / dfx));
	               ((ArrayList) map.get(key)).set(i,wx);

	            }

	         }

	      }
	      //파일 쓰기
	      objectOutputStream.writeObject(map);
	      objectOutputStream.close();
	      
	     
	      

	      //파일읽기
	      FileInputStream FS=new FileInputStream(output_flie);
	      ObjectInputStream obj= new ObjectInputStream(FS);
	   
	      Object object=obj.readObject();
	      obj.close();
	      
	      System.out.println("읽어온 객체의 type:"+object.getClass());
	      
	      HashMap hash= (HashMap) object;
	      
	      Iterator<String> itr = hash.keySet().iterator();
	      while (itr.hasNext()) {
	         String key = itr.next();
	         ArrayList arrr = (ArrayList) hash.get(key);
	         System.out.print(key+"->");
	         for (int i = 0; i < 5; i++) {            
	            System.out.print(i+" "+String.format("%.2f", arrr.get(i))+" ");
	         }
	         System.out.println();
	      }

		System.out.println("4주차 실행완료");
	}
}

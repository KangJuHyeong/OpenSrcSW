package scripts;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jsoup.Jsoup;
import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;



public class makeCollection{
	static private String data_path;
	static private String output_flie = "./collection.xml";
	
	public makeCollection(String path) {
		this.data_path = path;
	}
	
	public void makeXml() throws TransformerException, IOException {
		try {
			DocumentBuilderFactory docFactory =DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder;
			docBuilder = docFactory.newDocumentBuilder();
			
			Document doc; 
			doc= docBuilder.newDocument();
			
			Element movie =doc.createElement("docs");
			doc.appendChild(movie);		
				
			
			File[] abc=makeFileList(data_path);
			for(int i=0;i<abc.length;i++) {
				org.jsoup.nodes.Document html=Jsoup.parse(abc[i],"UTF-8");
				String titleData=html.title();
				String bodyData =html.body().text();
				//파일 받아오기
				
				Element w =doc.createElement("doc");
				movie.appendChild(w);
				
				w.setAttribute("id",Integer.toString(i));
				
				Element mn=doc.createElement("title");
				mn.appendChild(doc.createTextNode(titleData));
				w.appendChild(mn);
				
				Element mv =doc.createElement("body");
				mv.appendChild(doc.createTextNode(bodyData));
				w.appendChild(mv);
				
			}
			//반복문을 사용하여 받아온 파일 내용 doc에 저장
			
			
			TransformerFactory transformerFactory=TransformerFactory.newInstance();
			
			Transformer transformer=transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
			
			DOMSource source=new DOMSource(doc);
			StreamResult result=new StreamResult(new FileOutputStream(new File(output_flie)));
			
			transformer.transform(source, result);
			//doc에 저장한 파일 쓰기
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println("2주차 실행완료");
	}
	public static File[] makeFileList(String path) {
		File dir=new File(path);
		return dir.listFiles();
	}
	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		
		
	}
}

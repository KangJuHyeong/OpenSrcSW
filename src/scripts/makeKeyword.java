package scripts;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.lang.model.util.Elements;
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;


public class makeKeyword {
	public static File[] makeFileList(String path) {
		File dir=new File(path);
		return dir.listFiles();
	}
	private static String input_file;
	private static String output_flie = "./index.xml";
	
	public makeKeyword(String file) {
		this.input_file = file;
	}

	public void convertXml() throws TransformerException, IOException{
		try {
			DocumentBuilderFactory docFactory =DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder;
			docBuilder = docFactory.newDocumentBuilder();
			
			Document doc; 
			doc= docBuilder.newDocument();
			
			Element movie =doc.createElement("docs");
			doc.appendChild(movie);		
				
			
			File[] abc=makeFileList("./data");
			for(int i=0;i<abc.length;i++) {
				org.jsoup.nodes.Document html=Jsoup.parse(abc[i],"UTF-8");
				String titleData=html.title();
				String bodyData =html.body().text();
				
				
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
			
			
			TransformerFactory transformerFactory=TransformerFactory.newInstance();
			
			Transformer transformer=transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
			
			DOMSource source=new DOMSource(doc);
			StreamResult result=new StreamResult(new FileOutputStream(new File("./collection.xml")));
			
			transformer.transform(source, result);
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		try {
			DocumentBuilderFactory docFactory =DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder;
			docBuilder = docFactory.newDocumentBuilder();
			
			Document doc; 
			doc= docBuilder.newDocument();
			
			Element movie =doc.createElement("docs");
			doc.appendChild(movie);		
			
			
			File br=new File(input_file);			
			org.jsoup.nodes.Document html=Jsoup.parse(br,"UTF-8");	
								
			for(int i=0;i<html.select("title").size();i++) {
				org.jsoup.select.Elements a=html.select("docs doc#"+i);	
				String wr=a.text();
				StringTokenizer st=new StringTokenizer(wr," ");
				String titleData=st.nextToken();			
				String bodyData =wr.substring(titleData.length());	
				Element w =doc.createElement("doc");
				movie.appendChild(w);
				
				w.setAttribute("id",Integer.toString(i));
				
				Element mn=doc.createElement("title");
				mn.appendChild(doc.createTextNode(titleData));
				w.appendChild(mn);
				
				
				KeywordExtractor ke=new KeywordExtractor();
				KeywordList k1=ke.extractKeyword(bodyData, true);
				String x="";
				for(int j=0;j<k1.size();j++) {
					Keyword kwrd=k1.get(j);	
					x=x.concat(kwrd.getString()+":"+kwrd.getCnt()+"#");

				}//body 
				

				Element mv =doc.createElement("body");
				mv.appendChild(doc.createTextNode(x));
				
				w.appendChild(mv);
				
				
			}
			
		
			
			TransformerFactory transformerFactory=TransformerFactory.newInstance();
			
			Transformer transformer=transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
			
			DOMSource source=new DOMSource(doc);
			StreamResult result=new StreamResult(new FileOutputStream(new File(output_flie)));
			
			transformer.transform(source, result);
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("3주차 실행완료");
	}
	public static void main(String[] args)   {
		
		// TODO Auto-generated method stub
	
		
		
		
	}
}

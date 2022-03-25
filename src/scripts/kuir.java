package scripts;

import java.io.IOException;

import javax.xml.transform.TransformerException;

import scripts.makeCollection;
import scripts.makeKeyword;
import scripts.indexer;
public class kuir {

	public static void main(String[] args) throws TransformerException, IOException, ClassNotFoundException {
		
		String command = args[0];   
		String path = args[1];

		if(command.equals("-c")) {
			makeCollection collection = new makeCollection(path);
			collection.makeXml();
		}
		else if(command.equals("-k")) {
			makeKeyword keyword = new makeKeyword(path);
			keyword.convertXml();
		}
		else if(command.equals("-i")) {
			indexer keyword = new indexer(path);
			keyword.makepost();;
		}
	}
}

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
		String command2 =args[2];
		String query=args[3];
		if(command.equals("-c")) {
			makeCollection collection = new makeCollection(path);
			collection.makeXml();
		}
		else if(command.equals("-k")) {
			makeKeyword keyword = new makeKeyword(path);
			keyword.convertXml();
		}
		else if(command.equals("-i")) {
			indexer index = new indexer(path);
			index.makepost();
		}
		else if(command.equals("-s")) {
			searcher search = new searcher(path,query);
			search.srch();
		}
	}
}

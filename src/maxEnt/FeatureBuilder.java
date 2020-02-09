package maxEnt;

import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;

public class FeatureBuilder {

    public static void main (String[] args) throws IOException {
	if (args.length != 2) {
	    System.err.println("Score takes 2 arguments:  java Score key response");
	    System.exit(1);
	}
	String keyFileName = args[0];
	String outFileName = args[1];
	FileWriter fw = new FileWriter(outFileName);
	FileWriter fw1 ;
	BufferedWriter bw1 ;
	int size=0;
	int containsPrep=0,containsNNP=0;
	BufferedWriter bw = new BufferedWriter(fw);
	File keyFile = new File(keyFileName);
	List<String> key = Files.readAllLines(keyFile.toPath(), StandardCharsets.UTF_8);
	ArrayList<ArrayList<String>> keys= new ArrayList<ArrayList<String>>();
	ArrayList<String> tags= new ArrayList<String>();
	List<String> pos=null;
	
	for (int i = 0; i < key.size(); i++) {
		
	    String keyLine = key.get(i).trim();
	    String curr_chunk="";
	    int curr_chunk_size=0,no_chunks=0;
	    int chunk_start = 0;
	    if (keyLine.equals("")) {
	    	for (int j=0;j<keys.size();j++)
		    {
	    		
	    		ArrayList<String> l = keys.get(j);
	    		bw.write(l.get(0)+"\t");
	    		bw.write("current="+l.get(0)+"\t");
	    		bw.write("currentPos="+l.get(1)+"\t");
	  
	    		//bw.write("currentChunk="+l.get(2).replace("B-", "").replace("I-", "")+"\t");
	    		
				
				
				//bw.write("posnFromStart="+Integer.toString(j+1)+"\t");
	    		//bw.write("posnFromEnd="+Integer.toString(keys.size()-j)+"\t");
	    		if((j>0))
	    		{
	    			if((j-1)>=chunk_start)
	    			{
	    				bw.write("previous="+(keys.get(j-1)).get(0)+"\t");
	    				//bw.write("previousChunk="+(keys.get(j-1)).get(2)+"\t");
	    			}
	    			//bw.write("previousPos="+(keys.get(j-1)).get(1)+"\t");
	    			//if(no_chunks>1)
	    				//bw.write("previousChunkPos="+(keys.get(chunk_start-1)).get(1)+"\t");
	    			if(!((curr_chunk.equals(l.get(2)))||(curr_chunk.equals("B-NP"))))
	    			{	
	    				containsPrep=0;
	    				containsNNP=0;
	    				no_chunks++;
	    				curr_chunk=l.get(2);
	    				curr_chunk_size=1;
	    				chunk_start=j;
		    			for(int k=j+1;k<keys.size();k++)
		    			{
		    				if((keys.get(k).get(2).equals(curr_chunk)))
							{
		    					curr_chunk_size++;
							}
		    				else if(curr_chunk.equals("B-NP"))
		    				{
		    					curr_chunk=keys.get(k).get(2);
		    					curr_chunk_size++;
		    				}
		    				else 
		    				{
		    					
		    					break;
		    				}
		    				if(keys.get(k).get(2).contains("IN"))
		    					containsPrep=1;
		    				if(keys.get(k).get(2).contains("NN"))
		    					containsNNP=1;
		    			}
	    			}
	    			bw.write("chunkLength="+curr_chunk_size+"\t");
	    			//bw.write("containsPrep="+containsPrep+"\t");
	    			//bw.write("containsNNP="+containsNNP+"\t");
	    			//if(l.size()>3)
	    				//bw.write("previousName="+(keys.get(j-1)).get(3)+"\t");
	    		}
	    		else
	    		{
	    			containsPrep=0;
    				containsNNP=0;
	    			curr_chunk=l.get(2);
	    			curr_chunk_size=1;
	    			no_chunks++;
	    			chunk_start=j;
	    			for(int k=j+1;k<keys.size();k++)
	    			{
	    				if((keys.get(k).get(2).equals(curr_chunk)))
						{
	    					curr_chunk_size++;
						}
	    				else if(curr_chunk.equals("B-NP"))
	    				{
	    					curr_chunk=keys.get(k).get(2);
	    					curr_chunk_size++;
	    				}
	    				else 
	    				{
	    					
	    					break;
	    				}
	    				if(keys.get(k).get(2).contains("IN"))
	    					containsPrep=1;
	    				if(keys.get(k).get(2).contains("NN"))
	    					containsNNP=1;
	    			}
	    			bw.write("chunkLength="+curr_chunk_size+"\t");
	    			//bw.write("containsPrep="+containsPrep+"\t");
	    			//bw.write("containsNNP="+containsNNP+"\t");
	    		}
	    		String trigramPos="";
	    		if ((j-2)>=0)
	    			if(((keys.get(j-2).get(2).equals(curr_chunk))||((keys.get(j-2).get(2).equals("B-NP"))&&(curr_chunk.contains("NP")))))
	    			{
	    				trigramPos+=(keys.get(j-2)).get(1);
	    			
	    			}
	    		else
	    			trigramPos+="#";
	    		if((j-1)>=0)
	    			if(((keys.get(j-1).get(2).equals(curr_chunk))||((keys.get(j-1).get(2).equals("B-NP")))))
	    				trigramPos+=(keys.get(j-1)).get(1);
	    		else
	    			trigramPos+="#";
	    		trigramPos+=l.get(1);
	    		bw.write("trigramPos="+trigramPos+"\t");
	    		
	    		if(j<(keys.size()-1))
	    		{
	    			if((j+1)<=(chunk_start+curr_chunk_size))
	    			{bw.write("next="+(keys.get(j+1)).get(0)+"\t");
	    			bw.write("nextPos="+(keys.get(j+1)).get(1)+"\t");}
	    			//bw.write("nextChunk="+(keys.get(j+1)).get(2)+"\t");
	    		}
	    		int v=(int)(l.get(0)).charAt(0);
	    		//if((v>=65)&&(v<=90))
	    			//bw.write("currentWordIsCapitalized=true"+"\t");
	    		if(l.size()>3)
	    		{
	    			bw.write(l.get(3));
	    		}
	    		//else
	    			//bw.write("@@");
	    		bw.write("\n");
		    }
	    	bw.write("\n");
	    	no_chunks=0;
	    	curr_chunk_size=0;
	    	chunk_start = 0;
	    	keys.clear();
	    			}
	    else{

		    String[] keyFields = keyLine.split("\t");
		    
		    ArrayList<String> l = new ArrayList<String>(Arrays.asList(keyFields));
		    keys.add(l);
	    	
	    }        
	}
	bw.close();

	    }
}

/*
 * scorer for NLP class Spring 2016
 * ver.1.0
 *
 * score a key file against a response file
 * both should consist of lines of the form:   token \t tag
 * sentences are separated by empty lines
 */




// java Score WSJ_POS_CORPUS_FOR_STUDENTS/WSJ_24.pos WSJ_24.pos
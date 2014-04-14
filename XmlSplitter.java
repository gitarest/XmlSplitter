package XmlSplitter;


import java.io.*; 

public class XmlSplitter {

	/**
	 * @param args
	 */

	public static void main(String args[]) 
	throws IOException 
	{

      if (args.length <4){
         System.out.println("Usage: XmlSplitter <xmlfilename> <maintag> <recordtag> <records_per_file>");
      }else{
	  String mainTag = "<"+args[1]+">";
	  String endMainTag = "</"+args[1]+">";
	  String recordTag = "<"+args[2]+">";
	  String endRecordTag = "</"+args[2]+">";
	  String rootFileName=args[0].substring(0,args[0].indexOf(".xml"));
	  int breakAt = Integer.parseInt(args[3]);
	  int totalCount=0;
	  int fileCount=0;
	  try{
		  // Open the file that is the first 
		  // command line parameter
		  FileInputStream fstream = new FileInputStream(args[0]);
		  // Get the object of DataInputStream
		  DataInputStream in = new DataInputStream(fstream);
		  BufferedReader br = new BufferedReader(new InputStreamReader(in));
		  String strLine;
		  
		  //read input file saving contents until main tag is found
		  while ((strLine = br.readLine()) != null )   {
			    if (strLine.contains(mainTag)){
			    	break;
			    }
		  }		  
 		  //while file not completely processed
 		  String status = "Processing";
 		  while (status.equals("Processing")){

 			  //open new file based on current location
 			  String fname=rootFileName+"_"+(totalCount+1)+"-"+(totalCount+breakAt)+".xml";
 			  BufferedWriter writer = new BufferedWriter(new FileWriter(fname));
 			  System.out.println("Splitting XML to File "+fname); fileCount++;
 			  //write the main tag
 	 		  writer.write(mainTag+"\n");
 			  
 			  
		  //Read File Line By Line until eof or other break condition
		  int count = 0;
		  while ((strLine = br.readLine()) != null )   {
		    //check for end of xml	  
			if (strLine.contains(endMainTag)){
			   	break;
			}
			//check for end of xml record
		    if (strLine.contains(endRecordTag)){
		    	
		  // Write the content to the file up to the tag
			  writer.write(strLine.replace("\u00a0"," ")+"\n");
			  //increment the counters
			  count++; totalCount++;
			  if (count==breakAt)
			    break;
		    }else{ //write xml replace hex a0 with blank
				  writer.write(strLine.replace("\u00a0"," ")+"\n");
		    }
		  }		  
		if (strLine.contains(endMainTag)){
	   	    writer.write(strLine.replace("\u00a0"," ")+"\n");
			//set flag that we are done
			status="done";
		}else {			
		   writer.write(endMainTag);
		}
		//close the output file 
		writer.close(); 		  
 	   }
	//end while file not completely processed
		
		  //Close the input stream
		  in.close();

		  System.out.println("Successfully extracted "+totalCount+" total XML records into "+fileCount+" files");
		  
		    }catch (Exception e){//Catch exception if any
		  System.err.println("Error: " + e.getMessage());
		  }
		  }
    }
    }
	

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Controller extends ReadExcelFile{
	Controller(){
		//constructor
		
		}	
	
	ReadExcelFile readIntData= new ReadExcelFile();
	
	String fileLocation="CoffeeShopTransactions.csv";
	ArrayList<String[]> dataInTheFile=new ArrayList<String[]>(readIntData.readCSV(fileLocation));
	
	int numberOfNeededColumnsInFile=0;
	
	//declaring global arraylist has the whole needed data in the file
	List<List<String>> records = new ArrayList<List<String>>();
	
	List<String> distnctValues =new ArrayList<String>();
	List<List<List<String>>> n_ItemSet=new ArrayList<List<List<String>>>();
	
	List<String> stronglyCorrelatedList=new ArrayList<String>();
	
	
	private	int minSupport;
	private	double minConfidence;
	//encapsulation for minimnum support and confidence variables.
	public void setMinSupport(int minSupp)
	{
		
		System.out.println("\n ataInTheFile.size()= "+dataInTheFile.size()+"\n ");
		int minimumSupport=(minSupp*dataInTheFile.size())/100;
		
		this.minSupport=minimumSupport;
		System.out.println("\n minSupport ="+ minSupport+"\n");
	}
	public int getMinSupport()
	{
		return this.minSupport;
	}
	public void setConfidence(int d)
	{
		double minimumConfidence=(double)d/100;
		
		this.minConfidence=minimumConfidence;
		System.out.println("\n minConf = " +minConfidence+"\n");
	}
	public double getConfidence()
	{
		return this.minConfidence;
	}

	//getFrequency of all elements in the list exist in a all of records in the all data. 
public int getSupportCount(List<String> arrListForSearch,List<List<String>>rec)
{	
	int support =0;
	for(int i=0;i<rec.size();i++)
	{
		int count=0;
		//outer loop to get the List<String> of each row
		for(int j=0;j< arrListForSearch.size();j++)
		{
			if( rec.get(i).contains(arrListForSearch.get(j)))
				count++;						
		}
		// if each element in the arrListForSearch exists in the current row of the all records
		if(count==arrListForSearch.size())
			support++;			
		}
		return support;
}	//fill the  distnctValues
public void setDistinctValuesList()
{
	for(int i=0;i<records.size();i++)
	{
		String temp="";
		for(int j=0;j<records.get(i).size();j++)

		{
			temp=records.get(i).get(j);
			List<String> tempList=new ArrayList<String>();
			tempList.add(temp);
			if(!(distnctValues.contains(temp))&& getSupportCount(tempList, records)>=getMinSupport())
				distnctValues.add(temp);
		}
	}
	System.out.println(distnctValues.size()+"\n");
	System.out.println("distnct values = "+distnctValues+"\n" );
	}	//end of function setDistinctValuesList
		
public void transformData(){
		//this function is to store the coffee shop in an arraylist<List<String>>.
			//String for each line
		String s="";
		int [] neededColumnsInFile= {3,4,5};
		numberOfNeededColumnsInFile=neededColumnsInFile.length;
	       // Strings for each considerable record      
			String currentItem="";
			for(int i=0;i<dataInTheFile.size();i++)  
	        { 	        	
	        	//to skip the inconsiderable data
	        	s=Arrays.toString(dataInTheFile.get(i));
	        	String splittedString[]=s.split(";");
	        	// split the whole string to parts based on each ";" in the string
	        	List<String> buffer=new ArrayList<String>();
	        	// buffer to carry the needed data of each row
	        	for(int c=0;c<numberOfNeededColumnsInFile;c++)
	        	{
	        		currentItem=splittedString[   neededColumnsInFile[c] ]  ;
	        		if(c ==numberOfNeededColumnsInFile-1)
	        			currentItem=currentItem.substring(0,currentItem.indexOf(']'));
	        		
	        		buffer.add(currentItem);
	        	}
	        records.add(buffer);
	        }	//end of outer loop

			System.out.println("\n        		      A		P		R		I		O 		R 		I    		 \n");
			System.out.println("#of columns ="+numberOfNeededColumnsInFile);
			
			setDistinctValuesList();
	}	//end of function transformData.

	//generate item sets. it takes an set and make n joins on that set
public int factorial(int n)
{
	if(n<=1)
		return 1;
	return factorial(n-1);
}
public int getCombinations(int n,int r)
{
	//calculate all combination of n values to generate available r-Itemset
	int result=factorial(n)/(factorial(r)*factorial(n-r));
	
	return result;
}
public List<List<String>> generateNItemSet(int NofItemSet,List<String>distnctSet)
	{
		// nCr is the number of all available combinations
		int nCr=getCombinations(distnctSet.size(),NofItemSet);
			
		List<List<String>> itemSetBuffer=new ArrayList<List<String>>();
		String tempElem="";
		for(int currDistElem =0; currDistElem<distnctSet.size()-NofItemSet+1;currDistElem++)
		{
			for(int c=0;c<nCr;c++)
			{
				List<String> rowBuffer=new ArrayList<String>();
				rowBuffer.add(distnctSet.get(currDistElem));
				int currDistElemBeforeIncrement=currDistElem;
				for(int n=1;n < NofItemSet ; n++)
				{
				
					//System.out.println("n= "+ n +" , NofITemSet = "+NofItemSet+" , currDistElem = "+currDistElem );
					tempElem=distnctSet.get(++currDistElem);
					rowBuffer.add(tempElem);
				}
				currDistElem=currDistElemBeforeIncrement;
			
				//System.out.println("rowBuffer= "+rowBuffer);
				
				System.out.println("support ="+getSupportCount(rowBuffer,records) +" , minimum support = "+getMinSupport());
				if(getSupportCount(rowBuffer,records)>=getMinSupport())
				{
					itemSetBuffer.add(rowBuffer);
				}
			}
		}
		
		System.out.println("\n FinalItemSetBuffer ="+itemSetBuffer);
		System.out.println("\n \n \n ");
		return itemSetBuffer;
		
	}//end of generateNItemSet
	
	int numberOfFrequentSet=1;
	
	List<List<String>>frequentItemSet=new ArrayList<List<String>>();
	
	
public void setfrequentItemSet()
{ 
	

	
	//generateNItemSet(1,distnctValues);

	for(;numberOfFrequentSet<numberOfNeededColumnsInFile;numberOfFrequentSet++)
	{
		System.out.println(numberOfFrequentSet+"-Item Set is : \n");
		this.frequentItemSet=generateNItemSet(numberOfFrequentSet,distnctValues);
		if(this.frequentItemSet.size()==0) {
			if(numberOfFrequentSet-1 ==1)
			{
				this.frequentItemSet=generateNItemSet(0,distnctValues);
				break;				
			}
			else
			{
				this.frequentItemSet=generateNItemSet(numberOfFrequentSet-1,distnctValues);
				System.out.println("withBreak frequentItemSet.size = "+frequentItemSet.size() +" numberOfFrequentSet = "+numberOfFrequentSet);	
				break;
			}
			
		}
	}
	//System.out.println(" withoutBreak frequentItemSet.size = "+frequentItemSet.size() +"  numberOfFrequentSet ="+numberOfFrequentSet);

	
	
}

	
public boolean calculateConfidence(int suppA,int supp_AuB)
{
	double currentConfidence=(double)((1.0*supp_AuB)/(1.0*suppA));
	
	// confidence(A->B)= (support(A u B) )/ support(A)
	System.out.println("supp_AuB "+supp_AuB);
	System.out.println("suppA "+suppA);
	System.out.println("curr Conf is "+currentConfidence);
	System.out.println("minConfidence"+minConfidence);
	
	
	
	/* doubles numbers cant be compared as integers numbers so
	/we've to use built in function Double.compare 
	which returns 0 if the to parameters are equal
    and returns a +ve if first parameter is greater than second parameter
    */
	if(Double.compare(currentConfidence,minConfidence)>=0)
	{
		System.out.println(" true");
		System.out.println("    ___    ");
		return true;
		
	}
	else
	{
		System.out.println(" false");
		System.out.println("    ___    ");

		return false;
	}
}//end of function calculateConfidence.

public void swapArrayLists(List<String>a,List<String>b)
{
	List<String> temp=new ArrayList<String>(a);
	a.clear();
	a.addAll(b);
	b.clear();
	b.addAll(temp);
}
public int getPermutation(int n , int r) {
	int result=factorial(n)/factorial(n-r);
	return result;
}
//getAllCombinations of the most Frequent Item Set
public	void getStrongCorrelatedItemSet()
{
	
	transformData();
	setfrequentItemSet();
	int permutation=frequentItemSet.size() *getPermutation(frequentItemSet.get(0).size(), 2);
			
	if(this.frequentItemSet==null) {
		System.out.println("frequent set is null");
	}
	else
	{
	System.out.println("i'm getStrongCorrelated function and size of frequentSet is :"+this.frequentItemSet.size());
	System.out.println("FREQUENT SET = "+ frequentItemSet);
	for(int row=0;row<permutation;row++)
	{
		//this outer loop is for each line in the freqSet
		//in each confidence calculations there are A->B
		int suppForA=0 ;
		int suppForAuB=0;
		//calculating suppForAuB for the current row data
		
		suppForAuB=getSupportCount( this.frequentItemSet.get(row),records );
				
		for(int col=0; col<this.frequentItemSet.get(row).size();col++)
		{
			//this inner loop is for each element in the current row data in freqSet
			
			//to Add all data of each row in freqSet to List<String>B.
			List<String>B=new ArrayList<String>();
			B.addAll(this.frequentItemSet.get(row));
			
			
			//to pick a current element and remove it from B to add it to A. 
			String a=this.frequentItemSet.get(row).get(col);
			System.out.println("a = "+ a);
			B.remove(a);
			System.out.println("B = "+ B);
			List<String>A=new ArrayList<String>();
			A.add(a);
								
			//calculate suppForA for the current element
				suppForA=getSupportCount(A,records);
				//calculateConfidence(suppForA,suppForAuB)
			if(calculateConfidence(suppForA,suppForAuB))
			{
				String Astring=A.toString();
				String Bstring=B.toString();
				String status="";
				status+=Astring;
				status+="  - > ";
				status+=Bstring;
				status+="   is strongly Correlated ";
				
				stronglyCorrelatedList.add(status);
			}
			// to swap A with B to get all combinations
			swapArrayLists(A,B);
			
			//calculate suppForA for the current element
			suppForA=getSupportCount(A,records);
			
		if(calculateConfidence(suppForA,suppForAuB))
		{
			String Astring=A.toString();
			String Bstring=B.toString();
			String status="";
			status+=Astring;
			status+=" ----- > ";
			status+=Bstring;
			status+="      is strongly Correlated \n \n";
			
			this.stronglyCorrelatedList.add(status);
		}
			
			
			
		}
	}
	}

	
}// end of getStrongCorrelated.

//print the strongly correlated items.
public void printStrongCorrelatedItems ()
{
	getStrongCorrelatedItemSet();
	System.out.println("\n \n \nfrequentSet= "+frequentItemSet+"\n \n" );
	System.out.println("___________________________________________________");
	System.out.println();
	System.out.println("number of strong association rules is :"+stronglyCorrelatedList.size());
	System.out.println("Strong Association Rules are :");
	for(int i=0;i<this.stronglyCorrelatedList.size();i++)
	{
		
		System.out.println(stronglyCorrelatedList.get(i));
	}
}//end of print printStrongCorrelatedItems

//end of Class Controller.	
}
	
import java.util.Scanner;

public class Main 
{
	private static Scanner s;

	public static void main(String[] args) 
	{
		Controller controller=new Controller();
		
		s = new Scanner(System.in);
		System.out.print("Enter minimum support in % : ");
		int supPercentage=s.nextInt();
		System.out.print("Enter minimum confidence in % :");
		int confPercentage=s.nextInt();
		
		controller.setMinSupport(supPercentage);
		controller.setConfidence(confPercentage);
		//System.out.println("2-ItemSet ="+controller.generateNItemSet(2, controller.distnctValues));
		System.out.println("Frequent Item Set size ="+controller.frequentItemSet.size());
		
		System.out.println("Frequent Item Set  ="+controller.frequentItemSet); 
		controller.printStrongCorrelatedItems();
			

	}

}

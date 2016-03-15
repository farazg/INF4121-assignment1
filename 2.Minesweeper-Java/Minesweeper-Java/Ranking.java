import java.util.Scanner;

public class Ranking{

	private final int MAX_PEOPLE_LIMIT=5;
	private String[] name;
	private int[] record;
	private int last;
	
	Ranking(){
		name=new String[MAX_PEOPLE_LIMIT];
		record=new int[MAX_PEOPLE_LIMIT];
		
		last=0;
	}

	/*
	 * Records player name and score. 
	 * Does not record if list is full and players result did not beat a previous players result.
	 */
	public void recordName(int result) {
		System.out.print("\n Please enter your name -");
		Scanner in=new Scanner(System.in);
		String newName=in.nextLine();
		if((last==MAX_PEOPLE_LIMIT)&&record[MAX_PEOPLE_LIMIT-1]>result){ //player cannot be added
			System.out.println("\nSorry you cannot enter top "+(MAX_PEOPLE_LIMIT)+" players");
			return;
		}
		else if(last==MAX_PEOPLE_LIMIT){
			name[MAX_PEOPLE_LIMIT-1]=newName;{
				record[MAX_PEOPLE_LIMIT-1]=result;
			}
		}
		else{ //add player
			name[last]=newName;{
				record[last]=result;{
					last++;
				}
			}
		}
		
		sort();
		show();
	}

	/*
	 * returns the high score list
	 */
	public void show() {
		if(last==0){
			System.out.println("Still no results");
			return;
		}
		System.out.println("N Name\t\tresult");
		for(int i=0;i<last;i++){
			System.out.println((i+1)+" "+name[i]+"\t"+record[i]);
		}
	}
	
	/*
	 * Sorts the high score list based on points
	 */
	private void sort(){
		if(last<2) return;
		boolean unsorted=true;
		while(unsorted){
			unsorted=false;
			for(int i=0;i<(last-1);i++){
				unsorted = sortingStatements(i, unsorted);
			}
		}
	}
	
	private boolean sortingStatements(int i, boolean unsorted){
		if(record[i+1]>record[i]){
			int swapR=record[i];{
				record[i]=record[i+1];{
					record[i+1]=swapR;
					String swapN=name[i];
					name[i]=name[i+1];
					name[i+1]=swapN;
					unsorted=true;
				}
			}
		}
		return unsorted;
	}
}

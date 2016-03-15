import java.util.Random;

class MineField{

	private boolean[][] mines,visible;
	private boolean boom;
	private final int rowMax = 5;
	private final int colMax = 10;
	
	MineField(){
		
		mines=new boolean[rowMax][colMax];
		visible=new boolean[rowMax][colMax];
		boom=false;

		initMap();
		
		int counter2=15;
		int randomRow,randomCol;
		Random RGenerator=new Random();
		
		//This method fills counter2 columns with mines
		while(counter2>0){
			
			randomRow=Math.abs(RGenerator.nextInt()%rowMax);
			randomCol=Math.abs(RGenerator.nextInt()%colMax);
			
			if(trymove(randomRow,randomCol)){
				counter2--;
			}
		}
	}

	/*
	 * This methods initiates the field-arrays visible and mines.
	 * The values are all false by default.
	 */
	private void initMap(){
		for(int row=0;row<rowMax;row++){
			for(int col=0;col<colMax;col++){
				mines[row][col]=false;
				visible[row][col]=false;
			}
		}
	}
	
	/*
	 * This method is used when filling the field with mines.
	 * If the column is empty return true, otherwise false.
	 */
	private boolean trymove(int randomRow, int randomCol) {
		if(mines[randomRow][randomCol]){
			return false;
		}
		else{
			mines[randomRow][randomCol]=true;
			return true;
		}
	}
	
	/*
	 * This method is run when a user clicks a mine. All the mine columns are put as visible, 
	 * and the boom variable is set to true, to indicate loss. The show()-method is then run.
	 */
	private void boom() {
		for(int row=0;row<rowMax;row++){
			for(int col=0;col<colMax;col++){
				if(mines[row][col]){
					visible[row][col]=true;
				}
			}
		}
		boom=true;
		show();
	}

	/*
	 * This method is used to draw the field in the console.
	 * returns '?' if the column is not visible yet, '-' if the user has lost and the column was not visible,
	 * and '*' for every column with a mine, if the user has lost
 	 */
	private String drawChar(int row, int col) {
		int count=0;
		if(visible[row][col]){
			if(mines[row][col]) return "*"; //if column is a mine, return '*', this is used when user has lost
			count = this.getCount(count, row, col);
		}
		else{ //returns a '-' if the user lost and the column does not contain a mine
			  // and it returns a '?' when the user is still playing and the column is not yet visible
			if(boom){
				return "-";
			}
			else{
				return "?";
			}
		}
		
		return this.getNumberOfSurroundingMines(count);
	}
	
	/*
	 * This method calculates the amount of mines surrounding a column
	 */
	private int getCount(int count, int row, int col){
		for(int irow=row-1;irow<=row+1;irow++){
			for(int icol=col-1;icol<=col+1;icol++){
				if(validAndContainsMine(irow, icol)){
					count++;
				}
			}
		}
		
		return count;
	}
	
	private boolean validAndContainsMine(int irow, int icol){
		if(icol>=0&&icol<colMax&&irow>=0&&irow<rowMax){
			if(mines[irow][icol]) return true;
		}
		return false;
	}
	
	/*
	 * This method returns the number of mines surrounding a column
	 */
	private String getNumberOfSurroundingMines(int count)
	{
		if(count < 0 || count > 8)
			return "X";
		else
			return "" + count;
	}
	
	/*
	 * This method returns the value of variable boom
	 */
	public boolean getBoom(){
		
		return boom;
	}
	
	/*
	 * This method takes the input string and checks if the move is within bounds 0 and rowMax, 0 and colMax.
	 * It also checks whether the column chosen by the user contains a mine or not, in which case the user loses.
	 */
	public boolean legalMoveString(String input) {
		
		String[] separated=input.split(" ");
		int row;
		int col;
		
		try{
			row=Integer.parseInt(separated[0]);
			col=Integer.parseInt(separated[1]);
			if(row<0||col<0||row>=rowMax||col>=colMax){
				throw new java.io.IOException();
			}
		}
		catch(Exception e){
			System.out.println("\nInvalid Input!");
			return false;
		}
		
		if(legalMoveValue(row,col)){
			return true;
		}
		else{
			return false;
		}
	}

	/*
	 * Check if chosen column is set to to visible, and if the column contains a mine or not.
	 */
	private boolean legalMoveValue(int row, int col) {
		
		if(visible[row][col]){
			System.out.println("You stepped in allready revealed area!");
			return false;
		}
		else{
			visible[row][col]=true;
		}
		
		if(mines[row][col]){
			boom();
			return false;
		}
		
		return true;
	}
	
	/*
	 * This method prints the field to the console.
	 */
	public void show() {
		System.out.println("\n    0 1 2 3 4 5 6 7 8 9 ");
		System.out.println("   ---------------------");
		for(int row=0;row<rowMax;row++){
			System.out.print(row+" |");
			for(int col=0;col<colMax;col++){
				System.out.print(" "+drawChar(row,col));
				
			}
			System.out.println(" |");
		}
		System.out.println("   ---------------------");
	}
	
}

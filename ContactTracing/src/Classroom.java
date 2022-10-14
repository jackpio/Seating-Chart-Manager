
public class Classroom {
	  static Student[][] prevChart;
	  static Student[][] seatChart;
	  private static int rows;
	  private static int columns;
	  
	  public Classroom() {
		  seatChart = new Student[5][5];
		  rows = 0;
		  columns = 0;
	  }

	  public Classroom(int r, int c){
	    seatChart = new Student[r][c];
	    rows = r;
	    columns = c;
	  }

	  //adds student to the seating chart
	  public void addStudent(int r, int c, String n, int g){
	    if (seatChart[r][c] == null)
	      seatChart[r][c] = new Student(n,g);
	    else
	      System.out.println("Seat Filled. Pick an unfilled seat.");
	  }

	  //creates a backup of the old chart
	  //allows for copying into new chart
	  public void backupChart(){
	    prevChart = new Student[rows][columns];
	    for(int i = 0; i < seatChart.length; i++){
	      for(int z = 0; z < seatChart[i].length; z++){
	        prevChart[i][z] = seatChart[i][z];
	      }
	    }
	  }
	  
	  //sets a certain seat as null
	  public void removeStudent(int r, int c){
	      seatChart[r][c] = null;
	  }
	  
	  //swaps student at (r,c) with student at (r2,c2)
	  public void swapSeat(int r, int c, int r2, int c2){
	         Student temp = seatChart[r][c];
	         removeStudent(r,c);
	         seatChart[r][c] = seatChart[r2][c2];
	         seatChart[r2][c2] = temp; 
	  }

	  //adds a new row to the chart
	  //copies old chart into new
	  public void addRow(){
	    backupChart();
	    rows++;
	    seatChart = new Student[rows++][columns];
	    for(int i = 0; i < prevChart.length; i++){
	      for(int z = 0; z < prevChart[i].length; z++){
	        seatChart[i][z] = prevChart[i][z];
	      }
	    }
	  }

	  //adds a new column to the chart
	  //copies old chart into new
	  public void addColumn(){
	    backupChart();
	    columns++;
	    seatChart = new Student[rows][columns++];
	    for(int i = 0; i < prevChart.length; i++){
	      for(int z = 0; z < prevChart[i].length; z++){
	        seatChart[i][z] = prevChart[i][z];
	      }
	    }
	  }

	  //marks a student as Covid positve
	  //marks surronding students as close contact
	  public void changeCovidStatus(String name){
	     for(int i = 0; i < seatChart.length; i++){
	         for(int z = 0; z < seatChart[i].length; z++){
	            if(seatChart[i][z] != null){
	               if(seatChart[i][z].getName() == name && seatChart[i][z].getCovid() == false){
	                  seatChart[i][z].setCovid(true);
	                  updateCloseContact(i,z);
	               }
	               else if(seatChart[i][z].getName() == name && seatChart[i][z].getCovid() == true)
	                  seatChart[i][z].setCovid(false);
	            }
	         }
	     } 
	  }
	  
	  //passes a name in as a parameter
	  //searches for the name within the seating chart
	  //changes the located student's covid status
	  public String accessCovidStatus(String name){
	     for(int i = 0; i < seatChart.length; i++){
	         for(int z = 0; z < seatChart[i].length; z++){
	            if(seatChart[i][z].getName() == name){
	               if (seatChart[i][z].getCovid() == true)
	                  return "Positive";
	               else
	                  return "Negative";
	            }
	         }
	     } 
	     return "Student not found.";
	  }
	  
	  //changes the status of those sitting near a covid positive student
	  //it will mark those students to have been in "close contact" with COVID
	  public void updateCloseContact(int r, int c){
	  
	      if(inBounds(r-1,c))
	         {
	            if(seatChart[r-1][c] != null){
	                seatChart[r-1][c].setCC(true);
	            }
	         }
	         
	         if(inBounds(r-1,c-1))
	         {        
	            if(seatChart[r-1][c-1] != null){
	                seatChart[r-1][c-1].setCC(true);
	            }
	         }
	         
	         if(inBounds(r-1,c+1))
	         {        
	            if(seatChart[r-1][c+1] != null){
	                seatChart[r-1][c+1].setCC(true);
	            }
	         }
	         
	         if(inBounds(r,c-1))
	         {        
	            if(seatChart[r][c-1] != null){
	                seatChart[r][c-1].setCC(true);
	            }
	         }   
	            
	         if(inBounds(r,c+1))
	         {        
	            if(seatChart[r][c+1] != null){
	                seatChart[r][c+1].setCC(true);
	            }
	         }
	            
	         if(inBounds(r+1,c-1))
	         {        
	            if(seatChart[r+1][c-1] != null){
	                seatChart[r+1][c-1].setCC(true);
	            }
	         }
	            
	         if(inBounds(r+1,c))
	         {        
	            if(seatChart[r+1][c] != null){
	                seatChart[r+1][c].setCC(true);
	            }
	         }
	            
	         if(inBounds(r+1,c+1))
	         {       
	            if(seatChart[r+1][c+1] != null){
	                seatChart[r+1][c+1].setCC(true);
	            }
	         }
	  
	  }
	  
	  //checks to see if a certain row, column combination is in bounds
	  private boolean inBounds( int r, int c)
	   {
	      if(r >=0 && r < seatChart.length && c >=0 && c < seatChart[0].length)
	         return true; 
	      return false;
	   }
	  
	  //name passed in as parameter
	  //method searches for the name, removes the person from the quarantine list 
	  public void removeFromCC(String name){
	      for(Student[] row: seatChart){
	         for(Student select: row){
	            if(name.equals(select.getName())){
	               select.setCC(false);
	            }
	         }
	      }
	  }
	  
	  public static Student[][] getSeatChart() {
		  return seatChart;
	  }
	  
	  //Displays the seating chart
	  public String toString(){
	      String output = "";
	      for(int i = 0; i < seatChart.length; i++){
	         for(int z = 0; z < seatChart[i].length; z++){
	            if(seatChart[i][z] != null)
	               output += "[" + seatChart[i][z].getName() + "] ";
	            if(seatChart[i][z] == null)
	               output += "[Unfilled] ";
	      }
	      output += "\n";
	    }
	    return output;
	  }
}


public class Student {
	private String name;
	private int grade;
	private boolean covid;
	private boolean closeContact;
	  
	public Student(){
	   name = "";
	   grade = 0;
	   covid = false;
	   closeContact = false; 
	}
	  
	public Student(String n, int g){
	    name = n;
	    grade = g;
	}

	public Student(String n, int g, boolean c, boolean cc){
	    name = n;
	    grade = g;
	    covid = c;
	    closeContact = cc; 
	}

	public void setName(String s){
	     name = s; 
	}

	  public String getName(){
	    return name;
	  }

	  public void setGrade(int s){
	    grade = s;
	  }

	  public int getGrade(){
	    return grade;
	  }

	  public void setCovid(boolean b){
	    covid = b;
	  }

	  public boolean getCovid(){
	    return covid;
	  }

	  public void setCC(boolean b){
	    closeContact = b;
	  }

	  public boolean getCC(){
	    return closeContact; 
	  }
}

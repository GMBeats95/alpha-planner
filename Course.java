public class Course{
	private String name;
	private String meetingTime;
	private String[][] assignments = new String[10][2];
	
	// Constructors
	public Course(){
		name = " ";
		meetingTime = " ";
	}
	
	public Course(String a, String b){
		name = a;
		meetingTime = b;
	}
	
	public Course(String a, String b, String[][] c){
		name = a;
		meetingTime = b;
		System.arraycopy(c, 0, assignments, 0, c.length);
	}
	
	// Accessor/Mutator methods
	public void setName(String crsName){
		name = crsName;	
	}
	
	public void setTime(String time){
		meetingTime = time;
	}	
	
	public void setAssignments(String assign, String time){
		
		for(int i = 0; i < assignments.length; i++){
			// add data in first array 'slot' that is empty
			if(assignments[i][1] == null){
				assignments[i][0] = assign;
				assignments[i][1] = time;
				break;
			}else{continue;}	
		}
	}
	
	// Sets a specific element of the array
	public void setAssignments(int i, String assign, String time){
		assignments[i][0] = assign;
		assignments[i][1] = time;
	}
	
	public String getName(){
		return name;	
	}
	
	public String getTime(){
		return meetingTime;	
	}
	
	public String[][] getAssignments(){
		return assignments;	
	}
	
	// Returns a specific element of the assignments array
	public String getAssignments(int a, int b){
		String value;
		return assignments[a][b];	
	}
	

}
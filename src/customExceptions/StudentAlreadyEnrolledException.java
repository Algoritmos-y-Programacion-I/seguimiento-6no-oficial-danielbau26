package customExceptions;

public class StudentAlreadyEnrolledException extends Exception {
    
    public StudentAlreadyEnrolledException(String id){
        super("The student with ID " + id + " is already enrolled in the course");
    }
}

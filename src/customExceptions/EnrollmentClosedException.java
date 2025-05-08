package customExceptions;

public class EnrollmentClosedException extends Exception{
    
    public EnrollmentClosedException(int week){
        super("Enrollment is closed. Current week is " + week + ", and enrollment is only allowed until week 2.");
    }
}

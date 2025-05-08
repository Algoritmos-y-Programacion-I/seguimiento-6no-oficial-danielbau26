package customExceptions;

public class CancellationNotAllowedException extends Exception {
    
    public CancellationNotAllowedException(){
        super("The student can't be unenrolled because more than 50% of the course grades have already been assigned.");
    }
}

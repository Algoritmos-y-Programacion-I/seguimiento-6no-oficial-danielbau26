package model;

import customExceptions.CancellationNotAllowedException;
import customExceptions.EnrollmentClosedException;
import customExceptions.OutOfRangeGradeException;
import customExceptions.QuotaEnrollExceedException;
import customExceptions.StudentAlreadyEnrolledException;

public class Course {
	private double maxGrade;
	private double minGrade;
	private int currentWeek;
	private int totalGradesAmount;
	private int maxQuota;

	private Student[] studentsEnrolled;

	public Course(int tga, double mx, double mn, int mq) {
		currentWeek = 1;
		maxGrade = mx;
		minGrade = mn;
		totalGradesAmount = tga;
		maxQuota = mq;

		studentsEnrolled = new Student[mq];
	}

	public void enroll(String id) throws QuotaEnrollExceedException, EnrollmentClosedException, StudentAlreadyEnrolledException {
		boolean studentExist = doesStudentExist(id);

		if (studentExist == true){
			throw new StudentAlreadyEnrolledException(id);
		}

		if (currentWeek > 2){
			throw new EnrollmentClosedException(currentWeek);
		}

		int posNewStudent = searchFirstAvailable();

		if (posNewStudent == -1) {
			throw new QuotaEnrollExceedException(maxQuota);
		} else {
			studentsEnrolled[posNewStudent] = new Student(id, totalGradesAmount);
		}
		
	}

	public boolean doesStudentExist(String id){
		boolean flag = false;
		for (int i = 0; i<studentsEnrolled.length; i++){
			Student student = studentsEnrolled[i];
			String idStudent = student.getId();
			if (idStudent.equals(id)){
				flag = true;
				break;
			}
		}
		return flag;
	}

	public void cancelEnrollment(String id) throws CancellationNotAllowedException{
		int posStudent = searchStudent(id);
		int amountGrades = countStudentGrades(id);

		if (amountGrades > (totalGradesAmount/2)){
			throw new CancellationNotAllowedException();
		} else {
			studentsEnrolled[posStudent] = null;
		}
	}

	public int countStudentGrades(String id){
		int amountGrades = 0;

		for (int i = 0; i<studentsEnrolled.length; i++){
			Student student = studentsEnrolled[i];
			String idStudent = student.getId();
			if (idStudent.equals(id)){
				amountGrades = student.amountGrades();
				break;
			}
		}

		return amountGrades;
	}

	public void setStudentGrade(String id, int gradeNumber, double grade)
			throws ArrayIndexOutOfBoundsException, OutOfRangeGradeException {
		if (grade < minGrade || grade > maxGrade) {
			throw new OutOfRangeGradeException(grade, maxGrade, minGrade);
		}

		if (gradeNumber < 1 || gradeNumber > totalGradesAmount){
			throw new ArrayIndexOutOfBoundsException();
		}

		int posStudent = searchStudent(id);
		studentsEnrolled[posStudent].setGrade(gradeNumber, grade);
	}

	public void advanceWeek() {
		currentWeek++;
	}

	private int searchFirstAvailable() {
		int pos = -1;
		for (int i = 0; i < studentsEnrolled.length && pos == -1; i++) {
			Student current = studentsEnrolled[i];
			if (current == null) {
				pos = i;
			}
		}

		return pos;
	}

	private int searchStudent(String id) {
		int pos = -1;

		for (int i = 0; i < studentsEnrolled.length && pos == -1; i++) {
			Student current = studentsEnrolled[i];
			if (current != null) {
				if (id.contentEquals(current.getId())) {
					pos = i;
				}
			}
		}

		return pos;
	}

	public String showEnrolledStudents() {

		String msg = "";

		for (int i = 0; i < studentsEnrolled.length; i++) {

			if (studentsEnrolled[i] != null) {

				msg += studentsEnrolled[i].getId()+"\n";

			}

		}

		return msg;

	}

	public String showStudentGrades(String id) {

		int pos = searchStudent(id);

		if (pos != -1) {

			return studentsEnrolled[pos].showGrades();

		}

		return "Error";

	}
}

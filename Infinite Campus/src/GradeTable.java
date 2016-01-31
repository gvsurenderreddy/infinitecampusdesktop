import javafx.beans.property.SimpleStringProperty;

public class GradeTable {
    private final SimpleStringProperty time;
	private final SimpleStringProperty course;
    private final SimpleStringProperty assignment;
    private final SimpleStringProperty grade;

    public GradeTable(String time, String course, String assignment, String grades) {
        this.time = new SimpleStringProperty(time);
    	this.course = new SimpleStringProperty(course);
        this.assignment = new SimpleStringProperty(assignment);
        this.grade = new SimpleStringProperty(grades);
    }
    public String getTime() {
        return time.get();
    }
    public void setTime(String time) {
        this.time.set(time);
    }

    public String getCourse() {
        return course.get();
    }
    public void setCourse(String course) {
        this.course.set(course);
    }

    public String getAssignment() {
        return assignment.get();
    }
    public void setAssignment(String assignment) {
        this.assignment.set(assignment);
    }

    public String getGrade() {
        return grade.get();
    }
    public void setGrade(String grades) {
        grade.set(grades);
    }

}
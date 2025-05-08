package Smart;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Task implements Serializable {
    public enum Status { COMPLETED, PENDING }
    public enum Priority { LOW, MEDIUM, HIGH }

    private static final long serialVersionUID = 1L;

    private LocalDate deadLine;
    private String name;
    private String description;
    private Status status;
    private Priority priority;
    private String owner; // 👈 NEW FIELD

    // Default constructor
    public Task() {
        this.deadLine = LocalDate.now();
        this.name = "";
        this.description = "";
        this.status = Status.PENDING;
        this.priority = Priority.MEDIUM;
        this.owner = "";
    }

    // Parameterized constructor
    public Task(String name, String description, LocalDate deadline, Status status, Priority priority, String owner) {
        this.name = name;
        this.description = description;
        this.deadLine = deadline;
        this.status = status;
        this.priority = priority;
        this.owner = owner;
    }

    // Setters
    public void setDeadLine(LocalDate d) { this.deadLine = d; }
    public void setName(String n) { this.name = n; }
    public void setDescription(String des) { this.description = des; }
    public void setStatus(Status s) { this.status = s; }
    public void setPriority(Priority p) { this.priority = p; }
    public void setOwner(String owner) { this.owner = owner; }

    // Getters
    public LocalDate getDeadLine() { return deadLine; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Status getStatus() { return status; }
    public Priority getPriority() { return priority; }
    public String getOwner() { return owner; }

    // Override equals and hashCode for use in Sets
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return name.equals(task.name) && owner.equals(task.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, owner);
    }

    @Override
    public String toString() {
        return "Task[name=" + name + ", owner=" + owner + ", status=" + status + ", deadline=" + deadLine + "]";
    }
}

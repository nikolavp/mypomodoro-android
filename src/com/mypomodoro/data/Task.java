package com.mypomodoro.data;

import java.util.Date;

public class Task {
	public static final String NAME = "name";
	public static final String _ID = "_id";
	
	private String name;
	private TaskType type;
	private Date deadline;
	private Date dateCreated;
	private int estimatedPomodoros;

	public Task() {
		this.type = TaskType.NORMAL;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TaskType getType() {
		return type;
	}

	public void setType(TaskType type) {
		this.type = type;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public int getEstimatedPomodoros() {
		return estimatedPomodoros;
	}

	public void setEstimatedPomodoros(int estimatedPomodoros) {
		this.estimatedPomodoros = estimatedPomodoros;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dateCreated == null) ? 0 : dateCreated.hashCode());
		result = prime * result
				+ ((deadline == null) ? 0 : deadline.hashCode());
		result = prime * result + estimatedPomodoros;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		if (dateCreated == null) {
			if (other.dateCreated != null)
				return false;
		} else if (!dateCreated.equals(other.dateCreated))
			return false;
		if (deadline == null) {
			if (other.deadline != null)
				return false;
		} else if (!deadline.equals(other.deadline))
			return false;
		if (estimatedPomodoros != other.estimatedPomodoros)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

}

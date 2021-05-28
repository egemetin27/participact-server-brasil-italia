package it.unibo.paserver.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class TaskUser implements Serializable {
	private static final long serialVersionUID = -4339350368096489878L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "user_id")
	private User owner;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Task task;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.REFRESH })
	private Set<User> usersToAssign;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private TaskValutation valutation = TaskValutation.PENDING;	

	public Set<User> getUsersToAssign() {
		return usersToAssign;
	}

	public void setUsersToAssign(Set<User> usersToAssign) {
		this.usersToAssign = usersToAssign;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public TaskValutation getValutation() {
		return valutation;
	}

	public void setValutation(TaskValutation valutation) {
		this.valutation = valutation;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}

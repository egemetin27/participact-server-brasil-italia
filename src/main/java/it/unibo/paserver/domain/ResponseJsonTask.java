package it.unibo.paserver.domain;

import java.util.ArrayList;
import java.util.List;

import it.unibo.paserver.domain.flat.TaskFlat;
/**
 * Classe para respostas padrao das APIs
 * @author Claudio
 * @param <E>
 */
public class ResponseJsonTask extends ResponseJsonRest{
	private List<TaskFlat> tasks = new ArrayList<TaskFlat>();

	public List<TaskFlat> getTasks() {
		return tasks;
	}

	public void setTasks(List<TaskFlat> tasks) {
		this.tasks = tasks;
	}
}
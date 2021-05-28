package it.unibo.paserver.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TaskList implements Serializable {

	private static final long serialVersionUID = 1169293778999604162L;

	private List<Task> _list;

	public TaskList() {
		_list = new ArrayList<Task>();
	}

	public TaskList(List<Task> list) {
		_list = list;
	}

	public List<Task> getList() {
		return _list;
	}

	public void setList(List<Task> _list) {
		this._list = _list;
	}

}

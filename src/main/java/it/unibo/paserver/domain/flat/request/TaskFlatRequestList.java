package it.unibo.paserver.domain.flat.request;

import java.util.ArrayList;
import java.util.List;

public class TaskFlatRequestList {

	private List<TaskFlatRequest> _list;

	public TaskFlatRequestList() {
		_list = new ArrayList<TaskFlatRequest>();
	}

	public TaskFlatRequestList(List<TaskFlatRequest> list) {
		_list = list;
	}

	public List<TaskFlatRequest> getList() {
		return _list;
	}

	public void setList(List<TaskFlatRequest> _list) {
		this._list = _list;
	}

}

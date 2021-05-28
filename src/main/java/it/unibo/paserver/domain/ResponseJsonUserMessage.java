package it.unibo.paserver.domain;

import java.util.ArrayList;
import java.util.List;

import it.unibo.paserver.domain.rest.UserMessageRestResult;

/**
 * Classe para respostas padrao das APIs
 * 
 * @author Claudio
 * @param <E>
 */
public class ResponseJsonUserMessage extends ResponseJsonRest {
	private List<UserMessageRestResult> posts = new ArrayList<UserMessageRestResult>();

	public List<UserMessageRestResult> getPosts() {
		return posts;
	}

	public void setPosts(List<UserMessageRestResult> msg) {
		this.posts = msg;
	}
}
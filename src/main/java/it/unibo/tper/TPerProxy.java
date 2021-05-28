package it.unibo.tper;

import it.unibo.tper.ws.domain.OpenDataLineeFermateResponse;
import it.unibo.tper.ws.domain.extensions.FermateResponse;


public interface TPerProxy {
	
	public FermateResponse getOpenDataLineeFermateResponse();

}

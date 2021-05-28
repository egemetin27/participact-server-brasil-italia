package it.unibo.paserver.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class SensorDetailList implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private ArrayList<SensorDetail> dl;
	
	private ArrayList<MinimalSensorDetail> mdl;

	public ArrayList<SensorDetail> getDl() {
		return dl;
	}

	public void setDl(ArrayList<SensorDetail> dl) {
		this.dl = dl;
	}

	public ArrayList<MinimalSensorDetail> getMdl() {
		return mdl;
	}

	public void setMdl(ArrayList<MinimalSensorDetail> mdl) {
		this.mdl = mdl;
	}

}

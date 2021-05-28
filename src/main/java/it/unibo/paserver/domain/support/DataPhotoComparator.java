package it.unibo.paserver.domain.support;

import it.unibo.paserver.domain.DataPhoto;

import java.util.Comparator;

public class DataPhotoComparator implements Comparator<DataPhoto> {

	@Override
	public int compare(DataPhoto o1, DataPhoto o2) {
		if (o1 == null) {
			return 1;
		}
		if (o2 == null) {
			return -1;
		}
		if (!o1.getUser().equals(o2.getUser())) {
			return o1.getUser().compareTo(o2.getUser());
		}
		return o1.getSampleTimestamp().compareTo(o2.getSampleTimestamp());
	}

}

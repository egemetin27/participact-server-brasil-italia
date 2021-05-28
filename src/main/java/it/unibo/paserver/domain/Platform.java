package it.unibo.paserver.domain;

public enum Platform {
	ANDROID, IOS, WINDOWS_PHONE;
	
	//could be useful in future?
	@Override
	public String toString() {
		switch (this) {
		case ANDROID:
			return "ANDROID";
		case IOS:
			return "IOS";
		case WINDOWS_PHONE:
			return "WINDOWS_PHONE";
		default:
			return "Unknown";
		}
	}
	
	
}



package it.unibo.paserver.domain;

import it.unibo.paserver.domain.support.Pipeline;
import it.unibo.paserver.domain.support.Pipeline.Type;

public enum SensorType {
	AUDIO_CLASSIFIER			  ("audio classifier", 				ActionType.SENSING_MOST, Pipeline.Type.AUDIO_CLASSIFIER), 
	ACCELEROMETER                 ("accelerometer",                 ActionType.SENSING_MOST, Pipeline.Type.ACCELEROMETER                ),
	ACCELEROMETER_CLASSIFIER      ("accelerometer classifier",      ActionType.SENSING_MOST, Pipeline.Type.ACCELEROMETER_CLASSIFIER     ),
	RAW_AUDIO                     ("raw audio",                     ActionType.SENSING_MOST, Pipeline.Type.RAW_AUDIO                    ),
	AVERAGE_ACCELEROMETER         ("average accelerometer",         ActionType.SENSING_MOST, Pipeline.Type.AVERAGE_ACCELEROMETER        ),
	APP_ON_SCREEN                 ("app on screen",                 ActionType.SENSING_MOST, Pipeline.Type.APP_ON_SCREEN                ),
	APPS_NET_TRAFFIC              ("apps net traffic",              ActionType.SENSING_MOST, Pipeline.Type.APPS_NET_TRAFFIC             ),
	BATTERY                       ("battery",                       ActionType.SENSING_MOST, Pipeline.Type.BATTERY                      ),
	BLUETOOTH                     ("bluetooth",                     ActionType.SENSING_MOST, Pipeline.Type.BLUETOOTH                    ),
	CELL                          ("cell",                          ActionType.SENSING_MOST, Pipeline.Type.CELL                         ),
	CONNECTION_TYPE               ("connection type",               ActionType.SENSING_MOST, Pipeline.Type.CONNECTION_TYPE              ),
	DEVICE_NET_TRAFFIC            ("device net traffic",            ActionType.SENSING_MOST, Pipeline.Type.DEVICE_NET_TRAFFIC           ),
	GOOGLE_ACTIVITY_RECOGNITION   ("google activity recognition",   ActionType.SENSING_MOST, Pipeline.Type.GOOGLE_ACTIVITY_RECOGNITION  ),
	GYROSCOPE                     ("gyroscope",                     ActionType.SENSING_MOST, Pipeline.Type.GYROSCOPE                    ),
	INSTALLED_APPS                ("installed apps",                ActionType.SENSING_MOST, Pipeline.Type.INSTALLED_APPS               ),
	LIGHT                         ("light",                         ActionType.SENSING_MOST, Pipeline.Type.LIGHT                        ),
	LOCATION                      ("location",                      ActionType.SENSING_MOST, Pipeline.Type.LOCATION                     ),
	MAGNETIC_FIELD                ("magnetic field",                ActionType.SENSING_MOST, Pipeline.Type.MAGNETIC_FIELD               ),
	PHONE_CALL_DURATION           ("phone call duration",           ActionType.SENSING_MOST, Pipeline.Type.PHONE_CALL_DURATION          ),
	PHONE_CALL_EVENT              ("phone call event",              ActionType.SENSING_MOST, Pipeline.Type.PHONE_CALL_EVENT             ),
	SYSTEM_STATS                  ("system stats",                  ActionType.SENSING_MOST, Pipeline.Type.SYSTEM_STATS                 ),
	WIFI_SCAN                     ("wifi scan",                     ActionType.SENSING_MOST, Pipeline.Type.WIFI_SCAN                    ),
	ACTIVITY_RECOGNITION_COMPARE  ("activity recognition compare",  ActionType.SENSING_MOST, Pipeline.Type.ACTIVITY_RECOGNITION_COMPARE ),
	DR                            ("dr",                            ActionType.SENSING_MOST, Pipeline.Type.DR                           ),
	TRAINING                      ("training",                      ActionType.SENSING_MOST, Pipeline.Type.TRAINING                     ),
	TEST                          ("test",                          ActionType.SENSING_MOST, Pipeline.Type.TEST                         ),
	DUMMY                         ("dummy",                         ActionType.SENSING_MOST, Pipeline.Type.DUMMY                        ),
	ACTIVITY_DETECTION            ("activity detection",            ActionType.ACTIVITY_DETECTION, null          ),
	PHOTO                         ("photo",                         ActionType.PHOTO             , null          ),
	QUESTIONNAIRE                 ("questionnaire",                 ActionType.QUESTIONNAIRE     , null          ),
	STEP_COUNTER                  ("step counter",                  ActionType.SENSING_MOST, Pipeline.Type.STEP_COUNTER                 ),
;
	
	private Type pipelineType;
	private String description;
	private ActionType actionType;
	
	public static SensorType getSensorCategory(int cat){
		switch (cat) {
		case 1:
			return ACCELEROMETER;
		case 2:
			return RAW_AUDIO;
		case 3:
			return AVERAGE_ACCELEROMETER;
		case 4:
			return APP_ON_SCREEN;
		case 5:
			return BATTERY;
		case 6:
			return BLUETOOTH;
		case 7:
			return CELL;
		case 8:
			return GYROSCOPE;
		case 9:
			return INSTALLED_APPS;
		case 10:
			return LIGHT;
		case 11:
			return LOCATION;
		case 12:
			return MAGNETIC_FIELD;
		case 13:
			return PHONE_CALL_DURATION;
		case 14:
			return PHONE_CALL_EVENT;
		case 15:
			return ACCELEROMETER_CLASSIFIER;
		case 16:
			return SYSTEM_STATS;
		case 17:
			return WIFI_SCAN;
		case 18:
			return AUDIO_CLASSIFIER;
		case 19:
			return DEVICE_NET_TRAFFIC;
		case 20:
			return APPS_NET_TRAFFIC;
		case 21:
			return CONNECTION_TYPE;
		case 22:
			return GOOGLE_ACTIVITY_RECOGNITION;
        case 23:
            return ACTIVITY_RECOGNITION_COMPARE;
		case 24:
			return DR;
		case 25:
			return TRAINING;
		case 26:
			return STEP_COUNTER;
		case 27:
			return QUESTIONNAIRE;
		case 28:
			return PHOTO;
		case 29:
			return ACTIVITY_DETECTION;
		case 99:
			return TEST;
		default:
			return DUMMY;
		}
	}
	
	public int toInt() {
		switch (this) {
		case ACCELEROMETER:
			return 1;
		case RAW_AUDIO:
			return 2;
		case AVERAGE_ACCELEROMETER:
			return 3;
		case APP_ON_SCREEN:
			return 4;
		case BATTERY:
			return 5;
		case BLUETOOTH:
			return 6;
		case CELL:
			return 7;
		case GYROSCOPE:
			return 8;
		case INSTALLED_APPS:
			return 9;
		case LIGHT:
			return 10;
		case LOCATION:
			return 11;
		case MAGNETIC_FIELD:
			return 12;
		case PHONE_CALL_DURATION:
			return 13;
		case PHONE_CALL_EVENT:
			return 14;
		case ACCELEROMETER_CLASSIFIER:
			return 15;
		case SYSTEM_STATS:
			return 16;
		case WIFI_SCAN:
			return 17;
		case AUDIO_CLASSIFIER:
			return 18;
		case DEVICE_NET_TRAFFIC:
			return 19;
		case APPS_NET_TRAFFIC:
			return 20;
		case CONNECTION_TYPE:
			return 21;
		case GOOGLE_ACTIVITY_RECOGNITION:
			return 22;
        case ACTIVITY_RECOGNITION_COMPARE:
            return 23;
		case DR:
			return 24;
		case TRAINING:
			return 25;
		case STEP_COUNTER:
			return 26;
		case TEST:
			return 99;
		default:
			return 0;
		}
	}

	private SensorType (String description, ActionType actionType, Pipeline.Type pipelineType) {
		this.description = description;
		this.actionType = actionType;
		this.pipelineType = pipelineType;
	}
	
	public Type getPipelineType() {
		return pipelineType;
	}

	public String getDescription() {
		return description;
	}

	public ActionType getActionType() {
		return actionType;
	}

	@Override
	public String toString() {
		return this.description;
	}
	
	public String toPipelineString(){
		if(this.pipelineType == null)
			return "null";
		else
			return this.pipelineType.toString();
	}

}

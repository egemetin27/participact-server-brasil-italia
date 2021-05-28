package br.com.bergmannsoft.utils;

import br.com.bergmannsoft.config.Config;
/**
 * Encodando arquivos de midia com ffmpeg
 * @author Claudio
 *
 */
public class FFmpeg {

	/**
	 * MP3 Encode
	 * 
	 * @param src
	 * @param dst
	 * @return
	 */
	public static boolean toMp3(String src, String dst) {

		String command = String.format(Config.FFMPEG_CMD_MP3, src, dst);
		return execute(command);
	}
	/**
	 * Wav
	 * @param src
	 * @param dst
	 * @return
	 */
	public static boolean toWav(String src, String dst) {
		String command = String.format(Config.FFMPEG_CMD_WAV, src, dst);
		return execute(command);
	}
	/**
	 * 
	 * @param src
	 * @param dst
	 * @return
	 */
	public static boolean toFlac(String src, String dst) {
		String command = String.format(Config.FFMPEG_CMD_FLAC, src, dst);
		return execute(command);
	}
	/**
	 * AAC Encode
	 * @param src
	 * @param dst
	 * @return
	 */
	public static boolean toAac(String src, String dst) {

		String command = String.format(Config.FFMPEG_CMD_AAC, src, dst);
		return execute(command);
	}
	/**
	 * MP4 Encode
	 * 
	 * @param src
	 * @param dst
	 * @return
	 */
	public static boolean toMp4(String src, String dst) {
		String command = String.format(Config.FFMPEG_CMD_MP4, src, dst);
		return execute(command);
	}
	/**
	 * H264 Encode
	 * @param src
	 * @param dstWeb
	 * @return
	 */
	public static boolean toH264(String src, String dstWeb) {
		String command = String.format(Config.FFMPEG_CMD_H264, src, dstWeb);
		return execute(command);
	}	

	/**
	 * Executa o ffmpeg command
	 * 
	 * @param command
	 * @return
	 */
	public static boolean execute(String command) {
		try {
			System.out.println(command);
			Process child = Runtime.getRuntime().exec(command);

			child.waitFor();
			return true;
		} catch (Exception e) {
			e.printStackTrace(System.out);

		}
		return false;
	}
}

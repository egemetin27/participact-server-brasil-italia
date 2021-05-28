package br.com.bergmannsoft.utils;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Deque;
import java.util.LinkedList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Zips/
 * 
 * @author Claudio
 *
 */
public class ZipUtils {
	/**
	 * Zip um diretorio sem incluir o caminho abosulto
	 * 
	 * @param zipFileName
	 * @param dir
	 * @throws Exception
	 */
	public static void zipDir(String zipFileName, String dir) throws Exception {
		zip(new File(dir), new File(zipFileName));
	}

	public static void zip(File directory, File zipfile) throws IOException {
		URI base = directory.toURI();
		Deque<File> queue = new LinkedList<File>();
		queue.push(directory);
		OutputStream out = new FileOutputStream(zipfile);
		Closeable res = out;
		try {
			ZipOutputStream zout = new ZipOutputStream(out);
			res = zout;
			while (!queue.isEmpty()) {
				directory = queue.pop();
				for (File kid : directory.listFiles()) {
					String name = base.relativize(kid.toURI()).getPath();
					if (kid.isDirectory()) {
						queue.push(kid);
						name = name.endsWith("/") ? name : name + "/";
						zout.putNextEntry(new ZipEntry(name));
					} else {
						zout.putNextEntry(new ZipEntry(name));
						copy(kid, zout);
						zout.closeEntry();
					}
				}
			}
		} finally {
			res.close();
		}
	}

	private static void copy(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1024];
		while (true) {
			int readCount = in.read(buffer);
			if (readCount < 0) {
				break;
			}
			out.write(buffer, 0, readCount);
		}
	}

	private static void copy(File file, OutputStream out) throws IOException {
		InputStream in = new FileInputStream(file);
		try {
			copy(in, out);
		} finally {
			in.close();
		}
	}

	private static void copy(InputStream in, File file) throws IOException {
		OutputStream out = new FileOutputStream(file);
		try {
			copy(in, out);
		} finally {
			out.close();
		}
	}
	
	/**
	 * Zip com caminho absoluto
	 * @param zipFileName
	 * @param dir
	 * @throws IOException 
	 */
	public static void ZipAllFiles(String zipFileName, String dir) throws IOException{
		File dirObj = new File(dir);
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
		// System.out.println("Creating : " + zipFileName);
		addDirFullPath(dirObj, out);
		out.close();		
	}
	/**
	 * Adicionando recursivamente pastas dentro de um zip
	 * 
	 * @param dirObj
	 * @param out
	 * @throws IOException
	 */
	private static void addDirFullPath(File dirObj, ZipOutputStream out) throws IOException {
		File[] files = dirObj.listFiles();
		byte[] tmpBuf = new byte[1024];

		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				addDirFullPath(files[i], out);
				continue;
			}
			FileInputStream in = new FileInputStream(files[i].getAbsolutePath());
//			System.out.println(" Adding: " + files[i].getAbsolutePath());
//			System.out.println(" Adding: " + files[i].getName());
			out.putNextEntry(new ZipEntry(files[i].getAbsolutePath()));
			int len;
			while ((len = in.read(tmpBuf)) > 0) {
				out.write(tmpBuf, 0, len);
			}
			out.closeEntry();
			in.close();
		}
	}	
}
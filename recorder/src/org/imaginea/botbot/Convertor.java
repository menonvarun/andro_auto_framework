package org.imaginea.botbot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.util.TraceClassVisitor;

public class Convertor {

	/**
	 * @param args
	 * @throws IOException
	 *             Use /opt/github/bot-bot/recorder/robo-bin/code-bin
	 *             /opt/github/AppsList/bin/classes
	 */
	public static void main(String[] args) throws IOException {
		Convertor con = new Convertor();
		con.rewriteCode(args[0],args[1]);
		con.showFile(args[1]);
	}

	public void rewriteCode(String dirPath,String outPath) throws FileNotFoundException,
			IOException {
		File dir = new File(dirPath);
		ArrayList<File> fileList = new ArrayList<File>();
		this.listDirectory(dir, fileList);
		String filePath = outPath;
		if (new File(filePath).exists()) {
			this.deleteDir(new File(filePath));
			new File(filePath).mkdirs();
		}
		for (File f : fileList) {
			ClassWriter cw = new ClassWriter(0);
			CustomVisitor cv = new CustomVisitor(cw);
			ClassReader cr = new ClassReader(new FileInputStream(f));
			cr.accept(cv, 0);
			//cr.accept(new TraceClassVisitor(new PrintWriter(System.out)), 0);
			filePath = outPath;
			filePath = f.getAbsolutePath().replace(dirPath, filePath);
			//System.out.println("Out filepath is: "+filePath);
			new File(filePath).getParentFile().mkdirs();
			FileOutputStream fo = new FileOutputStream(filePath);
			fo.write(cw.toByteArray());
			fo.close();
		}

	}

	public void showFile(String outPath) throws FileNotFoundException, IOException {
		ArrayList<File> afterList = new ArrayList<File>();
		File afterDir = new File(outPath);
		this.listDirectory(afterDir, afterList);
		for (File f : afterList) {
			//System.out.println(f.getAbsolutePath());
			ClassWriter cw = new ClassWriter(0);
			CustomVisitor cv = new CustomVisitor(cw);
			ClassReader cr = new ClassReader(new FileInputStream(f));
			cr.accept(cv, 0);
		}
	}

	private boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}

		// The directory is now empty so delete it
		return dir.delete();
	}

	public void listDirectory(File f, ArrayList<File> fileList) {
		File[] listOfFiles = f.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				fileList.add(listOfFiles[i]);
			} else if (listOfFiles[i].isDirectory()) {
				listDirectory(listOfFiles[i], fileList);
			}
		}
	}

}

package timicasto.symbolgen;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
	private static FileSerializer file;

	private static String prefix = "$_ ";

	private static int method = -1;

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		String buf;

		if (method == -1) {
			System.out.println("User Guide");
			method = 0;
		}

		while (true) {
			if (method == 0) {
				System.out.print(prefix);
			} else if (method == 1) {
				System.out.print("E " + prefix);
			}

			buf = scanner.nextLine();
			try {
				if (buf.startsWith("newfile")) {
					newFile(buf);
				} else if (buf.startsWith("exit")) {
					exit();
				} else if (buf.startsWith("createmeta")) {
					createMeta(buf);
				} else if (buf.startsWith("cat")) {
					cat(buf);
				} else if (buf.startsWith("addpins")) {
					addPins(buf);
				} else if (buf.startsWith("addpin")) {
					addPin(buf);
				} else if (buf.startsWith("write")) {
					write();
				} else if (buf.startsWith("copy")) {
					copy();
				} else if (buf.startsWith("fuck")) {
					fuck(buf);
				}
			} catch (Throwable t) {
				System.out.println(t);
				method = 1;
			}
		}
	}

	private static void fuck(String command) {
		String[] args = command.split(" ");
		if (args.length > 2) {
			throw new IllegalArgumentException("Too many arguments.");
		}

		if (args.length == 2) {
			int count = Integer.parseInt(args[1]);
			fuck(count);
		} else {
			fuck();
		}
	}

	private static void fuck() {
		fuck(1);
	}

	private static void fuck(int count) {
		file.removePin(file.getPinSize() - count, count);
	}

	private static void addPin(String buf) {
		String[] parsed = buf.split(" ");
		if (parsed[1].length() != 1) {
			throw new IllegalArgumentException("Invalid Type: Corrupted length");
		}
		Pin.Type type = Pin.Type.of(parsed[1].toCharArray()[0]);
		method = 0;
		String name = parsed[2];
		String number = parsed[3];

		file.addPin(type, name, number);
	}

	private static void addPins(String command) {
		String[] args = command.split(" ");
		int count = Integer.parseInt(args[2]);
		String pinName = args[3];
		Pin.Type type = Pin.Type.of(args[1].toCharArray()[0]);
		for (int i = 0; i < count; i++) {
			file.addPin(type, pinName, args[4 + i]);
		}
	}

	private static void copy() {
		method = 0;

		if (file == null) {
			throw new NullPointerException("Please create new file first.");
		}

		String str = file.serialize();
		Transferable trans = new StringSelection(str);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(trans, null);
	}

	private static void write() throws IOException {
		method = 0;

		if (file == null) {
			throw new NullPointerException("Please create new file first.");
		}

		String str = file.serialize();
		BufferedWriter writer = new BufferedWriter(new FileWriter(file.getFilename()));
		writer.write(str);
		writer.close();
	}

	private static void cat(String buf) {
		String val = buf.split(" ")[1];
		method = 0;
		if (val.equals("filename")) {
			System.out.println(file.getFilename());
		} else if (val.equals("meta")) {
			FileMeta meta = file.getFileMeta();
			System.out.println(meta.ref + " " + meta.val + " " + meta.fp + " " + meta.datasheet);
		}
	}

	private static void createMeta(String buf) {
		String[] parsed = buf.split(" ");
		char[] options = parsed[1].toCharArray();
		int cx = 2;
		method = 0;
		System.out.println(Arrays.toString(parsed));
		for (char option : options) {
			switch (option) {
				case 'r':
					file.getFileMeta().ref = parsed[cx];
					++cx;
					break;
				case 'v':
					file.getFileMeta().val = parsed[cx];
					++cx;
					break;
				case 'f':
					file.getFileMeta().fp = parsed[cx];
					++cx;
					break;
				case 'd':
					file.getFileMeta().datasheet = parsed[cx];
					++cx;
					break;
			}
		}
	}

	private static void newFile(String buf) {
		String[] fileName = buf.split(" ");
		method = 0;
		if (fileName[1].isEmpty()) {
			throw new IllegalArgumentException("newfile: Filename Expected.");
		}
		file = new FileSerializer(fileName[1]);
		prefix = file.getFilename() + " " + prefix;
		System.out.println("Changing current file to " + fileName[1]);
	}

	private static void exit() {
		System.exit(0);
	}
}
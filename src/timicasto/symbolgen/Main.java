package timicasto.symbolgen;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

	static class FileMeta {
		public String ref, val, fp, datasheet;

		public FileMeta(String ref, String val, String fp, String datasheet) {
			this.ref = ref;
			this.val = val;
			this.fp = fp;
			this.datasheet = datasheet;
		}

		public FileMeta() {
			this.ref = "";
			this.val = "";
			this.fp = "";
			this.datasheet = "";
		}
	}

	static class Pin {
		enum PinType {
			IN,
			OUT,
			BIDIRECTIONAL,
			PASSIVE;

			@Override
			public String toString() {
				switch (this) {
					case IN:
						return "input";
					case OUT:
						return "output";
					case BIDIRECTIONAL:
						return "bidirectional";
					case PASSIVE:
						return "passive";
				}
				return "";
			}
		}

		public PinType type;
		public String name, number;

		public Pin(PinType type, String name, String number) {
			this.type = type;
			this.name = name;
			this.number = number;
		}

		public Pin() {

		}
	}

	static String curr = "";
	static String prefix = "$_ ";
	static FileMeta currMeta = new FileMeta();
	static List<Pin> pins = new ArrayList<>();
	static int method = -1;

	public static void main(String[] args) throws IOException {

		Scanner scanner = new Scanner(System.in);
		String buf, command;

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
				} else if (buf.startsWith("addpin")) {
					addPin(buf);
				} else if (buf.startsWith("write")) {
					write();
				}
			} catch (Throwable t) {
				System.out.println(t.toString());
				method = 1;
				continue;
			}
		}
	}

	public static void write() throws IOException {
		StringBuilder builder = new StringBuilder();
		method = 0;
		builder.append("(symbol ");
		builder.append("\"").append(curr).append("\" (in_bom yes) (on_board yes)\n");

		builder.append("  (property \"Reference\"" + " \"").append(currMeta.ref).append("\" ").append("(at 0 1.27 0)\n");
		builder.append("    (effects (font (size 1.27 1.27)))\n  )\n");

		builder.append("  (property \"Value\"" + " \"").append(currMeta.val).append("\" ").append("(at 0 1.27 0)\n");
		builder.append("    (effects (font (size 1.27 1.27)))\n  )\n");

		builder.append("  (property \"Footprint\"" + " \"").append(currMeta.fp).append("\" ").append("(at 0 1.27 0)\n");
		builder.append("    (effects (font (size 1.27 1.27)))\n  )\n");

		builder.append("  (property \"Datasheet\"" + " \"").append(currMeta.datasheet).append("\" ").append("(at 0 1.27 0)\n");
		builder.append("    (effects (font (size 1.27 1.27)))\n  )\n");

		builder.append("  (symbol \"").append(curr).append("_1_1\"\n");

		int i = 0;
		for (Pin pin : pins) {
			builder.append("    (pin ").append(pin.type.toString()).append(" line (at 8.89 ").append(-14.605 + 1.905 * i).append(" 180) (length 2.54)\n");
			builder.append("      (name \"").append(pin.name).append("\" (effects (font (size 1.27 1.27))))\n");
			builder.append("      (number \"").append(pin.number).append("\" (effects (font (size 1.27 1.27))))\n");
			builder.append("    )\n");
			++i;
		}

		builder.append("  )\n");

		builder.append(')');

		BufferedWriter writer = new BufferedWriter(new FileWriter(curr));
		writer.write(builder.toString());
		writer.close();
	}

	public static void addPin(String buf) {
		String[] parsed = buf.split(" ");
		if (parsed[1].length() != 1) {
			throw new IllegalArgumentException("Invalid Type: Corrupted length");
		}
		Pin pin = new Pin();
		method = 0;
		switch (parsed[1].toCharArray()[0]) {
			case 'I':
				pin.type = Pin.PinType.IN;
				break;
			case 'O':
				pin.type = Pin.PinType.OUT;
				break;
			case 'B':
				pin.type = Pin.PinType.BIDIRECTIONAL;
				break;
			case 'P':
				pin.type = Pin.PinType.PASSIVE;
				break;
		}
		pin.name = parsed[2];
		pin.number = parsed[3];
		pins.add(pin);
	}

	public static void cat(String buf) {
		String val = buf.split(" ")[1];
		method = 0;
		if (val.equals("filename")) {
			System.out.println(curr + "\n");
		} else if (val.equals("meta")) {
			System.out.println(currMeta.ref + " " + currMeta.val + " " + currMeta.fp + " " + currMeta.datasheet);
		}
	}

	public static void createMeta(String buf) {
		String[] parsed = buf.split(" ");
		char[] options = parsed[1].toCharArray();
		int cx = 2;
		method = 0;
		System.out.println(Arrays.toString(parsed));
		for (char option : options) {
			switch (option) {
				case 'r':
					currMeta.ref = parsed[cx];
					++cx;
					break;
				case 'v':
					currMeta.val = parsed[cx];
					++cx;
					break;
				case 'f':
					currMeta.fp = parsed[cx];
					++cx;
					break;
				case 'd':
					currMeta.datasheet = parsed[cx];
					++cx;
					break;
			}
		}
	}

	public static void newFile(String buf) {
		String[] fileName = buf.split(" ");
		method = 0;
		if (fileName[1].isEmpty()) {
			throw new IllegalArgumentException("newfile: Filename Expected.");
		}
		curr = fileName[1];
		prefix = curr + " " + prefix;
		System.out.println("Changing current file to " + fileName[1]);
	}

	static void exit() {
		System.exit(0);
	}
}
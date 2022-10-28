package timicasto.symbolgen;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileSerializer {
    private String filename;

    private final List<Pin> pins = new ArrayList<>();
    private FileMeta currMeta = new FileMeta();

    public FileSerializer(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public FileMeta getFileMeta() {
        return currMeta;
    }

    public FileSerializer addPin(Pin.Type type, String name, String number) {
        Pin pin = new Pin(type, name, number);
        pins.add(pin);
        return this;
    }

    public String serialize() {
        StringBuilder builder = new StringBuilder();
        builder.append("(symbol ");
        builder.append("\"").append(filename).append("\" (in_bom yes) (on_board yes)\n");

        builder.append("  (property \"Reference\"" + " \"").append(currMeta.ref).append("\" ").append("(at 0 1.27 0)\n");
        builder.append("    (effects (font (size 1.27 1.27)))\n  )\n");

        builder.append("  (property \"Value\"" + " \"").append(currMeta.val).append("\" ").append("(at 0 1.27 0)\n");
        builder.append("    (effects (font (size 1.27 1.27)))\n  )\n");

        builder.append("  (property \"Footprint\"" + " \"").append(currMeta.fp).append("\" ").append("(at 0 1.27 0)\n");
        builder.append("    (effects (font (size 1.27 1.27)))\n  )\n");

        builder.append("  (property \"Datasheet\"" + " \"").append(currMeta.datasheet).append("\" ").append("(at 0 1.27 0)\n");
        builder.append("    (effects (font (size 1.27 1.27)))\n  )\n");

        builder.append("  (symbol \"").append(filename).append("_1_1\"\n");

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

        return builder.toString();
    }

    public int getPinSize() {
        return pins.size();
    }

    public void removePin(int start, int count) {
        pins.subList(start, start + count).clear();
    }
}

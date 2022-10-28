package timicasto.symbolgen;

public class Pin {

    public Type type;
    public String name, number;

//    public Pin() {
//
//    }

    public Pin(Type type, String name, String number) {
        this.type = type;
        this.name = name;
        this.number = number;
    }

    public enum Type {
        IN("input"),
        OUT("output"),
        BIDIRECTIONAL("bidirectional"),
        PASSIVE("passive"),
        ;

        private String name;

        private Type(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        public static Type of(char ch) {
            switch (ch) {
                case 'I':
                    return IN;
                case 'O':
                    return OUT;
                case 'B':
                    return BIDIRECTIONAL;
                case 'P':
                    return PASSIVE;
                default:
                    throw new IllegalArgumentException("Invalid type: " + ch);
            }
        }
    }
}

package co.kaioru.nautilus.data;

public interface IDataNode<T extends IDataNode> {

	IDataNode<T> getChild(String child);

	boolean getBoolean(boolean def);

	default boolean getBoolean(String child, boolean def) {
		return getChild(child).getBoolean(def);
	}

	default boolean getBoolean(String child) {
		return getBoolean(child, false);
	}

	byte getByte(byte def);

	default byte getByte(String child, byte def) {
		return getChild(child).getByte(def);
	}

	default byte getByte(String child) {
		return getByte(child, (byte) 0);
	}

	short getShort(short def);

	default short getShort(String child, short def) {
		return getChild(child).getShort(def);
	}

	default short getShort(String child) {
		return getShort(child, (short) 0);
	}

	int getInt(int def);

	default int getInt(String child, int def) {
		return getChild(child).getInt(def);
	}

	default int getInt(String child) {
		return getInt(child, 0);
	}

	String getStringCur(String def);

	default String getString(String child, String def) {
		return getChild(child).getStringCur(def);
	}

	default String getString(String child) {
		return getString(child, "");
	}

}

package co.kaioru.nautilus.data.nx;

import co.kaioru.nautilus.data.IDataNode;
import us.aaronweiss.pkgnx.NXFile;
import us.aaronweiss.pkgnx.NXNode;

public class NXDataNode implements IDataNode<NXDataNode> {

	private final NXNode node;

	public NXDataNode(NXFile file) {
		this.node = file.getRoot();
	}

	public NXDataNode(NXNode node) {
		this.node = node;
	}

	@Override
	public IDataNode<NXDataNode> getChild(String child) {
		String[] splits = child.split("/");
		NXNode target = node;
		
		for (String split : splits)
			target = target.getChild(split);
		return new NXDataNode(target);
	}

	private Object get(Object def) {
		Object obj = node.get();
		if (obj != null)
			return obj;
		else return def;
	}

	@Override
	public boolean getBoolean(boolean def) {
		return (boolean) get(def);
	}

	@Override
	public byte getByte(byte def) {
		return (byte) get(def);
	}

	@Override
	public short getShort(short def) {
		return (short) get(def);
	}

	@Override
	public int getInt(int def) {
		return (int) get(def);
	}

	@Override
	public String getStringCur(String def) {
		return (String) get(def);
	}

}

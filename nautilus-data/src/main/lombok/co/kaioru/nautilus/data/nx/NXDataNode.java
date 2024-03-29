package co.kaioru.nautilus.data.nx;

import co.kaioru.nautilus.data.IDataNode;
import com.google.common.collect.Lists;
import us.aaronweiss.pkgnx.NXFile;
import us.aaronweiss.pkgnx.NXNode;

import java.awt.*;
import java.util.Collection;

public class NXDataNode implements IDataNode {

	private final NXNode node;

	public NXDataNode(NXFile file) {
		this.node = file.getRoot();
	}

	public NXDataNode(NXNode node) {
		this.node = node;
	}

	@Override
	public NXDataNode getChild(String child) {
		String[] splits = child.split("/");
		NXNode target = node;

		for (String split : splits)
			target = target.getChild(split);
		return new NXDataNode(target);
	}

	@Override
	public Collection<IDataNode> getChildren() {
		Collection<IDataNode> nodes = Lists.newArrayList();
		node.forEach(n -> nodes.add(new NXDataNode(n)));
		return nodes;
	}

	@Override
	public String getName() {
		return node.getName();
	}

	private Object get(Object def) {
		if (node == null) return def;
		Object obj = node.get();
		if (obj != null)
			return obj;
		else return def;
	}

	@Override
	public boolean getBoolean(boolean def) {
		return getLong(def ? 1 : 0) > 0;
	}

	@Override
	public byte getByte(byte def) {
		return (byte) getLong(def);
	}

	@Override
	public short getShort(short def) {
		return (short) getLong(def);
	}

	@Override
	public int getInt(int def) {
		return (int) getLong(def);
	}

	@Override
	public double getDouble(double def) {
		return (double) get(def);
	}

	@Override
	public Point getPoint(Point def) {
		return (Point) get(def);
	}

	@Override
	public long getLong(long def) {
		return (long) get(def);
	}

	@Override
	public String getStringCur(String def) {
		return (String) get(def);
	}

}

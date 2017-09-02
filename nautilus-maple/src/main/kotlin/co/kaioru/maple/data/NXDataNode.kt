package co.kaioru.maple.data

import co.kaioru.core.data.IDataNode
import us.aaronweiss.pkgnx.NXFile
import us.aaronweiss.pkgnx.NXNode

class NXDataNode(
	val node: NXNode
) : IDataNode {

	constructor(file: NXFile) : this(file.root)

	override val name: String
		get() = node.name
	override val children: Map<String, IDataNode>
		get() = node.map { it.name to NXDataNode(it) }.toMap()

	override fun getByte(): Byte = node.get() as Byte
	override fun getShort(): Short = node.get() as Short
	override fun getInt(): Int = node.get() as Int
	override fun getLong(): Long = node.get() as Long
	override fun getString(): String = node.get() as String

}

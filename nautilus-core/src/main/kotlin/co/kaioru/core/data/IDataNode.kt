package co.kaioru.core.data

interface IDataNode {
	val name: String
	val children: Map<String, IDataNode>

	fun getByte(child: String, default: Byte = 0): Byte = children[child]?.getByte() ?: default
	fun getShort(child: String, default: Short = 0): Short = children[child]?.getShort() ?: default
	fun getInt(child: String, default: Int = 0): Int = children[child]?.getInt() ?: default
	fun getLong(child: String, default: Long = 0): Long = children[child]?.getLong() ?: default
	fun getString(child: String, default: String = ""): String = children[child]?.getString() ?: default

	fun getByte(): Byte
	fun getShort(): Short
	fun getInt(): Int
	fun getLong(): Long
	fun getString(): String
}

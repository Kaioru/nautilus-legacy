package co.kaioru.maple.template.field

import co.kaioru.core.data.IDataNode
import co.kaioru.maple.template.ITemplate
import java.awt.Point

class FieldPortalTemplate(
	override val templateId: Int,
	private val node: IDataNode
) : ITemplate {
	val pn: String by lazy { node.getString("pn") }
	val pt: Int by lazy { node.getInt("pt") }
	val tm: Int by lazy { node.getInt("tm") }
	val tn: String by lazy { node.getString("tn") }
	val position: Point by lazy { Point(node.getInt("x"), node.getInt("y")) }
}

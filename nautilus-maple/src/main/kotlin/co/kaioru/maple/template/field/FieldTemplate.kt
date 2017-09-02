package co.kaioru.maple.template.field

import co.kaioru.core.data.IDataNode
import co.kaioru.maple.template.ITemplate

class FieldTemplate(
	override val templateId: Int,
	private val info: IDataNode,
	private val portal: IDataNode
) : ITemplate {
	val returnMap: Int by lazy { info.getInt("returnMap", 999999999) }

	val portals: Map<Int, FieldPortalTemplate> by lazy {
		portal.children
			.map { FieldPortalTemplate(Integer.parseInt(it.key), it.value) }
			.map { it.templateId to it }
			.toMap()
	}
}

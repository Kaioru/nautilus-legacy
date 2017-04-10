package co.kaioru.nautilus.core.packet;

import co.kaioru.nautilus.core.user.User;
import co.kaioru.nautilus.core.util.IValue;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Map;

public interface IReceiver<U extends User, T extends IPacketHandler<U>> extends Serializable {

	Map<Integer, T> getPacketHandlers();

	default void registerPacketHandler(IValue<Integer> operation, T handler) {
		getPacketHandlers().put(operation.getValue(), handler);
	}

	default void deregisterPacketHandler(T handler) {
		getPacketHandlers().remove(handler);
	}

	default void handlePacket(U user, IPacketReader reader) {
		int operation = reader.readShort();
		T handler = getPacketHandlers().get(operation);

		if (handler != null) {
			if (handler.validate(user))
				handler.handle(user, reader);
			ReceiverLogHolder.log.debug("Handled operation code {} with {}",
				Integer.toHexString(operation),
				handler.getClass().getSimpleName());
		} else {
			ReceiverLogHolder.log.warn("No packet handlers found for operation code {}", Integer.toHexString(operation));
		}
	}

}

final class ReceiverLogHolder {

	static final org.slf4j.Logger log = LoggerFactory.getLogger(IReceiver.class);

}

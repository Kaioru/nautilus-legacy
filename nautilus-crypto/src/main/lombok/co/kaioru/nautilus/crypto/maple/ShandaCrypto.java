package co.kaioru.nautilus.crypto.maple;

import co.kaioru.nautilus.crypto.ICrypto;

import static co.kaioru.nautilus.crypto.maple.MapleBitTool.rollLeft;
import static co.kaioru.nautilus.crypto.maple.MapleBitTool.rollRight;

public class ShandaCrypto implements ICrypto {

	@Override
	public byte[] encrypt(byte[]... bytes) {
		byte[] data = bytes[0];

		for (int j = 0; j < 6; j++) {
			byte remember = 0;
			byte dataLength = (byte) (data.length & 0xFF);
			if (j % 2 == 0) {
				for (int i = 0; i < data.length; i++) {
					byte cur = data[i];
					cur = rollLeft(cur, 3);
					cur += dataLength;
					cur ^= remember;
					remember = cur;
					cur = rollRight(cur, (int) dataLength & 0xFF);
					cur = ((byte) ((~cur) & 0xFF));
					cur += 0x48;
					dataLength--;
					data[i] = cur;
				}
			} else {
				for (int i = data.length - 1; i >= 0; i--) {
					byte cur = data[i];
					cur = rollLeft(cur, 4);
					cur += dataLength;
					cur ^= remember;
					remember = cur;
					cur ^= 0x13;
					cur = rollRight(cur, 3);
					dataLength--;
					data[i] = cur;
				}
			}
		}
		return data;
	}

	@Override
	public byte[] decrypt(byte[]... bytes) {
		byte[] data = bytes[0];

		for (int j = 1; j <= 6; j++) {
			byte remember = 0;
			byte dataLength = (byte) (data.length & 0xFF);
			byte nextRemember = 0;
			if (j % 2 == 0) {
				for (int i = 0; i < data.length; i++) {
					byte cur = data[i];
					cur -= 0x48;
					cur = ((byte) ((~cur) & 0xFF));
					cur = rollLeft(cur, (int) dataLength & 0xFF);
					nextRemember = cur;
					cur ^= remember;
					remember = nextRemember;
					cur -= dataLength;
					cur = rollRight(cur, 3);
					data[i] = cur;
					dataLength--;
				}
			} else {
				for (int i = data.length - 1; i >= 0; i--) {
					byte cur = data[i];
					cur = rollLeft(cur, 3);
					cur ^= 0x13;
					nextRemember = cur;
					cur ^= remember;
					remember = nextRemember;
					cur -= dataLength;
					cur = rollRight(cur, 4);
					data[i] = cur;
					dataLength--;
				}
			}
		}
		return data;
	}

}

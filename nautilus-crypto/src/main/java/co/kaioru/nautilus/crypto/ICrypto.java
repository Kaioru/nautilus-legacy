package co.kaioru.nautilus.crypto;

public interface ICrypto {

	byte[] encrypt(byte[]... bytes);

	byte[] decrypt(byte[]... bytes);

}

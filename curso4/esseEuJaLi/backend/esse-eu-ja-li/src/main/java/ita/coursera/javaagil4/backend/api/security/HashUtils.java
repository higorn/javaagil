/*
 * File:   HashUtils.java
 *
 * Created on 08/07/17, 21:21
 */
package ita.coursera.javaagil4.backend.api.security;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * @author higor
 */
public final class HashUtils {

    private static final Integer ITERATIONS = 1000;
    private static final Integer KEY_LENGHT = 512;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final Integer BYTE_SIZE = 16;
    /**
     * @param DELIMITER, String não pode ser alterada, faz parte da segurança do Hash
     */
    private static final String DELIMITER = "#@";

    private HashUtils() {
    }

    /**
     * Método responsável por efetuar hash de senhas baseado no usuário e algoritimo SHA-256;
     *
     * @param username
     *            nome do usuário
     * @param password
     *            senha.
     * @return senha criptografada.
     */
    public static String hashPassword(final String username, final String password) {
        if (null != username && null != password) {
            try {
                return HashUtils.generateStorngPasswordHash(String.join(HashUtils.DELIMITER, username, password));
            } catch (final NoSuchAlgorithmException e) {
                throw new SecurityException("Algoritmo de criptografia não definido.", e);
            } catch (final InvalidKeySpecException ex) {
                throw new SecurityException("Chave de criptografia inválida.", ex);
            }
        }
        throw new SecurityException("O usuário ou senha não podem ser nulos para criptografia.");
    }

    public static String generateToken() throws NoSuchAlgorithmException {
        final KeyGenerator keygen = KeyGenerator.getInstance("AES");
        keygen.init(256);
        final SecretKey secretKey = keygen.generateKey();
        final byte[] hash = secretKey.getEncoded();
        return toHex(hash);
    }

    /**
     * Método que gera o HashPassword
     *
     * @param password
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    private static String generateStorngPasswordHash(final String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        final PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), new byte[HashUtils.BYTE_SIZE], HashUtils.ITERATIONS, HashUtils.KEY_LENGHT);
        final SecretKeyFactory skf = SecretKeyFactory.getInstance(HashUtils.ALGORITHM);
        final byte[] hash = skf.generateSecret(spec).getEncoded();
        return HashUtils.toHex(hash);
    }

    /**
     * Método para converter o HashPassword em Hexadecimal
     *
     * @param array
     * @return hex
     */
    private static String toHex(final byte[] array) {
        final BigInteger bigInteger = new BigInteger(1, array);
        final String hex = bigInteger.toString(HashUtils.BYTE_SIZE);
        final int paddingLength = array.length * 2 - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

}

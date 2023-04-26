package com.example.criticaltechworkschallenge.util.biometric

import javax.crypto.Cipher

/**
 * Handles Biometric cryptography
 */
interface CryptographyManager {

    fun getInitializedCipherForAuthentication(keyName: String): Cipher
}
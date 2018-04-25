/*
 * Copyright 2018 Red Hat, Inc.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  and Apache License v2.0 which accompanies this distribution.
 *
 *  The Eclipse Public License is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  The Apache License v2.0 is available at
 *  http://www.opensource.org/licenses/apache2.0.php
 *
 *  You may elect to redistribute this code under either of these licenses.
 */
package xyz.jetdrone.blockapps.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

public final class Address {

  private Address() {
    throw new RuntimeException("Cannot be instantiated!");
  }

  private static final Pattern ADDRESS = Pattern.compile("^(0x)?[0-9a-f]{40}$", Pattern.CASE_INSENSITIVE);
  private static final Pattern TX = Pattern.compile("^(0x)?[0-9a-f]{64}$", Pattern.CASE_INSENSITIVE);

  private static final MessageDigest SHA3;

  static {
    MessageDigest tmp = null;
    try {
      tmp = MessageDigest.getInstance("SHA3-256");
    } catch (NoSuchAlgorithmException e) {
      System.err.println("SHA3 is missing in your system, are you running Java < 9?");
    } finally {
      SHA3 = tmp;
    }
  }

  public static boolean isAddress(String address) {
    if (!ADDRESS.matcher(address).matches()) {
      return false;
    } else if (address.equals(address.toLowerCase()) || address.equals(address.toUpperCase())) {
      return true;
    } else if (SHA3 == null) {
      // this JDK can't handle this...
      return false;
    } else {
      return isChecksumAddress(address);
    }
  }

  public static boolean isChecksumAddress(String address) {
    String uppercaseAddress = address.toUpperCase();
    String lowercaseAddress = address.toUpperCase();
    address = address.replace("0x", "");
    byte[] addressHash = SHA3.digest(address.toLowerCase().getBytes());
    for (int i = 0; i < 40; i++) {
      // the nth letter should be uppercase if the nth digit of casemap is 1
      if (
        (addressHash[i] > 7 && uppercaseAddress.charAt(i) != address.charAt(i)) ||
          (addressHash[i] <= 7 && lowercaseAddress.charAt(i) != address.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  public static boolean isTxHash(String address) {
    if (!TX.matcher(address).matches()) {
      return false;
    } else {
      return address.equals(address.toLowerCase()) || address.equals(address.toUpperCase());
    }
  }
}

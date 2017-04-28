package edu.brown.cs.courseler.userinfo;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Hex;

/**
 * The user object.
 *
 * @author adevor
 */
public class User {

  private String classYear; // "Freshman", "Sophomore", "Junior", "Senior"
  private List<String> concentration; // e.g. "CSCI"
  private String loginIdToken; // e.g. AxW67yh
  private List<String> interests; // e.g. CSCI, VISA
  private List<String> sectionsInCart;
  private String shareId;

  /**
   * User constructor. We only pass in the login token then add additional info.
   * If this is signup we then add email and password. If we're on login we add
   * everything.
   *
   * @param token
   *          the login token.
   */
  public User(String token) {
    if (token == null) {
      throw new NullPointerException("bad! no null input for user thanks!");
    }
    String share = "";
    try {
      share = createShareHash(token);
    } catch (NoSuchAlgorithmException e1) {
      share = null;
    }
    setShareId(share);

    this.loginIdToken = token;
    classYear = null;
    concentration = new ArrayList<>();
    interests = new ArrayList<>();
    sectionsInCart = new ArrayList<>();
  }

  /**
   * Create a hash of user id for sharing link.
   *
   * @param id
   *          the id of the user.
   * @return the hashed id.
   * @throws NoSuchAlgorithmException
   */
  private String createShareHash(String id) throws NoSuchAlgorithmException {
    final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
    messageDigest.reset();
    messageDigest.update(id.getBytes(Charset.forName("UTF8")));
    final byte[] resultByte = messageDigest.digest();
    return new String(Hex.encodeHex(resultByte));
  }

  /**
   * Sets the share id of the user.
   *
   * @param id
   *          the id of the user.
   */
  public void setShareId(String id) {
    shareId = id;
  }

  /**
   * Get the share id of the user.
   *
   * @return the share id.
   */
  public String getShareId() {
    return shareId;
  }

  /**
   * Sets class year of user.
   *
   * @param year
   *          the year of the user.
   */
  public void setClassYear(String year) {
    this.classYear = year;
  }

  /**
   * Sets the user's concentration.
   *
   * @param conc
   *          the concentration of the user.
   */
  public void setConcentration(List<String> conc) {
    this.concentration = conc;
  }

  /**
   * Sets user interests.
   *
   * @param interests
   *          the interests of the user.
   */
  public void setInterests(List<String> interests) {
    this.interests = interests;
  }

  void setCart(List<String> sections) {
    this.sectionsInCart = sections;
  }

  /**
   * Adds section to cart.
   *
   * @param sectionId
   *          the id of the section.
   */
  public void addToCart(String sectionId) {
    this.sectionsInCart.add(sectionId);
  }

  /**
   * Removes section from cart.
   *
   * @param sectionId
   *          the id of the section.
   *
   */
  public void removeFromCart(String sectionId) {
    if (sectionsInCart.contains(sectionId)) {
      this.sectionsInCart.remove(sectionId);
    } else {
      throw new IllegalArgumentException("No such section in cart");
    }
  }

  /**
   * Get interests of user.
   *
   * @return interests
   */
  public List<String> getInterests() {
    return interests;
  }

  /**
   * Get sections in cart of user.
   *
   * @return list of section ids
   */
  public List<String> getSectionsInCart() {
    return sectionsInCart;
  }

  /**
   * Get concentration of user.
   *
   * @return concentration
   */
  public List<String> getConcentration() {
    return concentration;
  }

  /**
   * Get class year of user.
   *
   * @return class year
   */
  public String getClassYear() {
    return classYear;
  }

  /**
   * Gets token id of user.
   *
   * @return token id
   */
  public String getTokenId() {
    return loginIdToken;
  }
}

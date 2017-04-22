package edu.brown.cs.coursler.userinfo;

import java.util.ArrayList;
import java.util.List;

/**
 * The user object.
 *
 * @author adevor
 */
public class User {

  // private String email;
  // private String password; // LOL saved in plaintext oh well
  private String classYear; // "Freshman", "Sophomore", "Junior", "Senior",
                            // "Grad Student"
  private String concentration; // e.g. "CSCI"
  private String loginIdToken; // e.g. AxW67yh
  private List<String> interests; // e.g. CSCI, VISA
  private List<String> sectionsInCart;

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
    this.loginIdToken = token;
    classYear = null;
    concentration = null;
    // email = null;
    // password = null;
    interests = new ArrayList<>();
    sectionsInCart = new ArrayList<>();
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
  public void setConcentration(String conc) {
    this.concentration = conc;
  }

  // void setEmail(String email) {
  // this.email = email;
  // }

  // void setPassword(String pw) {
  // this.password = pw;
  // }

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
  public String getConcentration() {
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

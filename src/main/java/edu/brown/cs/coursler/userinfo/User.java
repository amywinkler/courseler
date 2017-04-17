package edu.brown.cs.coursler.userinfo;

import java.util.List;

/**
 * The user object.
 *
 * @author adevor
 */
public class User {

  private String email;
  private String password; // LOL saved in plaintext oh well
  private String classYear; // "2019" or "2017.5"
  private String concentration; // e.g. "CSCI"
  private String favClassCode; // e.g. "CSCI 0320"
  private String loginIdToken; // e.g. AxW67yh
  private List<String> interests; // e.g. CSCI, VISA

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
    favClassCode = null;
    email = null;
    password = null;
    interests = null;
  }

  void setClassYear(String year) {
    this.classYear = year;
  }

  void setConcentration(String conc) {
    this.concentration = conc;
  }

  // void setEmail(String email) {
  // this.email = email;
  // }

  // void setPassword(String pw) {
  // this.password = pw;
  // }

  void setFavClassCode(String classC) {
    this.favClassCode = classC;
  }

  void setInterests(List<String> interests) {
    this.interests = interests;
  }

  List<String> getInterests() {
    return interests;
  }

  String getFavClassCode() {
    return favClassCode;
  }

  String getConcentraton() {
    return concentration;
  }

  String getClassYear() {
    return classYear;
  }

  String getTokenId() {
    return loginIdToken;
  }
}

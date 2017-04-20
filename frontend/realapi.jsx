
export class API {
  constructor() {
    
  }

  // LOGIN METHODS:

  isLoggedIn() {
    return localStorage.loggedIn === 'true';
  }

  getToken() {
    return this.isLoggedIn() ? 'token' : null;
  }

  logOut() {
    localStorage.loggedIn = 'false';
  }

  logIn(email, password, callback) {
    fakeDelay(() => {
      if (fakeAccounts[email]) {
        if (fakeAccounts[email] === password) {
          localStorage.loggedIn = 'true';
          localStorage.accountPrefs = JSON.stringify(loginSuccess.preferences);
          callback(loginSuccess);
        } else {
          callback(loginFailureWrongPassword);
        }
      } else {
        callback(loginFailureUnregistered);
      }
    })
  }

  signUp(email, password, callback) {
    fakeDelay(() => {
      if (fakeAccounts[email]) {
        callback(signupFailureAlreadyRegistered);
      } else {
        fakeAccounts[email] = password;
        localStorage.loggedIn = 'true';
        callback(signupSuccess);
      }
    })
  }

  // ACCOUNT PREFS:

  /*
  Prefs is a dictionary! (for both)

  {
    class_year: 2018,
    concentration: 'CSCI',
    favorite_class: 'CSCI 0320',
    dept_interests: ['CSCI', 'VISA', 'CHIN']
  }

  */

  // callback has 1 param, a prefs dictionary
  getPrefs(callback) {
    fakeDelay(() => {
      callback(JSON.parse(localStorage.accountPrefs));
    })
  }

  postPrefs(prefs) {
    localStorage.accountPrefs = JSON.stringify(prefs);
  }

  // callback has 1 param, a calendar json
  getCalendar(callback) {
    fakeDelay(() => {
      callback(calendar);
    })
  }

  // ADD COURSES UI apis

  /*
  `filters` are a dict of restrictions on the results.
  {

  }
  */

  getRecommendations(filters, callback) {
    fakeDelay(() => {
      callback(recommended);
    });
  }

  search(filters, query, callback) {
    fakeDelay(() => {
      callback(searchResults);
    });
  }

  // Gets course info
  // callback has 1 param, the course object
  courseInfo(courseCode, callback) {
    fakeDelay(() => {
      callback(courses[courseCode]);
    });
  }

  // adds one of the two sections to the backend calendar
  addToCart(sectionCode, callback) {
    fakeDelay(() => {
      //this is very fake
      if (sectionCode==='CSCI 0320 S01') {
        calendar.sections.push(cs032_s01);
      } else if (sectionCode==='CSCI 0320 S02') {
        calendar.sections.push(cs032_s02);
      }
      callback(sectionCode);
    });
  }

  removeFromCart(sectionCode, callback) {
    fakeDelay(() => {
      if (calendar.sections.filter(function(section) {return section.section_id===sectionCode})) {
        calendar.sections = calendar.sections.filter(function(section) {return section.section_id!=sectionCode});
        callback(sectionCode); 
      }
    });
  }
}

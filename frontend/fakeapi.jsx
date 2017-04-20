
let cs032_s01 = {
  course_code: 'CSCI 0320',
  title: 'Software Engineering',
  section_id: 'CSCI 0320 S01',
  professors: ['John Jannotti'],
  locations: {
    monday_location: null,
    tuesday_location: 'Salomon 001',
    wednesday_location: null,
    thursday_location: 'Salomon 001',
    friday_location: null
  },
  times: [{
      monday_start: null,
      monday_end: null,
      tuesday_start: 1300,
      tuesday_end: 1430,
      wednesday_start: null,
      wednesday_end: null,
      thursday_start: 1300,
      thursday_end: 1430,
      friday_start: null,
      friday_end: null
    }
  ]
};

let cs032_s02 = {
  course_code: 'CSCI 0320',
  title: 'Software Engineering',
  section_id: 'CSCI 0320 S02',
  professors: ['John Jannotti'],
  locations: {
    monday_location: null,
    tuesday_location: 'Sayles 001',
    wednesday_location: null,
    thursday_location: 'Sayles 001',
    friday_location: null
  },
  times: [{
      monday_start: null,
      monday_end: null,
      tuesday_start: 1500,
      tuesday_end: 1630,
      wednesday_start: null,
      wednesday_end: null,
      thursday_start: 1000,
      thursday_end: 1130,
      friday_start: null,
      friday_end: null
    }
  ]
};

let cs32 = {
  code: 'CSCI 0320',
  title: 'Software Engineering',
  hours_per_week: {maximum: 50, average: 30},
  demographics: {
    percent_freshmen: 0.25,
    percent_sophomores: 0.5,
    percent_juniors: 0.25,
    percent_seniors: 0,
    percent_grad: 0,
    percent_concentrators: 0.25,
    percent_non_concentrators: 0.5,
    percent_undecided: 0.25
  },
  course_score: 0.71,
  prof_score: 0.64,
  recommended_to_non_concentrators: 0.2,
  learned_a_lot: 0.8,
  difficulty: 0.9,
  enjoyed: 0.7,
  description: 'In this course you will learn how to type.',
  department: 'CSCI',
  cap: 999,
  courses_dot_brown_link: 'http://hahahahah.ha',
  fun_and_cool: {
    alternate_titles: ['An Accelerated Intro to Time Management'],
    emojis: ['😂'],
    descriptions: ['hahha', 'fuck']
  },
  sections: [
    cs032_s01,
    cs032_s02
  ]
};

let calendar = {
  sections: [
    cs032_s01,
    cs032_s02
  ]
};

let courses = {
  'CSCI 0320': cs32
}

let loginSuccess = {
  status: 'success',
  id: 'jj@brown.edu',
  preferences: {
    class_year: 2018,
    concentration: 'CSCI',
    favorite_class: 'CSCI 0320',
    dept_interests: 'CSCI,VISA,MCM'
  }
};

let loginFailureUnregistered = {
  status: 'unregistered' // or wrong_password, bad_ip
};

let loginFailureWrongPassword = {
  status: 'wrong_password' // or wrong_password, bad_ip
};

let fakeAccounts = {
  'jj@brown.edu': 'abc'
}

let signupSuccess = loginSuccess;

let signupFailureAlreadyRegistered = {
  status: 'already_registered'
};

// /search?query=???
let searchResults = [cs32, cs32, cs32, cs32, cs32];

// /recommend?open=true|false,less_than_10_hours=true|false
let recommended = [
  {name: 'Based on Your Cart', courses: [cs32, cs32, cs32, cs32]},
  {name: 'In Your Concentration', courses: [cs32, cs32, cs32]},
  {name: 'Hard Classes', courses: [cs32, cs32, cs32, cs32, cs32]},
  {name: 'Class That Make You Go "Yikes"!', courses: [cs32, cs32, cs32, cs32]}
]

let fakeDelay = function(callback) {
  setTimeout(() => {
    callback();
  }, Math.random() * 0.7);
}

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

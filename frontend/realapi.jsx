
let cs032_s01 = {
  courseCode: 'CSCI 0320',
  title: 'Software Engineering',
  sectionId: 'CSCI 0320 S01',
  professors: ['John Jannotti'],
  meetingLocations: {
    mondayMeetingLoc: null,
    tuesdayMeetingLoc: 'Salomon 001',
    wednesdayMeetingLoc: null,
    thursdayMeetingLoc: 'Salomon 001',
    fridayMeetingLoc: null
  },
  times: {
    mondayStart: null,
    mondayEnd: null,
    tuesdayStart: 1300,
    tuesdayEnd: 1430,
    wednesdayStart: null,
    wednesdayEnd: null,
    thursdayStart: 1300,
    thursdayEnd: 1430,
    fridayStart: null,
    fridayEnd: null
  }
};

let cs032_s02 = {
  courseCode: 'CSCI 0320',
  title: 'Software Engineering',
  sectionId: 'CSCI 0320 S02',
  professors: ['John Jannotti'],
  meetingLocations: {
    mondayMeetingLoc: null,
    tuesdayMeetingLoc: 'Sayles Basement',
    wednesdayMeetingLoc: null,
    thursdayMeetingLoc: 'Sayles Basement',
    fridayMeetingLoc: null
  },
  times: {
    mondayStart: null,
    mondayEnd: null,
    tuesdayStart: 1500,
    tuesdayEnd: 1630,
    wednesdayStart: null,
    wednesdayEnd: null,
    thursdayStart: 1500,
    thursdayEnd: 1630,
    fridayStart: null,
    fridayEnd: null
  }
};

let cs32 = {
  courseCode: 'CSCI 0320',
  title: 'Software Engineering',
  department: 'CSCI',
  cap: 999,
  coursesDotBrownLink: 'http://hahahahah.ha',
  prereq: null,
  description: 'In this course you will learn how to type.',
  crData: {
    hoursPerWeek: {
      average: 30,
      maximum: 50
    },
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
    courseScore: 0.71,
    profScore: 0.64,
    recommendedToNonConcentrators: 0.2,
    learnedAlot: 0.8,
    difficulty: 0.9,
    enjoyed: 0.7,
  },
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

// /recommend?id=verySecureId123|open=true|false&less_than_10_hours=true|false&small_courses=true|false
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

let copy = function(x) {
  return JSON.parse(JSON.stringify(x));
}

export class API {
  constructor() {

  }
  
  post(endpoint, params, callback) {
    let url = endpoint + '?' + Object.keys(params).map((k) => k + '=' + encodeURIComponent(params[k])).join('&');
    $.ajax({
      method: 'POST',
      url: url,
      success: (response) => {
        callback(JSON.parse(response));
      },
      error: (err) => {
        console.log(err);
      }
    });
  }

  // LOGIN METHODS:

  isLoggedIn() {
    return !!localStorage.userId;
  }

  logOut() {
    delete localStorage.userId;
  }

  logIn(email, password, callback) {
    this.post('/login', {email: email, password: password}, (result) => {
      if (result.status === 'success') {
        localStorage.userId = result.id;
      }
      callback(result);
    });
  }

  signUp(email, password, callback) {
    this.post('/signup', {email: email, password: password}, (result) => {
      if (result.status === 'success') {
        localStorage.userId = result.id;
      }
      callback(result);
    });
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
    this.post('/getUserPrefs', {id: localStorage.userId}, (result) => {
      if (result.status === 'success') {
        callback(result.preferences);
      }
    });
  }
  
  checkIfPreferencesNeedEntry(callback) {
    this.getPrefs((prefs) => {
      callback(!prefs.class_year);
    });
  }

  postPrefs(prefs) {
    prefs.id = localStorage.userId;
    this.post('/setUserPrefs', prefs, (result) => {

    });
  }

  // callback has 1 param, a calendar json
  getCalendar(callback) {
    this.post('/getCart', {id: localStorage.userId}, (result) => {
      if (result.status === 'success') {
        callback(result);
      }
    });
  }

  // ADD COURSES UI apis

  /*
  `filters` are a dict of restrictions on the results.
  {

  }
  */

  getRecommendations(filters, callback) {
    // /recommend?id=verySecureId123|open=true|false&less_than_10_hours=true|false&small_courses=true|false
    let boolString = (bool) => bool ? 'true' : 'false';
    let params = {
      id: localStorage.userId,
      open: boolString(filters.open),
      less_than_10_hours: boolString(filters.less_than_10_hours),
      small_courses: boolString(filters.small_courses),
      cap: boolString(filters.cap)
    }
    this.post('/recommend', params, callback);
  }

  search(filters, query, callback) {
    this.post('/search', {query: query}, callback);
  }

  // Gets course info
  // callback has 1 param, the course object
  courseInfo(courseCode, callback) {
    this.post('/course', {courseId: courseCode}, callback);
  }

  // adds one of the two sections to the backend calendar
  addToCart(sectionCode, callback) {
    this.post('/addSection', {id: localStorage.userId, section: sectionCode}, callback)
  }

  removeFromCart(sectionCode, callback) {
    this.post('/removeSection', {id: localStorage.userId, section: sectionCode}, callback)
  }

  // gets all the departments.
  getDepartments(callback) {
    $.get("/departments", responseJSON => {
      const responseObject = JSON.parse(responseJSON);
      callback(responseObject);
    });
  }
  
  getSharedCart(id, callback) {
    this.post('/getSharedCart', {id: id}, callback);
  }
  
  getSharedCartUrl(callback) {
    this.post('/getShareId', {id: localStorage.userId}, (response) => {
      let id = response.share_id;
      let url = 'http://' + location.host + '/user/' + id;
      callback(url);
    })
  }

  isIpValid() {
    $.get("/ipVerify", responseJSON => {
      const responseObject = JSON.parse(responseJSON);
      if(responseObject.status === "valid") {
        return true;
      } else {
        return false;
      }
    });
  }
}

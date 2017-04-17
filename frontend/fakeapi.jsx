let cs032_s01 = {
  course_code: 'CSCI 0320',
  title: 'Software Engineering',
  section: 'S01',
  professor: 'John Jannotti',
  location: 'Salomon 001',
  times: {
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
    percent_undecided: 0.25,
    percent_took_for_requirement: 0.6
  },
  course_score: 0.71,
  prof_score: 0.64,
  recommended_to_non_concentrators: 0.2,
  learned_a_lot: 0.8,
  difficulty: 0.9,
  enjoyed: 0.7,
  description: 'hahahahhahahahah',
  department: 'CSCI',
  cap: 999,
  courses_dot_brown_link: 'http://hahahahah.ha',
  fun_and_cool: {
    alternate_titles: ['An Accelerated Intro to Time Management'],
    emojis: ['😂'],
    descriptions: ['hahha', 'fuck']
  },
  sections: [
    cs032_s01
  ]
};

let calendar = {
  sections: [
    cs032_s01
  ]
};

let loginSuccess = {
  status: 'success',
  id: 'jj@brown.edu',
  preferences: {
    class_year: 2018,
    concentration: 'CSCI',
    favorite_class: 'CSCI 0320',
    dept_interests: ['CSCI', 'VISA', 'CHIN']
  }
};

let loginFailure = {
  status: 'unregistered' // or wrong_password, bad_ip
};

export default class API {
  // LOGIN METHODS:
  
  isLoggedIn() {
    
  }
  
  getToken() {
    
  }
  
  logOut() {
    
  }
  
  // callback is a function with the parameters: (loginSuccessful, message)
  // - loginSuccessful: true/false
  // - message: an error, if we failed 
  logIn(email, password, callback) {
    
  }
  
  // callback is a function with the parameters: (signupSuccessful, message)
  // - loginSuccessful: true/false
  // - message: an error, if we failed 
  signUp(email, password, callback) {
    
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
    
  }
  
  postPrefs(prefs) {
    
  }
  
  // callback has 1 param, a calendar json
  getCalendar(callback) {
    
  }
  
  // ADD COURSES UI apis
  
  /*
  `filters` are a dict of restrictions on the results.
  {
    
  }
  */
  
  getRecommendations(filters, callback) {
    
  }
  
  search(filters, query, callback) {
    
  }
  
  // GET cOURSE INFO
  courseInfo(courseCode, callback) {
    
  }
  
  // CSCS 0320 S01
  addToCart(sectionCode, callback) {
    
  }
  
  removeFromCart(sectionCode, callback) {
    
  }
}

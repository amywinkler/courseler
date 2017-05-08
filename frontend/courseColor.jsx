// returns the corresponding color
export let getDepartmentColor = (department) => {

  // console.log(department);

  let stem = new Set(["Applied Mathematics","Biology","Chemistry","Computer Science","Engineering","Environmental Science","Geology","Mathematics","Medical Education","BioMed-Neuroscience","Public Health","Physics","Program in Liberal Medical Education"]);
  let humanities = new Set(["Classics","Comparative Literature","Contemplative Studies","Education","English","Gender and Sexuality Studies","History of Art and Architecture","Humanities","Music","Philosophy","Religious Studies","Theatre Arts and Performance Studies","Visual Art"]);
  let socialSciences = new Set(["Africana Studies","American Studies","Anthropology","Archeology","Business Entrepreneurship & Organizations","Cognitive Linguistic & Psychological Sciences","Development Studies","Economics","Ethnic Studies","History","International Relations","Modern Culture and Media","Public Policy","Political Science","Science and Society","Sociology","Urban Studies"]);
  let regionalStudies = new Set(["Arabic","Assyriology","Chinese","Czech","East Asian Studies","Egyptology","English for Internationals","French","Greek","German","Hispanic Studies","Hindi-Urdu","Italian","Japanese","Judaic Studies","Korean","Latin","Middle East Studies","Modern Greek","Polish","Portugese and Brazilian Studies","Persian","Russian","Sanskrit","American Sign Language","Swedish","Turkish"]);

  if (stem.has(department)) {
    // return "linear-gradient(135deg, rgb(122, 91, 206) 0%, rgb(202, 71, 153) 100%)";
    // return "linear-gradient(135deg, rgb(74, 138, 218) 0%, rgb(100, 38, 210) 100%)";

    //purple
    return "linear-gradient(135deg, rgb(228, 124, 206) 0%, rgb(115, 76, 183) 100%)";
  } else if (humanities.has(department)) {
    // return "linear-gradient(135deg, rgb(74, 138, 218) 0%, rgb(100, 38, 210) 100%)";
    // return "linear-gradient(135deg, rgb(236, 102, 88) 0%, rgb(210, 38, 121) 100%)"

    //red
    // return "linear-gradient(135deg, rgb(255, 153, 143) 0%, rgb(230, 77, 95) 100%)";

    //magenta
    return "linear-gradient(135deg, rgb(236, 100, 163) 0%, rgb(255, 70, 134) 100%)";
  } else if (socialSciences.has(department)) {
    // return "linear-gradient(135deg, rgb(41, 206, 145) 0%, rgb(71, 117, 202) 100%)";
    // return "linear-gradient(135deg, rgb(249, 193, 34) 0%, rgb(214, 134, 25) 100%)"

    //orange
    // return "linear-gradient(135deg, rgb(253, 177, 123) 0%, rgb(234, 120, 31) 100%)";

    //turq
    return "linear-gradient(135deg, rgb(86, 200, 234) 0%, rgb(40, 113, 181) 100%)";

    //green
    // return "linear-gradient(135deg, rgb(48, 181, 113) 0%, rgb(26, 158, 175) 100%)"; 
  } else if (regionalStudies.has(department)) {
    // return "linear-gradient(135deg, rgb(243, 172, 12) 0%, rgb(173, 71, 202) 100%)";
    return "linear-gradient(135deg, rgb(121, 182, 218) 0%, rgb(56, 91, 199) 100%)";
  } else {
    return "linear-gradient(135deg, rgb(243, 172, 12) 0%, rgb(173, 71, 202) 100%)";
  }

}


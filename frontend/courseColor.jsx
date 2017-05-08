// returns the corresponding color
export let getDepartmentColor = (department) => {

  console.log(department);

  let stem = new Set(["Applied Mathematics","Biology","Chemistry","Computer Science","Engineering","Environmental Science","Geology","Mathematics","Medical Education","BioMed-Neuroscience","Public Health","Physics","Program in Liberal Medical Education"]);
  let humanities = new Set(["Classics","Comparative Literature","Contemplative Studies","Education","English","Gender and Sexuality Studies","History of Art and Architecture","Humanities","Music","Philosophy","Religious Studies","Theatre Arts and Performance Studies","Visual Art"]);
  let socialSciences = new Set(["Africana Studies","American Studies","Anthropology","Archeology","Business Entrepreneurship & Organizations","Cognitive Linguistic & Psychological Sciences","Development Studies","Economics","Ethnic Studies","History","International Relations","Modern Culture and Media","Public Policy","Political Science","Science and Society","Sociology","Urban Studies"]);
  let regionalStudies = new Set(["Arabic","Assyriology","Chinese","Czech","East Asian Studies","Egyptology","English for Internationals","French","Greek","German","Hispanic Studies","Hindi-Urdu","Italian","Japanese","Judaic Studies","Korean","Latin","Middle East Studies","Modern Greek","Polish","Portugese and Brazilian Studies","Persian","Russian","Sanskrit","American Sign Language","Swedish","Turkish"]);

  if (stem.has(department)) {
    return "linear-gradient(135deg, rgb(122, 91, 206) 0%, rgb(202, 71, 153) 100%)";
  } else if (humanities.has(department)) {
    return "linear-gradient(135deg, rgb(74, 138, 218) 0%, rgb(100, 38, 210) 100%)";
  } else if (socialSciences.has(department)) {
    return "linear-gradient(135deg, rgb(41, 206, 145) 0%, rgb(71, 117, 202) 100%)";
  } else if (regionalStudies.has(department)) {
    return "linear-gradient(135deg, rgb(243, 172, 12) 0%, rgb(173, 71, 202) 100%)";
  } else {
    return "linear-gradient(135deg, rgb(243, 172, 12) 0%, rgb(173, 71, 202) 100%)";
  }

}


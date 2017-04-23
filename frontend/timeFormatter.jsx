
// this takes a military-time integer like 1430 and returns a human-readable string like "2:30"
export let militaryTimeIntToString = (time) => {
	let timeString;
	if (time.toString().length==3) {
		timeString = '0' + time.toString();
	} else {
		timeString = time.toString();
	}

	let militaryHours = parseInt(timeString.substring(0, 2),10);
  let hours = ((militaryHours + 11) % 12) + 1;
  let amPm;
  if (militaryHours > 11) {
		amPm = 'pm';
	} else {
		amPm = 'am';
	}
  let minutes = timeString.substring(2);

  return hours + ':' + minutes + amPm;
}

// this returns a string description of meeting time for a particular section's `time` field
export let descriptionForSectionTimes = (times) => {
  let weekdays = ['monday', 'tuesday', 'wednesday', 'thursday', 'friday'];
  let weekdayAbbrevs = {monday: 'M', tuesday: 'T', wednesday: 'W', thursday: 'R', friday: 'F'};
  let weekdaysForTimes = {};
  weekdays.forEach((weekday) => {
    let startInt = times[weekday + 'Start'];
    let endInt = times[weekday + 'End'];
    if (startInt !== null && endInt !== null) {
      let timeStr = militaryTimeIntToString(startInt) + '–' + militaryTimeIntToString(endInt);
      weekdaysForTimes[timeStr] = (weekdaysForTimes[timeStr] || '') + weekdayAbbrevs[weekday];
    }
  });
  let descriptions = Object.keys(weekdaysForTimes).map((time) => {
    return weekdaysForTimes[time] + ' ' + time;
  });
  return descriptions.join(', ');
}

// this returns a meeting-time description for an entire course (which may have multiple meeting times)
export let descriptionForCourseTimes = (course) => {
  let mainSections = course.sections.filter((s) => s.isMainSection);
  if (mainSections.length > 1) {
    return descriptionForSectionTimes(mainSections[0].times) + ' + other sections';
  } else if (mainSections.length === 1) {
    return descriptionForSectionTimes(mainSections[0].times);
  } else {
    return "";
  }
}

import React from 'react';
import ReactDOM from 'react-dom';
import api from './api.jsx';
import CalendarCourseObject from './calendarCourseObject.jsx';
import CalendarDayView from './calendarDayView.jsx';
import CourseInfoScreen from './courseInfoScreen.jsx';

export default class Calendar extends React.Component {

	constructor(props) {
		super(props);
		this.state = {
			screen: 'calendar',
			// For calendar View
			monday: [],
			tuesday: [],
			wednesday: [],
			thursday: [],
			friday: [],
			// For course info view
			selectedCourseInfo: {}
		};
		this.loadExistingSections();
	}

	render() {
		let screen = this.state.screen;
		if (screen==='calendar') {
			return (
				<div className = 'calendar'>
					<CalendarDayView day="Monday" courses={this.state.monday} />
					<CalendarDayView day="Tuesday" courses={this.state.tuesday} />
					<CalendarDayView day="Wednesday" courses={this.state.wednesday} />
					<CalendarDayView day="Thursday" courses={this.state.thursday} />
					<CalendarDayView day="Friday" courses={this.state.friday} />
				</div>
			)
		} else if (screen==='coursePage') {
			let coursePage = <CourseInfoScreen click={this.showCalendar.bind(this)} info={this.state.selectedCourseInfo}/>
			return (
				<div className = 'coursePage'>{coursePage}</div>
			)
		}
	}

	/*
		Shows the course info view.
	*/
	showCourseInfo(e) {
		this.setState({screen: 'coursePage'});
		this.getCourseInfo(e);
	}

	/*
		Shows the calendar view.
	*/
	showCalendar(e) {
		this.setState({screen: 'calendar'});
	}

	/*
		Makes an api call to get the selected course info.
	*/
	getCourseInfo(courseCode) {
		let callback = (course) => {
			this.setState({selectedCourseInfo: course});
		}
		api.courseInfo(courseCode, callback);
	}


	/*
		Loads existing sections as CalendarCourseObjects from api.
	*/
	loadExistingSections() {

		// Puts a single section into the appropriate day 
		let loadDay = (day, timeObject, sectionObject) => {
			let startString = day+"_start";
			let endString = day+"_end";
			let courseId = sectionObject.course_code;
			if (timeObject[startString] != null && timeObject[endString] !=null) {
				let startTime = timeObject[startString];
				let endTime = timeObject[endString]; //bind courseid instead
				let newCourseObject = <CalendarCourseObject key={sectionObject.section_id} title={sectionObject.section_id} start={startTime} end={endTime} click={this.showCourseInfo.bind(this, courseId)}/>;
				let obj = {};
				obj[day] = this.state[day].concat([newCourseObject]);
				this.setState(obj);
			}
		}

		// Loads the entire week
		let loadCalendar = (calendar) => {
			calendar.sections.map(function(section) {
				section.times.map(function(time) {
					loadDay("monday", time, section);
					loadDay("tuesday", time, section);
					loadDay("wednesday", time, section);
					loadDay("thursday", time, section);
					loadDay("friday", time, section);
				}, this);
			}, this);
		};

		api.getCalendar(loadCalendar);
	}


}

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
			//Arrays of calendar course objects
			monday: [],
			tuesday: [],
			wednesday: [],
			thursday: [],
			friday: [],
			//
			selectedCourse: ''
		};

		// Binding 'this' to the togglers
		this.showCourseInfo = this.showCourseInfo.bind(this);
		this.showCalendar = this.showCalendar.bind(this);

		this.loadExistingSections();
	}

	showCourseInfo(e) {
		this.setState({screen: 'coursePage'});
	}

	showCalendar(e) {
		this.setState({screen: 'calendar'});
	}

	render() {
		let screen = this.state.screen;
		if (screen==='calendar') {
			return (
				<div className ='calendar'>
					<CalendarDayView day="Monday" courses={this.state.monday} />
					<CalendarDayView day="Tuesday" courses={this.state.tuesday} />
					<CalendarDayView day="Wednesday" courses={this.state.wednesday} />
					<CalendarDayView day="Thursday" courses={this.state.thursday} />
					<CalendarDayView day="Friday" courses={this.state.friday} />
				</div>
			)
		} else if (screen==='coursePage') {
			return (
				<div className = 'coursePage'>
					<CourseInfoScreen click={this.showCalendar}/>
				</div>
			)
		}
	}

	/*
		Loads existing sections as CalendarCourseObjects from api.
	*/
	loadExistingSections() {

		// Puts a single section into the appropriate day 
		let loadDay = (day, timeObject, sectionObject) => {
			let startString = day+"_start";
			let endString = day+"_end";
			if (timeObject[startString] != null && timeObject[endString] !=null) {
				let startTime = timeObject[startString];
				let endTime = timeObject[endString];
				let newCourseObject = <CalendarCourseObject key={sectionObject.section_id} title={sectionObject.section_id} start={startTime} end={endTime} click={this.showCourseInfo}/>;
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

import React from 'react';
import ReactDOM from 'react-dom';
import api from './api.jsx';
import CalendarCourseObject from './calendarCourseObject.jsx';
import CalendarDayView from './calendarDayView.jsx';

export default class Calendar extends React.Component {

	constructor(props) {
		super(props);
		this.state = {

		};
		this.displayExistingCourses();
	}

	render() {
		return (
			<div className ='calendar'>
				<CalendarDayView day="Monday" />
				<CalendarDayView day="Tuesday" />
				<CalendarDayView day="Wednesday" />
				<CalendarDayView day="Thursday" />
				<CalendarDayView day="Friday" />
			</div>
		)
	}

	displayExistingCourses() {
		api.getCalendar(console.log);
	}

}

import React from 'react';
import ReactDOM from 'react-dom';
import api from './api.jsx';
import CalendarCourseObject from './calendarCourseObject.jsx';

export default class CalendarDayView extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			day: props.day,
			date: ''
		};
	}

	render() {
		let day = this.state.day;
		return (
			<div className='calendarDayView' style={{display:"inline-block", width:"20%"}}>
				<h3>{day}</h3>
			</div>
		)
	}

}

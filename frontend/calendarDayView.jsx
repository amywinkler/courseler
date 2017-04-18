import React from 'react';
import ReactDOM from 'react-dom';
import api from './api.jsx';
import CalendarCourseObject from './calendarCourseObject.jsx';

export default class CalendarDayView extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
		};
	}

	render() {
		let day = this.props.day;
		let courses = this.props.courses;
		return (
			<div className='calendarDayView' style={{display:"inline-block", width:"20%", verticalAlign:"top"}}>
				<h1>{day}</h1>
				<div className='courses'>
					{courses}
				</div>
			</div>
		)
	}

}

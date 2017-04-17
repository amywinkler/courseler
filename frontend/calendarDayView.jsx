import React from 'react';
import ReactDOM from 'react-dom';
import api from './api.jsx';
import CalendarCourseObject from './calendarCourseObject.jsx';

export default class CalendarDayView extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			numCourses: 0
		};
	}

	render() {
		const courses = [];
		for (var i=0; i < this.state.numCourses; i+=1) {
			courses.push(<CalendarCourseObject />)
		}

		return (
			<div className='calendarDayView'>
			<h1>This is a day</h1>
			<button onClick={() => { this.addCourse() }}>Add a course!</button>
			{courses}
			</div>
		)
	}

	addCourse() {
		this.setState({
			numCourses: this.state.numCourses + 1
		})
	}
}

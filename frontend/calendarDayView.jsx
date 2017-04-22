import React from 'react';
import ReactDOM from 'react-dom';
import api from './api.jsx';
import CalendarSectionObject from './calendarSectionObject.jsx';

export default class CalendarDayView extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
		};
	}

	render() {
		let day = this.props.day;
		let sections = this.props.sections;
		return (
			<div className='calendarDayView'>
				<h3 className='dayLabel'>{day}</h3>
				<div className='courses'>
					{sections}
				</div>
			</div>
		)
	}

}

import React from 'react';
import ReactDOM from 'react-dom';
import api from './api.jsx';

export default class CalendarCourseObject extends React.Component {

	constructor(props) {
		super(props);
			this.state = {
				overlaps: false
			};
  }

	render() {
		let title = this.props.title;
		return (
		  <div className='calendarCourseObject'>
				<h3>{title}</h3>
		  </div>
		)
	}
}

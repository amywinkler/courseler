import React from 'react';
import ReactDOM from 'react-dom';
import api from './api.jsx';

export default class CalendarSectionObject extends React.Component {

	constructor(props) {
		super(props);
		this.state = {
			selected: false,
			overlaps: false
		};
  }

	render() {
		let title = this.props.title;
		let start = this.convertTime(this.props.start);
		let end = this.convertTime(this.props.end);

		return (
		  <div className='calendarSectionObject' onClick={this.props.click}>
				<h3>{title}</h3>
				<p>{start}–{end}</p>
		  </div>
		)
	}

	convertTime(time) {
		let hours = time.toString().substring(0,2) % 12;
		let minutes = time.toString().substring(2,4);
		let amPm;
		if (time.toString().substring(0,2) > 11) {
			amPm = 'pm';
		} else {
			amPm = 'am';
		}
		return hours + ":" + minutes + amPm;
	}

}

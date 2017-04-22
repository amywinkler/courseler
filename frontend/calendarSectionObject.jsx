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

	// Format military time 
	convertTime(time) {
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

}

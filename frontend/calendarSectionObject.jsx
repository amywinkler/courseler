import React from 'react';
import ReactDOM from 'react-dom';
import api from './api.jsx';
import { militaryTimeIntToString } from './timeFormatter.jsx';

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
		let start = militaryTimeIntToString(this.props.start);
		let end = militaryTimeIntToString(this.props.end);

		return (
		  <div className='calendarSectionObject' onClick={this.props.click}>
				<h4>{title}</h4>
				<p>{start}–{end}</p>
		  </div>
		)
	}

}

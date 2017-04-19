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
		let start = this.props.start;
		let end = this.props.end;
		return (
		  <div className='calendarSectionObject' onClick={this.props.click} style={{backgroundColor: "#cccccc"}}>
				<h3>{title}</h3>
				<p>time: {start}–{end}</p>
		  </div>
		)
	}

}

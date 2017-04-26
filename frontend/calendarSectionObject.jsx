import React from 'react';
import ReactDOM from 'react-dom';
import api from './api.jsx';
import { militaryTimeIntToString } from './timeFormatter.jsx';
import { currentRoute, navigateToRoute } from './routing.jsx';

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
		let removeButton = <div className='removeSection' onClick={this.removeSection.bind(this)}>✕</div>
		let style={
			backgroundColor: '#FC54B8'
		}
		let locations = this.props.locations;

		return (
		  <div className='calendarSectionObject' onClick={ this.props.click} style={style}>
		  	{removeButton}
				<h4>{title}</h4>
				<p>{start}–{end}</p>
				<p>{locations}</p>
		  </div>
		)
	}

	removeSection(e){
		if (!e) var e = window.event;
    if (e.stopPropagation) e.stopPropagation();
		api.removeFromCart(this.props.id, this.props.onRemove);
	}

}

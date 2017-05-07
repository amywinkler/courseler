import React from 'react';
import ReactDOM from 'react-dom';
import api from './api.jsx';
import { militaryTimeIntToString } from './timeFormatter.jsx';
import { currentRoute, navigateToRoute } from './routing.jsx';


class ConflictInfo extends React.Component {
	constructor(props) {
		super(props);
	}

	render() {
		let titles = this.props.conflicts.map((section) => {
			return section.title;
		});
		
		if (titles.length>0) {
			return (
				<div className="conflictInfo">
					<p className="conflicts">⚠️ Conflicts: {titles.join(", ")}</p>
				</div>
			)
		} else {
			return null;
		}
	}
 
}


class ConflictMarker extends React.Component {
	constructor(props) {
		super(props);
	}

	render() {
		let titles = this.props.conflicts.map((section) => {
			return section.title;
		});
		
		if (titles.length>0) {
			return (
				<div className="conflict">
					<div className="conflictMarker"></div>
				</div>
			)
		} else {
			return null;
		}
	}
}

export default class CalendarSectionObject extends React.Component {

	constructor(props) {
		super(props);
		this.state = {
			overlaps: false
		};
  }

	render() {
		let title = this.props.title;
		let start = militaryTimeIntToString(this.props.start);
		let end = militaryTimeIntToString(this.props.end);
		let removeButton = this.props.locked ? null : <div className='removeSection' onClick={this.removeSection.bind(this)}>×</div>;
		let style={
			backgroundColor: '#FC54B8'
		}
		let locations = this.props.locations;
		let conflictingSections = this.props.conflictingSections;
		let conflictMarker = <ConflictMarker conflicts={conflictingSections} />
		let conflictInfo = <ConflictInfo conflicts={conflictingSections} />

		return (
		  <div className='calendarSectionObject' onClick={ this.props.click} style={style}>
		  	{removeButton}
				<h4>{title}</h4>
				<p>{start}–{end}</p>
				<p>{locations}</p>
				{conflictInfo}
		  	{conflictMarker}
		  </div>
		)
	}

	removeSection(e){
		if (!e) var e = window.event;
    if (e.stopPropagation) e.stopPropagation();
		api.removeFromCart(this.props.id, this.props.onRemove);
	}

}

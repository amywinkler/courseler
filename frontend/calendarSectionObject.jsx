import React from 'react';
import ReactDOM from 'react-dom';
import api from './api.jsx';
import { militaryTimeIntToString } from './timeFormatter.jsx';
import { currentRoute, navigateToRoute } from './routing.jsx';


class Conflicts extends React.Component {
	constructor(props) {
		super(props);
	}

	render() {
		let titles = this.props.conflicts.map((section) => {
			return section.title;
		});
		
		if (titles.length>0) {
			return (
				<p className="conflicts">
					Conflicts: {titles.join(", ")}
				</p>
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
		let conflicts = <Conflicts conflicts={conflictingSections} />

		return (
		  <div className='calendarSectionObject' onClick={ this.props.click} style={style}>
		  	{removeButton}
				<h4>{title}</h4>
				<p>{start}–{end}</p>
				<p>{locations}</p>
				{conflicts}
		  </div>
		)
	}

	removeSection(e){
		if (!e) var e = window.event;
    if (e.stopPropagation) e.stopPropagation();
		api.removeFromCart(this.props.id, this.props.onRemove);
	}

}

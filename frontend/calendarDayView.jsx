import React from 'react';
import ReactDOM from 'react-dom';
import api from './api.jsx';
import CalendarSectionObject from './calendarSectionObject.jsx';
import { militaryTimeIntToString } from './timeFormatter.jsx';

export default class CalendarDayView extends React.Component {
	
	constructor(props) {
		super(props);
		this.state = {
			startTimes: [],
			sectionMap: {}
		};
	}

	componentDidUpdate() {
		if (this.props.sections) {
			// let currTimes = [];
			this.props.sections.map((section) => {
				let currTimes = this.state.startTimes;
				if (!currTimes.includes(section.props.start)) {
					this.setState({startTimes: currTimes.concat([section.props.start]).sort(this.orderTimes)});
				};
			});
		};
	}

	orderTimes = (s1, s2) => {
		return (s1 - s2);
	};


	render() {
		let day = this.props.day;
		let sections = this.props.sections;
		let style= (this.props.style) ? this.props.style : {};

		let timeHeaders = this.state.startTimes.map((time, index) => {
			return (
				<div className='timeSection' key={index}>
					<h4 className = 'timeLabel' key={index}>{militaryTimeIntToString(time)}</h4>
					<div className='courses'>
						{this.getSectionsWithStartTime(time)}
					</div>
				</div>
			);
		});

		return (
			<div className='calendarDayView' style={style}>
				<h3 className='dayLabel'>{day}</h3>
				{timeHeaders}
			</div>
		)
	}

	getSectionsWithStartTime(time) {
		let sections = [];
		// console.log(time);
		this.props.sections.map((section)=> {
			if (section.props.start === time) {
				sections.push(section);
			};
		});
		return sections;
	}

}

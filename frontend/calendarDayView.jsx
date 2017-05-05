import React from 'react';
import ReactDOM from 'react-dom';
import api from './api.jsx';
import CalendarSectionObject from './calendarSectionObject.jsx';

export default class CalendarDayView extends React.Component {
	
	constructor(props) {
		super(props);
		this.state = {
			startTimes: []
		};
	}

	componentDidUpdate() {
		if (this.props.sections) {
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

		console.log(this.state.startTimes);


		return (
			<div className='calendarDayView' style={style}>
				<h3 className='dayLabel'>{day}</h3>
				<h4 className='timeLabel'>Time</h4>
				<div className='courses'>
					{sections}
				</div>
			</div>
		)
	}

}

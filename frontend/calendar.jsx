import React from 'react';
import ReactDOM from 'react-dom';
import api from './api.jsx';
import CalendarCourseObject from './calendarCourseObject.jsx';
import CalendarDayView from './calendarDayView.jsx';

export default class Calendar extends React.Component {

	constructor(props) {
		super(props);
		this.state = {
		//Arrays of calendar course objects
			monday: [],
			tuesday: [],
			wednesday: [],
			thursday: [],
			friday: [] 
		};

		this.loadExistingSections();
	}

	render() {
		let monday = <CalendarDayView day="monday" courses={this.state.monday} />;
		let tuesday = <CalendarDayView day="tuesday" courses={this.state.tuesday} />;
		let wednesday = <CalendarDayView day="wednesday" courses={this.state.wednesday} />;
		let thursday = <CalendarDayView day="thursday" courses={this.state.thursday} />;
		let friday = <CalendarDayView day="friday" courses={this.state.friday} />;
		return (
			<div className ='calendar'>
				{monday}
				{tuesday}
				{wednesday}
				{thursday}
				{friday}
			</div>
		)
	}

	/*
		Loads existing sections as CalendarCourseObjects from api.
		todo: clean this up
	*/
	loadExistingSections() {

		let loadCalendar = (calendar) => {
			calendar.sections.map(function(section) {
				section.times.map(function(time) {
					if (time["monday_start"] != null && time["monday_end"] != null) {
						let startTime = time["monday_start"];
						let endTime = time["monday_end"];
						let newCourseObject = <CalendarCourseObject key={section.section_id} title={section.section_id} start={startTime} end={endTime}/>;
						this.setState((state) => ({ monday: state.monday.concat([newCourseObject]) }));
					}
					if (time["tuesday_start"] != null && time["tuesday_end"] != null) {
						let startTime = time["tuesday_start"];
						let endTime = time["tuesday_end"];
						let newCourseObject = <CalendarCourseObject key={section.section_id} title={section.section_id} start={startTime} end={endTime}/>;
						this.setState((state) => ({ tuesday: state.tuesday.concat([newCourseObject]) }));
					}
					if (time["wednesday_start"] != null && time["wednesday_end"] != null) {
						let startTime = time["wednesday_start"];
						let endTime = time["wednesday_end"];
						let newCourseObject = <CalendarCourseObject key={section.section_id} title={section.section_id} start={startTime} end={endTime}/>;
						this.setState((state) => ({ wednesday: state.wednesday.concat([newCourseObject]) }));
					}
					if (time["thursday_start"] != null && time["thursday_end"] != null) {
						let startTime = time["thursday_start"];
						let endTime = time["thursday_end"];
						let newCourseObject = <CalendarCourseObject key={section.section_id} title={section.section_id} start={startTime} end={endTime}/>;
						this.setState((state) => ({ thursday: state.thursday.concat([newCourseObject]) }));
					}
					if (time["friday_start"] != null && time["friday_end"] != null) {
						let startTime = time["friday_start"];
						let endTime = time["friday_end"];
						let newCourseObject = <CalendarCourseObject key={section.section_id} title={section.section_id} start={startTime} end={endTime}/>;
						this.setState((state) => ({ friday: state.friday.concat([newCourseObject]) }));
					}
				}, this);
			}, this);
		};

		api.getCalendar(loadCalendar);
	}


}

import React from 'react';
import ReactDOM from 'react-dom';
import api from './api.jsx';
import CalendarSectionObject from './calendarSectionObject.jsx';
import CalendarDayView from './calendarDayView.jsx';
import { currentRoute, navigateToRoute } from './routing.jsx';

export default class Calendar extends React.Component {

	constructor(props) {
		super(props);
		this.state = {
			screen: 'calendar',
			// For calendar view: arrays of CalendarSectionObjects
			monday: [],
			tuesday: [],
			wednesday: [],
			thursday: [],
			friday: [],
			// For course info view
			selectedCourseInfo: {},
			// Array of all sectionIds in current cart
			currentCart: []
		};
	}
  
  componentDidMount() {
    if (this.props.calendar) this.gotCalendar(this.props.calendar);
  }

	render() {
		return (
			<div className = 'calendar'>
				<CalendarDayView day="Monday" sections={this.state.monday} />
				<CalendarDayView day="Tuesday" sections={this.state.tuesday} />
				<CalendarDayView day="Wednesday" sections={this.state.wednesday} />
				<CalendarDayView day="Thursday" sections={this.state.thursday} />
				<CalendarDayView day="Friday" sections={this.state.friday} />
			</div>
		)
	}

	/*
		Shows the course info view.
	*/
	showCourseInfo(courseCode) {
		navigateToRoute({screen: 'course', courseCode: courseCode});
    // this.getCourseInfo(e);
	}

	/*
		Shows the calendar view.
	*/
	showCalendar(e) {
    navigateToRoute({});
	}

	/*
		Makes an api call to get the selected course's info.
	*/
	getCourseInfo(courseCode) {
		let callback = (course) => {
			this.setState({selectedCourseInfo: course});
      navigateToRoute({screen: 'course'});
		}
		api.courseInfo(courseCode, callback);
	}


  componentWillReceiveProps(props) {
    if (props.calendar) {
      this.gotCalendar(props.calendar)
    }
	}
  
  gotCalendar(calendar) {
  	// Clear the calendar first
  	this.setState({"monday" : []});
  	this.setState({"tuesday" : []});
  	this.setState({"wednesday" : []});
  	this.setState({"thursday" : []});
  	this.setState({"friday" : []});

		// Puts a single section into the appropriate day
		let loadDay = (day, timeObject, sectionObject) => {
			let startString = day+"Start";
			let endString = day+"End";
			let courseId = sectionObject.courseCode;
			if (timeObject[startString] != null && timeObject[endString] !=null) {
				let startTime = timeObject[startString];
				let endTime = timeObject[endString]; 
				let newSectionObject = <CalendarSectionObject 
										key={sectionObject.sectionId} 
										title={sectionObject.sectionId} 
										start={startTime} 
										end={endTime} 
										click={this.showCourseInfo.bind(this, courseId)}/>;
				this.setState((state) => {
  				state[day] = state[day].concat([newSectionObject]);
        });
			}
    }
		calendar.sections.map(function(section) {
			loadDay("monday", section.times, section);
			loadDay("tuesday", section.times, section);
			loadDay("wednesday", section.times, section);
			loadDay("thursday", section.times, section);
			loadDay("friday", section.times, section);
			this.setState({currentCart: this.state.currentCart.concat([section.sectionId])});
		}, this);
  }
}

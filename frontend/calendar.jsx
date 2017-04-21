import React from 'react';
import ReactDOM from 'react-dom';
import api from './api.jsx';
import CalendarSectionObject from './calendarSectionObject.jsx';
import CalendarDayView from './calendarDayView.jsx';
import CourseInfoScreen from './courseInfoScreen.jsx';
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
		this.loadApiSections();
	}

	render() {
		let screen = this.state.screen;
		if (screen==='calendar') {
			return (
				<div className = 'calendar'>
					<CalendarDayView day="Monday" sections={this.state.monday} />
					<CalendarDayView day="Tuesday" sections={this.state.tuesday} />
					<CalendarDayView day="Wednesday" sections={this.state.wednesday} />
					<CalendarDayView day="Thursday" sections={this.state.thursday} />
					<CalendarDayView day="Friday" sections={this.state.friday} />
				</div>
			)
		} else if (screen==='coursePage') {
			let coursePage = <CourseInfoScreen click={this.showCalendar.bind(this)} 
								info={this.state.selectedCourseInfo} 
								currentCart={this.state.currentCart} 
								remove={this.removeCourse.bind(this)}
								add={this.addCourse.bind(this)} />
			return (
				<div className = 'coursePage'>{coursePage}</div>
			)
		}
	}

	/*
		Shows the course info view.
	*/
	showCourseInfo(e) {
    // navigateToRoute({screen: 'course', course: e.courseCode});
		this.setState({screen: 'coursePage'});
		this.getCourseInfo(e);
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
		}
		api.courseInfo(courseCode, callback);
	}


	/*
		Loads existing sections as CalendarCourseObjects from api.
	*/
	loadApiSections() {

		// Puts a single section into the appropriate day (see the next function) 
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
				let obj = {};
				obj[day] = this.state[day].concat([newSectionObject]);
				this.setState(obj);
			}
		}

		// Loads the entire week
		let loadCalendar = (calendar) => {
			// For each section in the calendar, 
			// add sectionObjects in the appropriate days
			calendar.sections.map(function(section) {
				loadDay("monday", section.times, section);
				loadDay("tuesday", section.times, section);
				loadDay("wednesday", section.times, section);
				loadDay("thursday", section.times, section);
				loadDay("friday", section.times, section);
				this.setState({currentCart: this.state.currentCart.concat([section.sectionId])});
			}, this);
		};

		api.getCalendar(loadCalendar);
	}

	/*
		Removes a section from the current cart state / calendar. 
		Called when a user hits "remove from cart" for a section in a course page
	*/
	removeCourse(sectionId) {
		//can maybe do the api thing here
		let removeIndex = this.state.currentCart.indexOf(sectionId);
		if (removeIndex > -1) {
			this.setState({currentCart: this.state.currentCart.splice(removeIndex,1)});
    		//Reset view
    		this.setState({currentCart: []});
    		this.setState({monday: []});
    		this.setState({tuesday: []});
    		this.setState({wednesday: []});
    		this.setState({thursday: []});
    		this.setState({friday: []});
    		this.loadApiSections();
		}
	}

	/*
		Adds a section from the current cart state / calendar.
		Called when a user hits "add to cart" for a section in a course page
	*/ 
	addCourse(sectionId) {
		this.setState({currentCart: this.state.currentCart.concat([sectionId])});
		// There is 100% a better way to do this
	    this.setState({currentCart: []});
		this.setState({monday: []});
		this.setState({tuesday: []});
		this.setState({wednesday: []});
		this.setState({thursday: []});
		this.setState({friday: []});
		this.loadApiSections();
	}
}

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
    if (this.props.calendar) this.gotCalendar(this.props.calendar);
	}

	render() {
    let screen = this.props.route.screen || 'calendar';
    if (screen === 'calendar') {
  		return (
  			<div className = 'calendar'>
  				<CalendarDayView day="Monday" sections={this.state.monday} />
  				<CalendarDayView day="Tuesday" sections={this.state.tuesday} />
  				<CalendarDayView day="Wednesday" sections={this.state.wednesday} />
  				<CalendarDayView day="Thursday" sections={this.state.thursday} />
  				<CalendarDayView day="Friday" sections={this.state.friday} />
  			</div>
  		)
    } else if (screen === 'course') {
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
		navigateToRoute({screen: 'course'});
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
		calendar.sections.map(function(section) {
			loadDay("monday", section.times, section);
			loadDay("tuesday", section.times, section);
			loadDay("wednesday", section.times, section);
			loadDay("thursday", section.times, section);
			loadDay("friday", section.times, section);
			this.setState({currentCart: this.state.currentCart.concat([section.sectionId])});
		}, this);
  }

	/*
		Removes a section from the current cart state / calendar. 
		Called when a user hits "remove from cart" for a section in a course page
	*/
	removeCourse(sectionId) {
		//can maybe do the api thing here
		let removeIndex = this.state.currentCart.indexOf(sectionId);
    this.props.reloadCalendar();
	}

	/*
		Adds a section from the current cart state / calendar.
		Called when a user hits "add to cart" for a section in a course page
	*/ 
	addCourse(sectionId) {
    // TODO call the api
    this.props.reloadCalendar();
	}
}

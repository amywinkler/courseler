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
			currentCart: [],
			timeslots: {}
		};
	}
  
  componentDidMount() {
    if (this.props.calendar) this.gotCalendar(this.props.calendar);
  }

	render() {
    let sharedCartTitle = null;
    if (this.props.shared && this.props.calendar && this.props.calendar.email) {
      sharedCartTitle = <h1 className='sharedCartTitle'><em>{ this.props.calendar.email }</em> has shared their cart with you.</h1>
    }
    let emptyState = null;
    if (this.props.calendar && this.props.calendar.sections.length === 0) {
      emptyState = <div className='emptyState'/>;
    }
		return (
			<div className='calendar screen'>
        { sharedCartTitle }
        { emptyState }
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

  	// Compare function to order sections based on time
  	let orderSections = (s1, s2) => {
  		return (s1.props.start - s2.props.start);
  	}

		// Puts a single section into the appropriate day
		let loadDay = (day, timeObject, sectionObject, timeslots) => {

			// console.log(sectionObject);

			// Conflicting section logic
			let conflictingSections = [];
			sectionObject.overlappingTimeSlots.map((slot) => {
				if ((timeslots[slot].length) > 0){
					timeslots[slot].map((section) => {
						if (!(section.title===sectionObject.title) && !conflictingSections.includes(section)) {
							conflictingSections = conflictingSections.concat([section]);
						};
					});
				};
			});

			let startString = day+"Start";
			let endString = day+"End";
			let courseId = sectionObject.courseCode;
			if (timeObject[startString] != null && timeObject[endString] !=null) {
				let startTime = timeObject[startString];
				let endTime = timeObject[endString]; 
				let newSectionObject = <CalendarSectionObject 
										key={sectionObject.sectionId} 
										id={sectionObject.sectionId}
										title={sectionObject.title} 
										department={sectionObject.department}
										start={startTime} 
										end={endTime}
										locations={this.getLocationString(sectionObject.meetingLocations)}
										conflictingSections={conflictingSections}
										onRemove={this.props.reloadCalendar} 
                    locked={this.props.locked}
										click={this.showCourseInfo.bind(this, courseId)}/>;
				this.setState((state) => {
  				state[day] = state[day].concat([newSectionObject]).sort(orderSections);
        });
			}
    }

    //Overlapping timeslot logic; messy
    let timeslots = {};
    calendar.sections.map((section) => {
			if (section.overlappingTimeSlots) {
				section.overlappingTimeSlots.map((slot) => {
					if (!timeslots[slot]) {
						timeslots[slot] = [section];
					} else {
						timeslots[slot].push(section);
					}
				});
			};
			this.setState({timeslots: timeslots});
    })

		calendar.sections.map((section) => {
			loadDay("monday", section.times, section, timeslots);
			loadDay("tuesday", section.times, section, timeslots);
			loadDay("wednesday", section.times, section, timeslots);
			loadDay("thursday", section.times, section, timeslots);
			loadDay("friday", section.times, section, timeslots);
			this.setState({currentCart: this.state.currentCart.concat([section.sectionId])});
		}, this);
  }

  getLocationString(locations){
		let weekdays = ['monday', 'tuesday', 'wednesday', 'thursday', 'friday'];
		let locationArray = new Set();
		weekdays.forEach((weekday) => {
	    	let loc = locations[weekday + 'MeetingLoc'];
		    if (loc !== null) {
	      		locationArray.add(loc);
	    	};
    	});
    	return Array.from(locationArray).join(', ');
	}


}

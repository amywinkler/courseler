import React from 'react';
import ReactDOM from 'react-dom';
import api from './api.jsx';

export default class CourseInfoScreen extends React.Component {

	constructor(props) {
		super(props);
		this.state = {
		};
  }

	render() {
		let calendarButton = <a href='#' onClick={this.props.click}>Return To Calendar</a>;

		return (
			<div>
				<h1>Course Info Page!!!!</h1>
				{calendarButton}
			</div>
		)	
	}


}

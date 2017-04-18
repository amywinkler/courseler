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
		let title = this.props.info.title;
		let code = this.props.info.code;
		let description = this.props.info.description;
		//...and so on

		let calendarButton = <a href='#' onClick={this.props.click}>Return To Calendar</a>;

		return (
			<div>
				<h2>{code}: {title}</h2>
				<p>Course Description: {description}</p>
				{calendarButton}
			</div>
		)	
	}

}

import React from 'react';
import ReactDOM from 'react-dom';
import api from './api.jsx';
import SectionInfo from './sectionInfo.jsx';

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
 		let sections = [];
 		let currCart = this.props.currentCart;
		if (this.props.info.sections != undefined) {
			sections = this.props.info.sections;
		};

		let calendarButton = <a href='#' onClick={this.props.click}>Return To Calendar</a>;
		return (
			<div className='courseInfo'>
				{calendarButton}
				<h2>{code}: {title}</h2>
				<p>{description}</p>
				<div className ='sections' style={{backgroundColor: '#f4f4f4'}}>Sections:
				{sections.map(function(section, index){
					if (1) {
						return <SectionInfo key={index} sectionId={section.section_id} time={section.times}/>
					}
        })}
        </div>
			</div>
		)	
	}


}

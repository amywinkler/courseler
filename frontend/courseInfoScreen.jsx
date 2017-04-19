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
 		let remove = this.props.remove;
 		let add = this.props.add;
		if (this.props.info.sections != undefined) {
			sections = this.props.info.sections;
		};

		let sectionInfo = sections.map(function(section, index) {
			// Checks whether the current cart has this section in it already
			if (currCart.indexOf(section.section_id) >= 0) {
				return <SectionInfo key={index} sectionId={section.section_id} time={section.times} inCart = {true} remove={remove} add={add}/>
			} else {
				return <SectionInfo key={index} sectionId={section.section_id} time={section.times} inCart = {false} remove={remove} add={add}/>
			}
		});

		let calendarButton = <a href='#' onClick={this.props.click}>Return To Calendar</a>;
		return (
			<div className='courseInfo'>
				{calendarButton}
				<h2>{code}: {title}</h2>
				<p>{description}</p>
				<div className ='sections'>Sections:
					{sectionInfo}
        		</div>
			</div>
		)	
	}

}

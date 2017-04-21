import React from 'react';
import ReactDOM from 'react-dom';
import api from './api.jsx';
import SectionInfo from './sectionInfo.jsx';

export default class CourseInfoScreen extends React.Component {

	constructor(props) {
		super(props);
		this.state = {
      info: null
    };
    api.courseInfo(this.props.courseCode, (info) => {
      this.setState({info: info});
    });
  }

	render() {
    if (this.state.info) {
      let info = this.state.info;
  		let title = info.title;
  		let code = info.courseCode;
  		let description = info.description;
      
   		let mySections = this.props.calendar ? this.props.calendar.sections : [];
      let mySectionIds = mySections.map((s) => s.sectionId);
            
  		let sectionInfo = info.sections.map((section, index) => {
  			// Checks whether the current cart has this section in it already
        let inCart = mySectionIds.indexOf(section.sectionId) >= 0;
        return <SectionInfo key={index} sectionId={section.sectionId} time={section.times} inCart = {inCart} onAdd={this.props.reloadCalendar} onRemove={this.props.reloadCalendar} />
      });

  		let calendarButton = <a href='#' onClick={this.back.bind()}>Back</a>;
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
    } else {
      return null;
    }
	}
  back() {
    history.back();
  }
}

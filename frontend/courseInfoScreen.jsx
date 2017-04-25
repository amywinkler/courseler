import React from 'react';
import ReactDOM from 'react-dom';
import api from './api.jsx';
import SectionInfo from './sectionInfo.jsx';


class CourseInfoSection extends React.Component {
  
  // props should be label and content 
  constructor(props) {
    super(props);
  }

  render() {

    if (this.props.content != null) {
      return (
        <div className="courseInfoSection">
          <div className="line"></div>
             <label>{this.props.label}</label>
             <div>{this.props.content}</div>
        </div>
      )
    } else {
      return null;
    }
  }
}


class DemographicsSection extends React.Component {
  render() {
    return (
      <div>Hi</div>
    )
  }
}



export default class CourseInfoScreen extends React.Component {

	constructor(props) {
		super(props);
		this.state = {
      info: null,
      place: '',
      time: ''
    };
    api.courseInfo(this.props.courseCode, (info) => {
      this.setState({info: info});
    });
  }

	render() {
    if (this.state.info) {

      let info = this.state.info;
      let term = info.term;
  		let title = info.title;
  		let code = info.courseCode;
  		let description = info.description;
      let emojis = this.state.info.funAndCool.emojis;

   		let mySections = this.props.calendar ? this.props.calendar.sections : [];
      let mySectionIds = mySections.map((s) => s.sectionId);
      
      let getDemographicsContent = () => {
        if (this.state.info.crData) {
          return (
            <div className="demographicsSection">
              <div className="classYearDemographics">
                <div className="freshmen" style={{width:info.crData.demographics.percent_freshmen*100+"%", backgroundColor: "#444"}}></div>
                <div className="sophomores" style={{width:info.crData.demographics.percent_sophomores*100+"%", backgroundColor: "#777"}}></div>
                <div className="junior" style={{width:info.crData.demographics.percent_juniors*100+"%", backgroundColor: "#999"}}></div>
                <div className="senior" style={{width:info.crData.demographics.percent_seniors*100+"%", backgroundColor: "#aaa"}}></div>
                <div className="other" style={{width:info.crData.demographics.percent_grad*100+"%", backgroundColor: "#ccc"}}></div>
              </div>
              <div className="concentratorDemographics">
                <div className="conc" style={{width:info.crData.demographics.percent_concentrators*100+"%", backgroundColor: "#444"}}></div>
                <div className="nonconc" style={{width:info.crData.demographics.percent_non_concentrators*100+"%", backgroundColor: "#777"}}></div>
                <div className="undecided" style={{width:info.crData.demographics.percent_undecided*100+"%", backgroundColor: "#999"}}></div>
              </div>
            </div>
          )
        } else {
          return null;
        };
      }
      

  		let sectionContent = info.sections.map((section, index) => {

        if (section.isMainSection) {
          
        } 

  			// Checks whether the current cart has this section in it already
        let inCart = mySectionIds.indexOf(section.sectionId) >= 0;
        return <SectionInfo key={index} 
                  sectionId={section.sectionId} 
                  times={section.times} 
                  inCart = {inCart} 
                  onAdd={this.props.reloadCalendar} 
                  onRemove={this.props.reloadCalendar} 
                  professors = {section.professors} 
                  locations = {section.meetingLocations} />
      });


  		let calendarButton = <a href='#' onClick={this.back.bind()}>Back</a>;
      let courseDescriptionContent = <p>{info.description}</p>
      let hoursPerWeekContent = <p>Hours Per Week Lots</p>

      return (
  			<div className='courseInfo'>
  				{calendarButton}
          <div className ="courseInfoHeader">
            <label>{term}</label> 
            <div className='emojis'>{emojis}</div>
          </div>
  				<h2>{code}: {title}</h2>
          <CourseInfoSection label='Sections' content={sectionContent} />
          <CourseInfoSection label='Description' content={courseDescriptionContent} />
          <CourseInfoSection label='Hours Per Week' content={hoursPerWeekContent} />
          <CourseInfoSection label='Demographics' content={getDemographicsContent()} />
  			</div>
  		)	
    } else {
      return null;
    }
	}

  demographics() {
    
  }


  back() {
    history.back();
  }


}

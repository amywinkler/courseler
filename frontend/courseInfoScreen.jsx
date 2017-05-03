import React from 'react';
import ReactDOM from 'react-dom';
import api from './api.jsx';
import SectionInfo from './sectionInfo.jsx';
import { currentRoute, navigateToRoute } from './routing.jsx';


/*
  A single section of the Course Info screen, 
  like "Hours per week" or "Demographics"
*/
class CourseInfoSection extends React.Component {
  
  // Props are 'label' and 'content'
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


export default class CourseInfoScreen extends React.Component {

	constructor(props) {
		super(props);
		this.state = {
      info: null,
      place: '',
      time: ''
    };
    api.courseInfo(this.props.courseCode, (info) => {
      console.log(info);
      this.setState({info: info});
    });
  }

	render() {
    if (this.state.info) {

      console.log(this.state.info);

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
                <p>Class Year Demographics</p>
                <div className="freshmen" style={{width:info.crData.demographics.percent_freshmen*100+"%", backgroundColor: "#444"}}><h5 className="demLabel">Freshmen</h5></div>
                <div className="sophomores" style={{width:info.crData.demographics.percent_sophomores*100+"%", backgroundColor: "#777"}}><h5 className="demLabel">Sophomores</h5></div>
                <div className="junior" style={{width:info.crData.demographics.percent_juniors*100+"%", backgroundColor: "#999"}}><h5 className="demLabel">Juniors</h5></div>
                <div className="senior" style={{width:info.crData.demographics.percent_seniors*100+"%", backgroundColor: "#aaa"}}><h5 className="demLabel">Seniors</h5></div>
                <div className="other" style={{width:info.crData.demographics.percent_grad*100+"%", backgroundColor: "#ccc"}}><h5 className="demLabel">Grad</h5></div>
              </div>
              <div className="concentratorDemographics">
                <p>Concentrator Demographics</p>
                <div className="conc" style={{width:info.crData.demographics.percent_concentrators*100+"%", backgroundColor: "#444"}}><h5 className="demLabel">Concentrators</h5></div>
                <div className="nonconc" style={{width:info.crData.demographics.percent_non_concentrators*100+"%", backgroundColor: "#777"}}><h5 className="demLabel">Non-Concentrators</h5></div>
                <div className="undecided" style={{width:info.crData.demographics.percent_undecided*100+"%", backgroundColor: "#999"}}><h5 className="demLabel">Undecided</h5></div>
              </div>
            </div>
          )
        } else {
          return null;
        };
      }

      let getHoursPerWeekContent = () => {
        if (this.state.info.crData) {
          let avg = this.state.info.crData.hoursPerWeek.average.toFixed(2);
          let max = this.state.info.crData.hoursPerWeek.maximum.toFixed(2);
          return <p>Average: {avg}, Maximum: {max}</p>;
        } else {
          return null;
        }
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
                  locations = {section.meetingLocations}
                  locked={this.props.locked} />
      });

      let courseDescriptionContent = <p>{info.description}</p>

      let adjectives = (this.state.info.funAndCool.descriptions != undefined) ? this.state.info.funAndCool.descriptions.map((description, index) => {
        return <div className="adj" key={index}>{description}</div>
      }) : null;

      return (
        <div>
          {this.renderHeader()}
    			<div className='courseInfo screen'>
            <div className ="courseInfoHeader">
              <label>{term}</label> 
              <div className='emojis'>{emojis}</div>
            </div>
    				<h2>{code}: {title}</h2>
            <div className ="adjectives">{adjectives}</div>
            <CourseInfoSection label='Sections' content={sectionContent} />
            <CourseInfoSection label='Description' content={courseDescriptionContent} />
            <CourseInfoSection label='Hours Per Week' content={getHoursPerWeekContent()} />
            <CourseInfoSection label='Demographics' content={getDemographicsContent()} />
    			</div>
        </div>
  		)	
    } else {
      return null;
    }
	}
  
  renderHeader() {
    return (
      <div className='header'>
        <div onClick={ () => history.back() }>
          <span className='fa fa-angle-left'/><label>Back</label>
        </div>
      </div>
    )
  }

  back() {
    history.back();
  }
}

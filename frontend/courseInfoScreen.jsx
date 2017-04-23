import React from 'react';
import ReactDOM from 'react-dom';
import api from './api.jsx';
import SectionInfo from './sectionInfo.jsx';


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
      info: null
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
            
      let demographics = () => {
        if (this.state.info.crData) {
          return (
            <div className="demographicsSection">
              <div className="line"></div>
              <label>Demographics</label>
              <div className="classYearDemographics">
                <div className="freshmen" style={{width:info.crData.demographics.percent_freshmen*100+"%", backgroundColor: "#444"}}></div>
                <div className="sophomores" style={{width:info.crData.demographics.percent_sophomores*100+"%", backgroundColor: "#777"}}></div>
                <div className="junior" style={{width:info.crData.demographics.percent_juniors*100+"%", backgroundColor: "#999"}}></div>
                <div className="senior" style={{width:info.crData.demographics.percent_seniors*100+"%", backgroundColor: "#aaa"}}></div>
                <div className="other" style={{width:info.crData.demographics.percent_grad*100+"%", backgroundColor: "#ccc"}}></div>
              </div>
              <div className="classYearDemographics">
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
      

  		let sectionInfo = info.sections.map((section, index) => {
        console.log(section);
  			// Checks whether the current cart has this section in it already
        let inCart = mySectionIds.indexOf(section.sectionId) >= 0;
        return <SectionInfo key={index} 
                  sectionId={section.sectionId} 
                  time={section.times} 
                  inCart = {inCart} 
                  onAdd={this.props.reloadCalendar} 
                  onRemove={this.props.reloadCalendar} />
      });

  		let calendarButton = <a href='#' onClick={this.back.bind()}>Back</a>;
  		
      return (
  			<div className='courseInfo'>
  				{calendarButton}
          <div className ="courseInfoHeader">
            <label>{term}</label> 
            <div className='emojis'>{emojis}</div>
          </div>
  				<h2>{code}: {title}</h2>
          <div className="line"></div>
            <label>Course Description</label>
    				  <p>{description}</p>
          <div className="line"></div>
            <label>Hours Per Week</label>
              <p>lots of hours</p>
          {demographics()}
          <div className="line"></div>
          <label>Sections</label> 
            <div className ='sections'>
              {sectionInfo}
            </div>
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

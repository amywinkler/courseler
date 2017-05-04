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
    if (this.props.content != null && this.props.content.length != 0) {
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
      time: '',
      emojis: []
    };
    api.courseInfo(this.props.courseCode, (info) => {
      this.setState({info: info});
      let emojis = (this.state.info.funAndCool.emojis != undefined) ? this.state.info.funAndCool.emojis : [];
      this.setState({emojis: emojis});
    });
  }

	render() {
    if (this.state.info) {
      // console.log(this.state.info);

      let info = this.state.info;
      let term = info.term;
  		let title = info.title;
  		let code = info.courseCode;
  		let description = info.description;
      let emojis = this.state.emojis;

   		let mySections = this.props.calendar ? this.props.calendar.sections : [];
      let mySectionIds = mySections.map((s) => s.sectionId);
      
      let getDemographicsContent = () => {
        if (this.state.info.crData) {
          return (
            <div className="demographicsSection">
              <div className="demographic">
              <h4 className="demLabel">Class Year Demographics</h4>
                <div className="gradient"></div>
                <div className="classYearDemographics">
                  <div className="freshmen graph" style={{width:info.crData.demographics.percent_freshmen*100+"%", backgroundColor: "#626BFF"}}></div>
                  <div className="sophomores graph" style={{width:info.crData.demographics.percent_sophomores*100+"%", backgroundColor: "#7E86FF"}}></div>
                  <div className="junior graph" style={{width:info.crData.demographics.percent_juniors*100+"%", backgroundColor: "#A6ABFF"}}></div>
                  <div className="senior graph" style={{width:info.crData.demographics.percent_seniors*100+"%", backgroundColor: "#CACDFF"}}></div>
                  <div className="other graph" style={{width:info.crData.demographics.percent_grad*100+"%", backgroundColor: "#DCDEFF"}}></div>
                </div>
                <div className="key">
                  <div className="keyRow"><div className="keySquare" style={{backgroundColor: "#626BFF"}}></div><label>Freshmen: {(info.crData.demographics.percent_freshmen*100).toFixed(2)}%</label></div>
                  <div className="keyRow"><div className="keySquare" style={{backgroundColor: "#7E86FF"}}></div><label>Sophomores: {(info.crData.demographics.percent_sophomores*100).toFixed(2)}%</label></div>
                  <div className="keyRow"><div className="keySquare" style={{backgroundColor: "#A6ABFF"}}></div><label>Juniors: {(info.crData.demographics.percent_juniors*100).toFixed(2)}%</label></div>
                  <div className="keyRow"><div className="keySquare" style={{backgroundColor: "#CACDFF"}}></div><label>Seniors: {(info.crData.demographics.percent_seniors*100).toFixed(2)}%</label></div>
                  <div className="keyRow"><div className="keySquare" style={{backgroundColor: "#DCDEFF"}}></div><label>Grad: {(info.crData.demographics.percent_grad*100).toFixed(2)}%</label></div>
                </div>
              </div>
              <div className="demographic">
                <h4 className="demLabel">Concentrator Demographics</h4>
                <div className="gradient"></div>
                <div className="concentratorDemographics">              
                  <div className="conc graph" style={{width:info.crData.demographics.percent_concentrators*100+"%", backgroundColor: "#3CEEE5"}}></div>
                  <div className="nonconc graph" style={{width:info.crData.demographics.percent_non_concentrators*100+"%", backgroundColor: "#84FFFA"}}></div>
                  <div className="undecided graph" style={{width:info.crData.demographics.percent_undecided*100+"%", backgroundColor: "#C5FFFD"}}></div>
                </div>
                <div className="key">
                  <div className="keyRow"><div className="keySquare" style={{backgroundColor: "#3CEEE5"}}></div><label>Concentrator: {(info.crData.demographics.percent_concentrators*100).toFixed(2)}%</label></div>
                  <div className="keyRow"><div className="keySquare" style={{backgroundColor: "#84FFFA"}}></div><label>Non-concentrator: {(info.crData.demographics.percent_non_concentrators*100).toFixed(2)}%</label></div>
                  <div className="keyRow"><div className="keySquare" style={{backgroundColor: "#C5FFFD"}}></div><label>Undecided: {(info.crData.demographics.percent_undecided*100).toFixed(2)}%</label></div>
                </div>
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


      // Differentiation of section types
      let sections = [];
      let conferences = [];
      let filmScreenings = [];

      // Add each section in the course to its corresponding group
  		info.sections.map((section, index) => {
        let inCart = mySectionIds.indexOf(section.sectionId) >= 0;
        let sectionObject =  <SectionInfo key={index} 
                  sectionId={section.sectionId} 
                  times={section.times} 
                  inCart = {inCart} 
                  onAdd={this.props.reloadCalendar} 
                  onRemove={this.props.reloadCalendar} 
                  professors = {section.professors} 
                  locations = {section.meetingLocations}
                  locked={this.props.locked} 
                  shared={this.props.shared} />;

        switch(section.sectionType) {
          case 'film screening':
            filmScreenings.push(sectionObject);
            break;
          case 'conference':
            conferences.push(sectionObject);
            break;
          default:
            sections.push(sectionObject);
        }

                
      });

      let courseDescriptionContent = <p>{info.description}</p>

      let adjectives = (this.state.info.funAndCool.descriptions != undefined) ? this.state.info.funAndCool.descriptions.map((description, index) => {
        return <div className="adj" key={index}>{description}</div>
      }) : null;

      let altTitles = (this.state.info.funAndCool.alternate_titles != undefined) ? 
        <div className="altTitles">
        <div className="altTitle">also known as... </div>
          {this.state.info.funAndCool.alternate_titles.map((altTitle, index) => {
            return <div className="altTitle" key={index}>"{altTitle}"</div>
          })}
        </div>
      : null;

      let addEmojiVisibility = this.addEmojiVisibility();

      return (
        <div>
          {this.renderHeader()}
    			<div className='courseInfo screen'>
            <div className ="courseInfoHeader">
              <label>{term}</label> 
              <div className='emojis'>{emojis}</div>
              <div className='add-emoji' onClick={
                this.addEmoji.bind(this)
              } style = {addEmojiVisibility}>⊕</div>
              <input id = "emoji-input-box" onChange={
                this.emojiChange.bind(this)
              } style = {addEmojiVisibility} />
              <p id="emoji-error"></p>
            </div>
    				<h2>{code}: {title}</h2>
            {altTitles}
            <div className ="adjectives">{adjectives}</div>
            <CourseInfoSection label='Sections' content={sections} />
            <CourseInfoSection label='Conferences' content={conferences} />
            <CourseInfoSection label='Film Screenings' content={filmScreenings} />
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

  addEmoji(e){
    if (!e) var e = window.event;
    if (e.stopPropagation) e.stopPropagation();
    let emojiBox = $('#emoji-input-box');
    emojiBox.show();
    emojiBox.emojiPicker({
      height: '200px',
      width:  '300px'
    });
    $('.add-emoji').hide();
  }

  addEmojiVisibility() {
    let numEmojis = this.state.emojis.length;
    if (numEmojis <5 ){
      return ({});
    } 
  }

  emojiChange(e) {
    let emojiVal = $('#emoji-input-box');
    console.log(this.state.info.courseCode);
    if (emojiVal.val().length == 2) {
      api.addEmoji(this.state.info.courseCode, emojiVal.val());
      let currEmojis = this.state.emojis;
      this.setState({emojis: currEmojis.concat(emojiVal.val())});
    } else {
      console.log("unkown error");
    }
    emojiVal.val("");
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

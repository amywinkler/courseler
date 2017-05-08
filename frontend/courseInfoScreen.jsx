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
      emojis: [],
      adjectives: [],
      altTitles: []
    };
    api.courseInfo(this.props.courseCode, (info) => {
      this.setState({info: info});

      // function to shuffle emojis
      let shuffle = (array) => {
        let j;
        let x;
        let i;
        for (i = array.length; i; i--) {
            j = Math.floor(Math.random() * i);
            x = array[i - 1];
            array[i - 1] = array[j];
            array[j] = x;
        }
        return array;
      };

      // get the shuffled emoji array (but keep the dept emoji first)
      let getShuffledEmojiArray = (array) => {
        let copy = shuffle(array.slice(1)).slice(0,4);
        return array.slice(0,1).concat(copy);
      }

      let emojis = (this.state.info.funAndCool.emojis != undefined) ? getShuffledEmojiArray(this.state.info.funAndCool.emojis) : [];
      this.setState({emojis: emojis});
      let adjectives = (this.state.info.funAndCool.descriptions != undefined) ? this.state.info.funAndCool.descriptions : [];
      this.setState({adjectives: adjectives});
      let altTitles = (this.state.info.funAndCool.alternate_titles != undefined) ? this.state.info.funAndCool.alternate_titles : [];
      this.setState({altTitles: altTitles});
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
                  <div className="freshmen graph" style={{width:info.crData.demographics.percent_freshmen*100+"%", backgroundColor: "#F980FF"}}></div>
                  <div className="sophomores graph" style={{width:info.crData.demographics.percent_sophomores*100+"%", backgroundColor: "#A527E8"}}></div>
                  <div className="junior graph" style={{width:info.crData.demographics.percent_juniors*100+"%", backgroundColor: "#A161FF"}}></div>
                  <div className="senior graph" style={{width:info.crData.demographics.percent_seniors*100+"%", backgroundColor: "#664CE8"}}></div>
                  <div className="other graph" style={{width:info.crData.demographics.percent_grad*100+"%", backgroundColor: "#5461FF"}}></div>
                </div>
                <div className="key">
                  <div className="keyRow"><div className="keySquare" style={{backgroundColor: "#F980FF"}}></div><label>Freshmen: {(info.crData.demographics.percent_freshmen*100).toFixed(2)}%</label></div>
                  <div className="keyRow"><div className="keySquare" style={{backgroundColor: "#A527E8"}}></div><label>Sophomores: {(info.crData.demographics.percent_sophomores*100).toFixed(2)}%</label></div>
                  <div className="keyRow"><div className="keySquare" style={{backgroundColor: "#A161FF"}}></div><label>Juniors: {(info.crData.demographics.percent_juniors*100).toFixed(2)}%</label></div>
                  <div className="keyRow"><div className="keySquare" style={{backgroundColor: "#664CE8"}}></div><label>Seniors: {(info.crData.demographics.percent_seniors*100).toFixed(2)}%</label></div>
                  <div className="keyRow"><div className="keySquare" style={{backgroundColor: "#5461FF"}}></div><label>Grad: {(info.crData.demographics.percent_grad*100).toFixed(2)}%</label></div>
                </div>
              </div>
              <div className="demographic">
                <h4 className="demLabel">Concentrator Demographics</h4>
                <div className="gradient"></div>
                <div className="concentratorDemographics">
                  <div className="conc graph" style={{width:info.crData.demographics.percent_concentrators*100+"%", backgroundColor: "#A5FFCA"}}></div>
                  <div className="nonconc graph" style={{width:info.crData.demographics.percent_non_concentrators*100+"%", backgroundColor: "#79E8E7"}}></div>
                  <div className="undecided graph" style={{width:info.crData.demographics.percent_undecided*100+"%", backgroundColor: "#8BC2FF"}}></div>
                </div>
                <div className="key">
                  <div className="keyRow"><div className="keySquare" style={{backgroundColor: "#A5FFCA"}}></div><label>Concentrator: {(info.crData.demographics.percent_concentrators*100).toFixed(2)}%</label></div>
                  <div className="keyRow"><div className="keySquare" style={{backgroundColor: "#79E8E7"}}></div><label>Non-concentrator: {(info.crData.demographics.percent_non_concentrators*100).toFixed(2)}%</label></div>
                  <div className="keyRow"><div className="keySquare" style={{backgroundColor: "#8BC2FF"}}></div><label>Undecided: {(info.crData.demographics.percent_undecided*100).toFixed(2)}%</label></div>
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

      let adjectives =  this.state.adjectives.map((description, index) => {
        return <div className="adj" key={index}>{description}</div>
      });

      let altTitles = (this.state.altTitles.map((altTitle, index) => {
        return '"' + altTitle + '"'
      })).join(", ");

      let addEmojiVisibility = this.addEmojiVisibility();

      let areAdj = (this.state.adjectives && this.state.adjectives.length) > 0;
      let addAdjStyle = areAdj ? {display: 'none'} : {};
      let altTitlesExist = (this.state.altTitles && this.state.altTitles.length) > 0;
      let akaStyle = altTitlesExist ? {} : {display: 'none'};

      // Compare function to order sections alphabetically
      let orderSections = (s1, s2) => {
            if(s1.props.sectionId < s2.props.sectionId) return -1;
            if(s1.props.sectionId > s2.props.sectionId) return 1;
            return 0;
      };

      return (
        <div>
          {this.renderHeader()}
    			<div className='courseInfo screen'>
            <div className ="courseInfoHeader">
              <label>{term}</label>
              <div className='emojis'>{emojis}</div>
              <input id = "emoji-input-box" ref={(input) => { this.addEmojiBox(input)}} onChange={(e) => this.emojiChange(e)} />
              <p id="emoji-error"></p>
            </div>
    				<h2>{code}: {title}</h2>
            <div className="altTitles">
              <div className="altTitle">
                <span style={akaStyle}>also known as...</span>
                <span style={akaStyle} className="titles">{altTitles}</span>
                <div className='add-alttitle' onClick={
                  this.addAltTitle.bind(this) } >add alternate title</div>
                <input id = "alttitle-input-box" ref={this.listenToPage()} onKeyPress={this.handleEnterTitle} />
                </div>
              </div>
            <div className ="adjectives">
             {adjectives}  <div className="adj" id="addAdj" onClick={this.addWord.bind(this)}> Add adjective</div>
            <input id = "word-input-box" ref={this.listenToPage()} onKeyDown={this.handleTypeWordNoSpaces} onKeyPress={this.handleEnterWord} />
            </div>
            <CourseInfoSection label='Sections' content={sections.sort(orderSections)} />
            <CourseInfoSection label='Conferences' content={conferences.sort(orderSections)} />
            <CourseInfoSection label='Film Screenings' content={filmScreenings.sort(orderSections)} />
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

  listenToPage(){
    console.log('hit page');
    window.addEventListener('mousedown', this.pageClick, false);
  }

  pageClick (e) {
    if(e.srcElement.id != 'alttitle-input-box' && e.srcElement.id != 'word-input-box'){
    $('#word-input-box').hide();
    $('#addAdj').show();
    $('.add-alttitle').show();
    $('#alttitle-input-box').hide();
    }
  }
/* Add emoji box to screen!!!!*/
  addEmojiBox(emojiBox){
    // let emojiLen = 0;
    // if(this.state.emojis) {
    //   emojiLen = this.state.emojis.lenght
    // }
    // console.log(this.state.emojis);
    $(emojiBox).emojiPicker({
      height: '200px',
      width:  '300px'
    });
  }

  addWord(e){
    if (!e) var e = window.event;
    if (e.stopPropagation) e.stopPropagation();
    let wordBox = $('#word-input-box');
    wordBox.show();

    $('#addAdj').hide();
  }

  addAltTitle(e){
    if (!e) var e = window.event;
    if (e.stopPropagation) e.stopPropagation();
    let titleBox = $('#alttitle-input-box');
    titleBox.show();

    $('.add-alttitle').hide();
  }


  handleEnterWord = (e) => {

    if (e.key === 'Enter') {
      this.wordChange(e);
      $('#alttitle-input-box').hide();
      $('.add-alttitle').show();
    }
  }

  handleEnterTitle = (e) => {

    if (e.key === 'Enter') {
      this.titleChange(e);
      $('#alttitle-input-box').hide();
      $('.add-alttitle').show();
    }
  }


  handleTypeWordNoSpaces = (e) => {
    if(e.key === " "){
    e.preventDefault();
    }
  }

  addEmojiVisibility() {
    let numEmojis = this.state.emojis.length;
    if (numEmojis <5 ){
      return ({});
    }
  }

  emojiChange(e) {
    let emojiVal = $('#emoji-input-box');
    if (emojiVal && emojiVal.val().length == 2) {
      api.addEmoji(this.state.info.courseCode, emojiVal.val());
      let currEmojis = this.state.emojis;
      this.setState({emojis: currEmojis.concat(emojiVal.val())});
    } else {
      console.log("unkown error");
    }
    emojiVal.val("");
  }

  wordChange(e) {

    let wordVal = $('#word-input-box');
    let Filter = require('bad-words'),
    filter = new Filter();
    var words = require("an-array-of-english-words")

    let wordToAdd = filter.clean(wordVal.val());
    if(wordVal.val() != wordToAdd || !words.includes(wordVal.val()) ||
    this.state.adjectives.includes(wordVal.val().toLowerCase())){
      alert('Must add real words that are not profanity and have not already been added!')
    } else if(wordVal.val().trim()) {
      api.addWord(this.state.info.courseCode, wordToAdd);
      let currWords = this.state.adjectives;
      this.setState({adjectives: currWords.concat(wordToAdd)});
      wordVal.val("");
      wordVal.hide();
    }
  }



  titleChange(e) {

    let titleVal = $('#alttitle-input-box');
    let Filter = require('bad-words'),
    filter = new Filter();
    var words = require("an-array-of-english-words")


    let wordToAdd = filter.clean(titleVal.val());
    if(titleVal.val() != wordToAdd ||
    this.state.altTitles.includes(titleVal.val().toLowerCase()) || !titleVal.val().trim()){
      alert('Must add real title that is not profanity and has not already been added!')
    } else {
      api.addAltName(this.state.info.courseCode, titleVal.val());
      let currTitles = this.state.altTitles;
      this.setState({altTitles: currTitles.concat(wordToAdd)});
      titleVal.val("");
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

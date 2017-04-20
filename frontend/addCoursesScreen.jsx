import React from 'react';
import ReactDOM from 'react-dom';
import api from './api.jsx';

class CourseCell extends React.Component {
  render() {
    return (
      <div className='CourseCell' onClick={this.props.onClick}>
        <h4>{ this.props.course.title }</h4>
        <p>{ this.props.course.course_code } - {this.courseTimes()}</p>
      </div>
    )
  }
  courseTimes() {
    return "MWF 10:00–10:50 THIS IS FAKE";
  }
}

class SectionCell extends React.Component {
  render() {
    let courseCells = this.props.section.courses.map((course, i) => {
      return <CourseCell key={i} course={course} onClick={() => this.props.onClickedCourse(course) } />;
    });
    return (
      <div className='SectionCell'>
        <label>{ this.props.section.name }</label>
        <div className='courses hscroll'>{ courseCells }</div>
      </div>
    )
  }
}

export default class AddCoursesScreen extends React.Component {
  constructor(props) {
     super(props);
     this.state = {
       search: '',
       recommendations: null,
       results: null,
       filters: {}
     };
     this.loadRecommendations({});
   }
   
  render() {
    return (
    <div className='AddCourses'>
      <form className='search' onSubmit={(e) => this.submit(e)}>
        <input type='text' onChange={(e) => this.updateSearchQuery(e)} placeholder='Search courses...' value={this.state.search} />
      </form>
      { this.renderContent() }
      <div className='back' onClick={() => this.props.onDone()}>Back to Calendar</div>
    </div>
    )
    return <h1>add courses go here! <a href='#' onClick={() => this.props.onDone()}>done</a></h1>
  }
  
  updateSearchQuery(e) {
    let text = e.target.alue
    this.setState({search: e.target.value});
    if (text) {
      // update search results:
      this.loadSearchResults(text);
    }
  }
  
  loadRecommendations(filters) {
    api.getRecommendations(filters, (results) => {
      this.setState({recommendations: results});
    });
  }
  
  loadSearchResults(query) {
    api.search(null, query, (results) => {
      this.setState({results: results});
    })
  }
  
  submit(e) {
    // do nothing
    e.preventDefault();
  }
  renderContent() {
    if (this.state.search) {
      return this.renderRecommendations();
    } else {
      return this.renderSearchResults();
    }
  }
  renderRecommendations() {
    let sections = this.state.recommendations || [];
    let sectionCells = sections.map((section, i) => {
      return <SectionCell key={i} section={section} onClickedCourse={(course) => this.clickedCourse(course)} />;
    });
    return <div className='recommendations'>{sectionCells}</div>;
  }
  renderSearchResults() {
    let cells = (this.state.results || []).map((course, i) => {
      return <CourseCell key={i} course={course} onClick={() => this.clickedCourse(course)} />;
    });
    return <div className='results'>{cells}</div>; 
  }
  clickedCourse(course) {
    // TODO
  }
}

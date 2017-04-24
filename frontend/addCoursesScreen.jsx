import React from 'react';
import ReactDOM from 'react-dom';
import api from './api.jsx';
import { currentRoute, navigateToRoute } from './routing.jsx';
import { descriptionForCourseTimes } from './timeFormatter.jsx';

// from http://werxltd.com/wp/2010/05/13/javascript-implementation-of-javas-string-hashcode-method/
function hashCode(string) {
	let hash = 0;
	if (string.length == 0) return hash;
	for (let i = 0; i < string.length; i++) {
		let char = string.charCodeAt(i);
		hash = ((hash<<5)-hash)+char;
		hash = hash & hash; // Convert to 32bit integer
	}
	return hash;
}

let colors = [
  '#F8FD58',
  '#2CFD83',
  '#FC54B8',
  '#52F8FE'
];

function colorForCourse(course) {
  let hash = Math.abs(hashCode(course.title));
  return colors[hash % colors.length];
}

class CourseCell extends React.Component {
  render() {
    let style = {backgroundColor: colorForCourse(this.props.course)};
    return (
      <div className='CourseCell' onClick={this.props.onClick} style={style}>
        <h4>{this.props.course.courseCode}: { this.props.course.title }</h4>
        <p>{this.courseTimes()}</p>
      </div>
    )
  }
  courseTimes() {
    return descriptionForCourseTimes(this.props.course);
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
        <div className='courses hscroll'><div>{ courseCells }</div></div>
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
        <input type='text' onChange={(e) => this.updateSearchQuery(e)} placeholder='Search courses...' value={this.state.search} className='search-field' placeholder='search for courses or departments' />
      </form>
      { this.renderContent() }
      <div className='back floating-button' onClick={() => this.done()}>Back to Calendar</div>
    </div>
    )
  }
  
  updateSearchQuery(e) {
    let text = e.target.value
    this.setState({search: text});
    if (text) {
      // update search results:
      this.loadSearchResults(text);
    }
  }
  
  updateFilters(key,value) {
    let copy = JSON.parse(JSON.stringify(this.state.filters));
    if (value) {
      copy[key] = value;
    } else {
      delete copy[key];
    }
    this.setState({filters: copy});
    loadRecommendations(copy);
  }
  
  // doing requests:
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
      return this.renderSearchResults();
    } else {
      return this.renderRecommendations();
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
    navigateToRoute({screen: 'course', courseCode: course.courseCode});
  }
  done() {
    navigateToRoute({});
  }
}

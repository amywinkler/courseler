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
        <div className='bottom'>
          <div className='times'>{this.courseTimes()}</div>
          <div className='emoji'>{this.props.course.funAndCool.emojis.join(' ')}</div>
        </div>
      </div>
    )
  }
  courseTimes() {
    return descriptionForCourseTimes(this.props.course);
  }
}

class FilterEditor extends React.Component {
  options() {
    return [
      {key: 'open', values: [false, true], labels: ['Any time', 'Avoid time conflicts']},
      {key: 'less_than_10_hours', values: [false, true], labels: ['Any workload', 'Less than 10 hours/wk']},
      {key: 'small_courses', values: [false, true], labels: ['Any size', 'Small courses only']},
      {key: 'cap', values: [false, true], labels: ['Capped + uncapped', 'Capped classes only']}
    ]
  }
  render() {
    let items = this.options().map((o) => this.renderField(o));
    return <div className='FilterEditor'>{items}</div>;
  }
  renderField(item) {
    let fieldVal = this.props.filters[item.key];
    let options = item.values.map((val, i) => {
      return <option key={i} value={val}>{ item.labels[i] }</option>;
    });
    let onChange = (e) => {
      let i = e.target.selectedIndex;
      if (i >= 0) this.updateFilter(item.key, item.values[i]);
    }
    return <select key={item.key} onChange={onChange} value={fieldVal}>{options}</select>
  }
  updateFilter(key, value) {
    let f = JSON.parse(JSON.stringify(this.props.filters || {}));
    f[key] = value;
    this.props.onFiltersChanged(f);
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
       search: localStorage.searchQuery || '',
       recommendations: null,
       results: null,
       filters: JSON.parse(localStorage.filters || '{}')
     };
     if (this.state.search) {
       this.loadSearchResults(this.state.search);
     }
     this.loadRecommendations(this.state.filters);
   }
   
  render() {
    return (
    <div className='AddCourses screen'>
      <div className='header'>
        <form className='search' onSubmit={(e) => this.submit(e)}>
          <span className='fa fa-search' />
          <input type='text' onChange={(e) => this.updateSearchQuery(e)} placeholder='Search courses...' value={this.state.search} className='search-field' placeholder='search for courses or departments' />
        </form>
        <div className='flexible-space' />
        <div onClick={this.done.bind('this')}>
          <label>Done</label><span className='fa fa-check'/>
        </div>
      </div>
      { this.renderContent() }
    </div>
    )
  }
  
  updateSearchQuery(e) {
    let text = e.target.value;
    localStorage.searchQuery = text;
    this.setState({search: text});
    if (text) {
      // update search results:
      this.loadSearchResults(text);
    }
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
    let updateFilters = (filters) => {
      this.setState({filters: filters});
      localStorage.filters = JSON.stringify(filters);
      this.loadRecommendations(filters);
    };
    return <div className='recommendations'>
              <FilterEditor filters={this.state.filters} onFiltersChanged={updateFilters} />
              {sectionCells}
           </div>;
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

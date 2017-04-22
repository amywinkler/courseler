import React from 'react';
import ReactDOM from 'react-dom';
import api from './api.jsx';
import { currentRoute, navigateToRoute } from './routing.jsx';

export default class PreferencesScreen extends React.Component {

  constructor(props) {
     super(props);
     this.state = {
       classYear: '',
       concentration: '',
       favoriteClass: '',
       departmentalInterests: '',
       departments: []
     };
     this.displayExistingPreferences();
     this.loadDepartments();
  }

  render() {

    // console.log(this.state.departments);

    let classYearField = <input type='text' value={this.state.classYear} onChange={ (e) => {this.setState({classYear: e.target.value})} } />;
    let favoriteClassField = <input type='text' value={this.state.favoriteClass} onChange={ (e) => {this.setState({favoriteClass: e.target.value})} } />;
    let departmentalInterestsField = <input type='text' value={this.state.departmentalInterests} onChange={ (e) => {this.setState({departmentalInterests: e.target.value})} } />;
    let concentrationField = (
      <select name="deptInterests" onChange={ (e) => {console.log(e.target.value)}}> 
        {this.state.departments}
      </select>
    );

    let doneButton = <a href='#' onClick={() => this.done()}>done</a>;

    return (
      <div className='preferences'>
        <h1>User Preferences! </h1>
        <div>
          <label>Class Year</label>
          {classYearField}
        </div>
        <div>
          <label>Concentration</label>
          {concentrationField}
        </div>
        <div>
          <label>Favorite Class</label>
          {favoriteClassField}
        </div>
        <div>
          <label>Departmental Interests</label>
          {departmentalInterestsField}
        </div>
        {doneButton}
      </div>
    )
  }

  /*
    Called when user hits the 'done' button.
    Sends data to backend, and executes onDone
  */
  done() {
    this.post(); 
    navigateToRoute({});
  }

  /*
    Sends preferences to backend
  */
  post() {
    let prefs = {
      class_year: this.state.classYear,
      concentration: this.state.concentration,
      favorite_class: this.state.favoriteClass,
      dept_interests: this.state.departmentalInterests
    };
    api.postPrefs(prefs);
  }


  /*
    Displays any existing user preferences using the api call.
  */
  displayExistingPreferences() {
    let display = (preferences) => {
      this.setState({classYear: preferences.class_year});
      this.setState({concentration: preferences.concentration});
      this.setState({favoriteClass: preferences.favorite_class});
      this.setState({departmentalInterests: preferences.dept_interests});
    }
    api.getPrefs(display);
  }

  loadDepartments() {
    let departmentDropdown = (departments) => {
      this.setState({departments: departments.map((department, index) => {
        return <option key={index} value={department}>{department}</option>;
      })});
    };
    api.getDepartments(departmentDropdown);
  }


}

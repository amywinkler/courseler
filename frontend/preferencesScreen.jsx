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
       departmentalInterests: '',
       departments: [] //for the dropdown
     };
     this.displayExistingPreferences();
     this.loadDepartments();
  }

  render() {

    let classYearField = (
      <div className="classYear">
        <select name="classYear" 
            value={this.state.classYear} 
            onChange={ (e) => {this.setState({classYear: e.target.value})} }> 
          <option value="Freshman">Freshman</option>
          <option value="Sophomore">Sophomore</option>
          <option value="Junior">Junior</option>
          <option value="Senior">Senior</option>
          <option value="Graduate Student">Grad</option>
        </select>
      </div>
    );

    let concentrationField = (
      <div className="concentration">
        <select name="concentration" 
            value={this.state.concentration} 
            onChange={ (e) => {this.setState({concentration: e.target.value})} }> 
          {this.state.departments}
        </select>
      </div>
    );

    // let departmentalInterestsField = <input type='text' value={this.state.departmentalInterests} onChange={ (e) => {this.setState({departmentalInterests: e.target.value})} } />;
    let departmentalInterestsField = (
      <div className="departmentalInterests">
        <select name="departmentalInterests" 
            value={this.state.departmentalInterests} 
            onChange={ (e) => {
              this.setState({departmentalInterests: e.target.value})
            } }> 
          {this.state.departments}
        </select>
        <div className='addMoreInterests'>+</div>
      </div>
    );

    let doneButton = <a href='#' onClick={() => this.done()}>done</a>;

    return (
      <div className='preferences'>
        <h3>Preferences</h3>
        <div className="line"></div>
        <div className="prefSection">
          <label>Class Year</label>
            {classYearField}
        </div>
        <div className="line"></div>
        <div className="prefSection">
          <label>Concentration</label>
            {concentrationField}
        </div>
        <div className="line"></div>
        <div className="prefSection">
          <label>Departmental Interests</label>
            {departmentalInterestsField}
        </div>
        <div className="prefSection">
          {doneButton}
        </div>
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
      year: this.state.classYear,
      concentration: this.state.concentration,
      interests: this.state.departmentalInterests
    };
    api.postPrefs(prefs);
  }


  /*
    Displays any existing user preferences using the api call.
  */
  displayExistingPreferences() {
    let display = (prefs) => {
      this.setState({classYear: prefs.class_year==undefined ? '' : prefs.class_year});
      this.setState({concentration: prefs.concentration==undefined ? '' : prefs.concentration});
      this.setState({departmentalInterests: prefs.dept_interests==undefined ? '' : prefs.dept_interests});
    }
    api.getPrefs(display);
  }

  /*
    Loads the list of departments into the dropdowns using the api call.
  */
  loadDepartments() {
    let departmentDropdown = (departments) => {
      this.setState({departments: departments.map((department, index) => {
        return <option key={index} value={department}>{department}</option>;
      })});
    };
    api.getDepartments(departmentDropdown);
  }

}

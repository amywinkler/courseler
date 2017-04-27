import React from 'react';
import ReactDOM from 'react-dom';
import api from './api.jsx';
import { currentRoute, navigateToRoute } from './routing.jsx';

class InterestField extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      interest: ''
    }
  }
  componentWillReceiveProps() {
    this.setState({interest: this.props.interests[this.props.index]});
  }
  render() {
    return (
      <select name="interest" 
          value={this.state.interest} 
          onChange={ (e) => {
            this.changeInterest(e);
          } }> 
        {this.props.departments}
      </select>
    )
  }
  changeInterest(e) {
    this.setState({interest: [e.target.value]});
    this.props.onchange(e.target.value, this.props.index);
  }
}

class ConcentrationField extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      concentration: ''
    }
  }
  componentWillReceiveProps() {
    this.setState({concentration: this.props.concentrations[this.props.index]});
  }
  render() {
    return (
      <select name="concentration" 
          value={this.state.concentration} 
          onChange={ (e) => {
            this.changeConcentration(e);
          } }> 
        {this.props.departments}
      </select>
    )
  }
  changeConcentration(e) {
    this.setState({concentration: [e.target.value]});
    this.props.onchange(e.target.value, this.props.index);
  }
}


export default class PreferencesScreen extends React.Component {

  constructor(props) {
     super(props);
     this.state = {
       classYear: '',
       concentration: [],
       departmentalInterests: [],
       departments: [] //for the dropdowns
     };
     this.displayExistingPreferences();
     this.loadDepartments();
  }

  render() {

    let classYearField = (
      <select name="classYear" 
          value={this.state.classYear}
          onChange={ (e) => {this.setState({classYear: e.target.value})} }> 
        <option value="Freshman">Freshman</option>
        <option value="Sophomore">Sophomore</option>
        <option value="Junior">Junior</option>
        <option value="Senior">Senior</option>
        <option value="Graduate Student">Grad</option>
      </select>
    );

    let concentrationFields = (
      <div className="concentrationFields">
      <ConcentrationField 
        index='0' 
        concentrations={this.state.concentration}
        departments={this.state.departments} 
        onchange={this.updateConcentration.bind(this)}/>
      <ConcentrationField 
        index='1' 
        concentrations={this.state.concentration}
        departments={this.state.departments} 
        onchange={this.updateConcentration.bind(this)}/>
      <ConcentrationField 
        index='2' 
        concentrations={this.state.concentration}
        departments={this.state.departments} 
        onchange={this.updateConcentration.bind(this)}/>
      </div>
    )

    let interestsFields = (
      <div className="interestsFields">
      <InterestField 
        index='0' 
        interests={this.state.departmentalInterests}
        departments={this.state.departments} 
        onchange={this.updateInterest.bind(this)}/>
      <InterestField 
        index='1' 
        interests={this.state.departmentalInterests}
        departments={this.state.departments} 
        onchange={this.updateInterest.bind(this)}/>
      <InterestField 
        index='2' 
        interests={this.state.departmentalInterests}
        departments={this.state.departments} 
        onchange={this.updateInterest.bind(this)}/>
      <InterestField 
        index='3' 
        interests={this.state.departmentalInterests}
        departments={this.state.departments} 
        onchange={this.updateInterest.bind(this)}/>
      <InterestField 
        index='4' 
        interests={this.state.departmentalInterests}
        departments={this.state.departments} 
        onchange={this.updateInterest.bind(this)}/>
      </div>
    )

    let doneButton = <a href='#' onClick={() => this.done()}>done</a>;
    let addConcentrationButton = <div className='addMore' onClick={() => this.updateConcentration('', this.state.concentration.length+1)} >+</div>;
    let addInterestButton = <div className='addMore'>+</div>;

    return (
      <div>
        {this.renderHeader()}
        <div className='preferences screen'>
          <h3>Preferences</h3>
          <div className="line"></div>
          <div className="prefSection">
            <label>Class Year</label>
            {classYearField}
          </div>
          <div className="line"></div>
          <div className="prefSection">
            <label>Concentration</label>
            {concentrationFields}
          </div>
          <div className="line"></div>
          <div className="prefSection">
            <label>Departmental Interests</label>
            {interestsFields}
          </div>
          <div className="line" />
          <div className="prefSection">
            <div className='logOut' onClick={this.logOut.bind(this)}>Log out</div>
          </div>
        </div>
      </div>
    )
  }
  
  renderHeader() {
    return (
      <div className='header'>
        <div className='flexible-space' />
        <div onClick={this.done.bind(this)}>
          <label>Save</label><span className='fa fa-check' />
        </div>
      </div>
    )
  }
  
  logOut() {
    api.logOut();
    this.props.onLogOut();
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
      concentration: this.state.concentration.join(','),
      interests: this.state.departmentalInterests.join(',')
    };
    api.postPrefs(prefs);
  }

  /*
    Displays any existing user preferences using the api call.
  */
  displayExistingPreferences() {
    let display = (prefs) => {
      this.setState({classYear: prefs.class_year==undefined ? '' : prefs.class_year});
      this.setState({concentration: prefs.concentration==undefined ? [] : prefs.concentration});
      this.setState({departmentalInterests: prefs.dept_interests==undefined ? [] : prefs.dept_interests});
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

  /*
    Updates the current list of concentrations in the state
    Called when a concentration dropdown field is changed
  */
  updateConcentration(newConc, index) {
    let newConcentrationArray = this.state.concentration;
    newConcentrationArray[index] = newConc;
    this.setState({concentration: newConcentrationArray});
  }

  /*
    Updates the current list of departmental interests in the state
    Called when an interest dropdown field is changed
  */
  updateInterest(newInterest, index) {
    let newInterestArray = this.state.departmentalInterests;
    newInterestArray[index] = newInterest;
    this.setState({departmentalInterests: newInterestArray});
  }

}

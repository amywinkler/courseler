import React from 'react';
import ReactDOM from 'react-dom';
import api from './api.jsx';
import { currentRoute, navigateToRoute } from './routing.jsx';

class InterestField extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      inputtedText: '',
      interest: ''
    }
    this.interestSet = false;
  }
  componentWillReceiveProps() {
    this.setState({interest: this.props.interests[this.props.index]});
    this.interestSet = true;
  }
  componentDidUpdate() {
    if (this.interestSet) {
      this.interestSet = false;
      this.refs.myInput.value = (this.state.interest==undefined) ? '' : this.state.interest;
    }
  }
  render() {
    // For autocomplete dropdown 
    const departments = this.props.departments;
    const id = "interest"+this.props.index;
    $( "#"+id ).autocomplete({
      source: departments,
      change: this.handleChangeAutocomplete.bind(this)
    },this);

    return (
      <div className="concentrationField">
        <div className="ui-widget">
          <input id = {id}
            ref='myInput'
            onChange={ (e) => {
              this.handleChange(e);
            } } />
        </div>
      </div>
    )
  }
  changeInterest(e) {
    this.setState({interest: [e.target.value]});
    this.props.onchange(e.target.value, this.props.index);
  }
  handleChangeAutocomplete(e, ui) {
    if (ui.item != null) {
      this.setState({inputtedText: ui.item.value});
      this.setState({interest: [ui.item.value]});
      this.props.onchange(ui.item.value, this.props.index);
    }
  }
  handleChange(e) {
    this.setState({inputtedText: e.target.value});
    // Only change the interest if it exists in props.departments
    if (this.props.departments.includes(e.target.value)) {
      this.setState({interest: [e.target.value]});
      this.props.onchange(e.target.value, this.props.index);
    };
  }
}

class ConcentrationField extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      inputtedText: '',
      concentration: ''
    }
    this.concentrationSet = false;
  }

  componentWillReceiveProps() {
    this.setState({concentration: this.props.concentrations[this.props.index]});
    this.concentrationSet = true;
  }

  componentDidUpdate() {
    if (this.concentrationSet) {
      this.concentrationSet = false;
      this.refs.myInput.value = (this.state.concentration==undefined) ? '' :this.state.concentration;
    }
  }

  render() {
    // For autocomplete dropdown 
    const departments = this.props.departments;
    const id = "concentration"+this.props.index;
    $( "#"+id ).autocomplete({
      source: departments,
      change: this.handleChangeAutocomplete.bind(this)
    },this);

    return (
      <div className="ui-widget">
        <input id = {id}
          ref='myInput'
          onChange={ (e) => {
            this.handleChange(e);
          } } />
      </div>
    )

  }
  handleChangeAutocomplete(e, ui) {
    if (ui.item != null) {
      this.setState({inputtedText: ui.item.value});
      this.setState({concentration: [ui.item.value]});
      this.props.onchange(ui.item.value, this.props.index);
    }
  }
  handleChange(e) {
    this.setState({inputtedText: e.target.value});
    // Only change the concentration if it exists in props.departments
    if (this.props.departments.includes(e.target.value)) {
      this.setState({concentration: [e.target.value]});
      this.props.onchange(e.target.value, this.props.index);
    };
  }
}


export default class PreferencesScreen extends React.Component {

  constructor(props) {
     super(props);
     this.state = {
       classYear: '',
       concentration: [],
       departmentalInterests: [],
       departments: [], //for the dropdowns
       numConcentrationBoxes: 0,
       numInterestBoxes: 0
     };
     this.displayExistingPreferences();
     this.loadDepartments();
  }

  render() {
    let classYearField = (
      <select name="classYear" 
          value={this.state.classYear}
          onChange={ (e) => {this.setState({classYear: e.target.value})} }> 
        <option value="" disabled>Select a year</option>
        <option value="Freshman">Freshman</option>
        <option value="Sophomore">Sophomore</option>
        <option value="Junior">Junior</option>
        <option value="Senior">Senior</option>
        <option value="Graduate Student">Grad</option>
      </select>
    );

    let concentrationFields = [];
    for (var i=0; i<this.state.numConcentrationBoxes; i++) {
      concentrationFields.push(
        <ConcentrationField 
          key={i}
          index={i} 
          concentrations={this.state.concentration}
          departments={this.state.departments} 
          onchange={this.updateConcentration.bind(this)}/>
      );
    };

    let interestsFields = [];
    for (var i=0; i<this.state.numInterestBoxes; i++) {
      interestsFields.push(
        <InterestField
          key={i} 
          index={i}
          interests={this.state.departmentalInterests}
          departments={this.state.departments} 
          onchange={this.updateInterest.bind(this)}/>
      );
    };

    let doneButton = <a href='#' onClick={() => this.done()}>done</a>;
    let addMoreConcentrations = (this.state.numConcentrationBoxes<3) ? <div className="addMore" onClick={this.addConcentrationBox.bind(this)}>+</div> : null;
    let addMoreInterests = (this.state.numInterestBoxes<5) ? <div className="addMore" onClick={this.addInterestBox.bind(this)}>+</div> : null;

    return (
      <div>
        {this.renderHeader()}
        <div className='preferences screen'>
          <h2>Tell us about yourself</h2>
          <p>We’ll use this information to provide personalized recommendations, and show you interesting courses you might not know about.</p>
          <div className="line"></div>
          <div className="prefSection">
            <label>Class Year</label><br/>
            {classYearField}
          </div>
          <div className="line"></div>
          <div className="prefSection">
            <label>Concentration(s)</label>
            {addMoreConcentrations}
            <div className="concentrationFields">
              {concentrationFields}
            </div>
          </div>
          <div className="line"></div>
          <div className="prefSection">
            <label>Departmental Interests</label>
            {addMoreInterests}
            <div className="interestsFields">
              {interestsFields}
            </div>
          </div>
          <div className="line" />
          <div className="prefSection">
            <div className='done big-button' onClick={this.done.bind(this)}>
              <span className='fa fa-check' /> Done
            </div>
          </div>
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
    navigateToRoute({});
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
    console.log(prefs);
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


      // Pre-fill Input boxes
      if (prefs.concentration!=undefined) {
        this.setState({numConcentrationBoxes: prefs.concentration.length});
      };
      if (prefs.dept_interests!=undefined) {
        this.setState({numInterestBoxes: prefs.dept_interests.length});
      };

    };
    api.getPrefs(display);
  }

  /*
    Loads the list of departments into the dropdowns using the api call.
  */
  loadDepartments() {
    let getDepts = (departments) => {
      this.setState({departments: departments});
    };
    api.getDepartments(getDepts);
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

  /*
    adds a concentration box to dom
  */
  addConcentrationBox(){
    let curr = this.state.numConcentrationBoxes;
    this.setState({numConcentrationBoxes: curr+1});
  }

  /*
    adds an interest box to dom
  */
  addInterestBox(){
    let curr = this.state.numInterestBoxes;
    this.setState({numInterestBoxes: curr+1});
  }
}

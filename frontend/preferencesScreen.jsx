import React from 'react';
import ReactDOM from 'react-dom';
import api from './api.jsx';

export default class PreferencesScreen extends React.Component {

  constructor(props) {
     super(props);
     this.state = {
       classYear: '',
       concentration: '',
       favoriteClass: '',
       departmentalInterests: []
     };
     this.displayExistingPreferences();
  }

  render() {

  	let classYearField = <input type='text' value={this.state.classYear} onChange={ (e) => {this.update("classYear", e.target.value)} } />;
    let concentrationField = <input type='text' value={this.state.concentration} onChange={ (e) => {this.update("concentration", e.target.value)} } />;
    let favoriteClassField = <input type='text' value={this.state.favoriteClass} onChange={ (e) => {this.update("favoriteClass", e.target.value)} } />;
    let departmentalInterestsField = <input type='text' value={this.state.departmentalInterests} onChange={ (e) => {this.update("departmentalInterests", e.target.value)} } />;

    let doneButton = <a href='#' onClick={() => this.props.onDone()}>done</a>;

    return (
    	<div className='Preferences'>
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
  	Updates the specified state with a new value.
  */
  update(state, value) {

  	// Set state on frontend
  	let obj = {};
  	obj[state] = value;
  	this.setState(obj);

  	// Update backend (there is prob a better way to do this) 
  	let prefs = {
  		class_year: this.state.classYear,
    	concentration: this.state.concentration,
    	favorite_class: this.state.favoriteClass,
    	dept_interests: this.state.departmentalInterests
  	};

  	api.postPrefs(prefs);
  }

  /*
  	Displays any existing user prefernces using the api call.
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


}

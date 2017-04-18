import React from 'react';
import ReactDOM from 'react-dom';
import api from './api.jsx';
import CalendarDayView from './calendarDayView.jsx';
import PreferencesScreen from './preferencesScreen.jsx';
import AddCoursesScreen from './addCoursesScreen.jsx';

export default class LoggedInView extends React.Component {
  constructor(props) {
     super(props);
     this.state = {
       screen: 'calendar'
     };
   }
  render() {
    return (
      <div className='LoggedInView'>
        {this.renderScreen()}
      </div>
    );
  }
  renderScreen() {
    let screen = this.state.screen;
    if (screen === 'calendar') {
      return (
        <div className='calendar-screen'>
          <div className='menu-button' onClick={() => this.setState({screen: 'menu'})}>Menu</div>
          <h1>Calendar goes here</h1>
          <div className='add-courses-button' onClick={() => this.setState({screen: 'add-courses'})}>Add Courses</div>
        </div>
      )
    } else if (screen === 'menu') {
      return (
        <div className='menu-screen'>
          <div onClick={() => this.setState({screen: 'calendar'})}>Calendar</div>
          <div onClick={() => this.setState({screen: 'preferences'})}>Account preferences</div>
          <div onClick={() => this.logOut()}>Log out</div>
        </div>
      )
    } else if (screen === 'preferences') {
      return <PreferencesScreen onDone={() => this.setState({screen: 'calendar'})} />;
    } else if (screen === 'add-courses') {
      return <AddCoursesScreen onDone={() => this.setState({screen: 'calendar'})} />;
    }
  }
  logOut() {
    api.logOut();
    this.props.onLogOut();
  }
}

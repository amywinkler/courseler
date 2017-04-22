import React from 'react';
import ReactDOM from 'react-dom';
import api from './api.jsx';
import Calendar from './calendar.jsx';
import PreferencesScreen from './preferencesScreen.jsx';
import AddCoursesScreen from './addCoursesScreen.jsx';
import CourseInfoScreen from './courseInfoScreen.jsx';
import { currentRoute, navigateToRoute } from './routing.jsx';

export default class LoggedInView extends React.Component {
  constructor(props) {
     super(props);
     this.state = {
       calendar: null
     };
     this.reloadCalendar();
   }
   
   reloadCalendar() {
     api.getCalendar((calendar) => {
       this.setState({calendar: calendar});
       // this.setState({screen: 'calendar'});
     });
   }
   
   render() {
     return (
       <div className='LoggedInView'>
         {this.renderScreen()}
       </div>
     );
   }
   
   renderScreen() {
     let screen = this.props.route.screen || 'calendar';
     if (screen === 'calendar') {
       return (
         <div className='calendar-screen'>
           <div className='header'>
             <div className='menu-button' onClick={() => navigateToRoute({screen: 'menu'})}>Menu</div>
           </div>
           <Calendar calendar={this.state.calendar} route={this.props.route} reloadCalendar={this.reloadCalendar.bind(this)} />          
           <div className='add-courses-button floating-button' onClick={() => navigateToRoute({screen: 'add-courses'})}>Add Courses</div>
         </div>
       )
     } else if (screen === 'menu') {
       return (
         <div className='menu-screen'>
           <div onClick={() => navigateToRoute({})}>Calendar</div>
           <div onClick={() => navigateToRoute({screen: 'preferences'})}>Account preferences</div>
           <div onClick={() => this.logOut()}>Log out</div>
         </div>
       )
     } else if (screen === 'preferences') {
       return <PreferencesScreen />;
     } else if (screen === 'add-courses') {
       return <AddCoursesScreen />;
     } else if (screen === 'course') {
       return <CourseInfoScreen courseCode={this.props.route.courseCode} calendar={this.state.calendar} reloadCalendar={this.reloadCalendar.bind(this)} />
     }
   }
   
   logOut() {
     api.logOut();
     this.props.onLogOut();
   }
}

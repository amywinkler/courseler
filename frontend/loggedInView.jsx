import React from 'react';
import ReactDOM from 'react-dom';
import api from './api.jsx';
import Calendar from './calendar.jsx';
import PreferencesScreen from './preferencesScreen.jsx';
import AddCoursesScreen from './addCoursesScreen.jsx';
import CourseInfoScreen from './courseInfoScreen.jsx';
import PopupMenu from './popupMenu.jsx';
import { currentRoute, navigateToRoute } from './routing.jsx';
import Modal from './modal.jsx';
import ShareSheet from './shareSheet.jsx';

export default class LoggedInView extends React.Component {
  constructor(props) {
     super(props);
     this.state = {
       calendar: null,
       showShareSheet: false
     };
     this.reloadCalendar();
   }
   
   reloadCalendar() {
     api.getCalendar((calendar) => {
       this.setState({calendar: calendar});
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
             <PopupMenu menu={this.renderMenuContent()}>Menu</PopupMenu>
           </div>
           <Calendar calendar={this.state.calendar} route={this.props.route} reloadCalendar={this.reloadCalendar.bind(this)} />          
           <div className='add-courses-button floating-button' onClick={() => navigateToRoute({screen: 'add-courses'})}>Add Courses</div>
           { this.renderShareSheet() }
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
   
   renderMenuContent() {
     return (
       <div>
         <div onClick={() => navigateToRoute({screen: 'preferences'})}>Account preferences</div>
         <div onClick={() => this.setState({showShareSheet: true})}>Share my cart</div>
         <div onClick={() => this.logOut()}>Log out</div>
       </div>
     )
   }
   
   renderShareSheet() {
     if (this.state.showShareSheet) {
       let dismiss = () => this.setState({showShareSheet: false});
       return <Modal onDismiss={dismiss}><ShareSheet /></Modal>
     } else {
       return null;
     }
   }
   
   logOut() {
     api.logOut();
     this.props.onLogOut();
   }
}

import React from 'react';
import ReactDOM from 'react-dom';
import LoggedInView from './loggedInView.jsx';
import LoggedOutView from './loggedOutView.jsx';
import api from './api.jsx';
import { currentRoute, navigateToRoute } from './routing.jsx';
import Calendar from './calendar.jsx';
import CourseInfoScreen from './courseInfoScreen.jsx';

export default class SharedCartApp extends React.Component {
  constructor(props) {
     super(props);
     window.onpopstate = (event) => {
       this.setState({route: currentRoute()});
     };
     this.state = {
       route: currentRoute(),
       calendar: null
     };
     this.reloadCalendar(this.getCartId());
   }
   reloadCalendar(id) {
     api.getSharedCart(id, (cal) => this.setState({calendar: cal}))
   }
   getCartId() {
     let parts = location.pathname.split('/')
     return parts[parts.length - 1];
   }
   render() {
     return (
       <div className='SharedCartApp'>
         {this.renderContent()}
       </div>
      )
   }
   renderContent() {
     let screen = this.state.route.screen || 'calendar';
     if (screen === 'calendar') {
       return <Calendar calendar={this.state.calendar} locked={true} shared={true} />;
     } else if (screen === 'course') {
       return <CourseInfoScreen courseCode={this.state.route.courseCode} calendar={this.state.calendar} reloadCalendar={this.reloadCalendar.bind(this)} locked={true} />;
     }
   }
}

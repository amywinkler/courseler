**Team Members:** 
Alberta Devor, Amy Winkler, Koko Nakajima, Nathaniel Parrott

**Project** 
A web application to help Brown students navigate the tumultuous series of days that is known as “shopping period”, where they have to pick their classes. We are going to allow students to add courses to an easy to use mobile-responsive calendar. We also suggest courses to check out based on user interests/preferences.

## How to Build and Run

You gotta compile the frontend _and_ the backend!!!!

### Installing dependencies for frontend!

Install dependencies! (do this once)

`npm install`

also:

`npm install -g webpack`

### Compiling the frontend

`webpack`

### Compiling the backend

`mvn package`

### Run it!

`./run --gui`

_If you update the frontend code, you just need to re-run `webpack` — no need to `mvn package` or `./run` again!_

### Overview:
### Potential User Flow:
(First-time user)
 * Log in with my Brown account information
* Input preliminary information (concentration, departments I’m interested in, etc?)
* Calendar view
      * Ability to add multiple courses per timeslot
      * Ability to toggle course suggestions for specified timeslots

![friends](http://i.imgur.com/NH3osox.png)

### Features:
1. brown.edu email authentication/Brown Wifi authentication:

      * We need this because the Critical Review data cannot be accessed outside of the Brown community. We will explore which method is the most reasonable out of these two. 

2. Calendar with time slots

      * Students often have multiple ways of keeping track of their shopping period courses because they are uncertain about how to keep all of their options (that they prioritize in different ways) in one list. Having an intuitive and easy to use calendar in our app would be essential for allowing students to create their shopping schedule in one application. In our preliminary interviews, students said they primarily used Courses@Brown’s calendar export functionality to keep track of where and when to shop (although some supplemented with written notes, suggesting that there’s room to improve on the calendar-based approach).

3. Integration with Critical Review data

      * Critical Review data can be used to generate relevant course recommendations based on factors like open timeslots on the user’s schedule, indicated department interests, and class year demographic. In our preliminary interviews we spoke with many students who thought the Critical Review was an invaluable resource for shopping period.

4. Integration with Banner Data

      *  We must use banner data to get information about all of the courses that brown offers for a given semester, their descriptions, time slots and locations.

5. Using data to suggest courses

      *  We will develop a ranking system to generate suggested courses for students based on the courses in their cart, their department preferences, and their open times. Interviewed participants indicated that they lacked a concrete system for getting suggestions outside of their own department. Participants also indicated they specifically were unsure how to obtain suggestions in departments where they do not have friends who have already taken courses in the department. This would benefit students who are looking for suggestions in departments where they do not have social connections.

5. Share your cart

      *  We have a feature to share your cart with others.


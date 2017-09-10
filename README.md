# Dash and Dine

### Group Members: 
Micheal Luker, Eric Saputra and Tiffany Wong

### Purpose: 
This project was for CS 275 which focused on developing Android apps. This app facilitate deliveries from food trucks on-campus by availible students for students who are in class or otherwise busy.


|     | Team Member | Task                                                               |
| --- |:-----------:| ------------------------------------------------------------------ |
| [x] | Michael     | Storing information about food trucks                              |
| [x] | Michael     | Handling customer orders and placing them into a cloudmine         |
| [x] | Eric        | Handling GPS location for the deliverer and the customer           |
| [x] | Tiffany     | Creating the user interface for the login menu and messaging board |


### Limitations:
- Contacting the deliverer does not currently work
  - Implementing the messaging system
- GPS does not currently work
  - Android's location class was very difficult to work with
  - Could not figure out how to update the deliverer's phone location
- Learning how to use cloudmine was somewhat difficult


### Walkthrough of Application:
The application should run from the apk file included. Upon opening the application you come to a login screen which currently takes anything as input. Once logged in, you come to a screen with four buttons. 

The customer button allows you to place an order from the stored food trucks. Once a food truck is selected the get menu button must be pressed to get menu items for that food truck. The other two fields to be entered are address and additional notes, which is used for any special instructions you might want to enter. To go back an activity, the deliverer button is not yet implemented. The view customer button shows a map of all available customers and all food cars. The view deliver button is currently just a map with one fixed point on it. 
